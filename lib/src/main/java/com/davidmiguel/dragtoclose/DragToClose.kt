/*
 * Copyright (c) 2017 David Miguel Lozano
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.davidmiguel.dragtoclose

import android.app.Activity
import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

/**
 * View group that extends FrameLayout and allows to finish an activity dragging down a view.
 */
@Suppress("unused")
class DragToClose : FrameLayout {

    // Attributes
    @IdRes
    private var draggableContainerId: Int = -1
    @IdRes
    private var draggableViewId: Int = -1
    private var finishActivity: Boolean = false
    private var closeOnClick: Boolean = false

    private lateinit var draggableContainer: View
    private lateinit var draggableView: View
    private var draggableContainerTop: Int = 0
    private var draggableContainerLeft: Int = 0

    private lateinit var dragHelper: ViewDragHelper
    private var listener: DragListener? = null


    private var verticalDraggableRange: Int = 0
    private var uiBlocked: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeAttributes(attrs)
    }

    /**
     * Configures draggable view and initializes DragViewHelper.
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        initViews()
        initViewDragHelper()
    }

    /**
     * Gets the height of the DragToClose view and configures the vertical
     * draggable threshold base on it.
     */
    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        super.onSizeChanged(w, h, oldW, oldH)
        verticalDraggableRange = h
    }

    /**
     * Intercepts only touch events over the draggable view.
     */
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (uiBlocked) return true // Don't propagate event when ui blocked
        var handled = false
        if (isEnabled) {
            handled = dragHelper.shouldInterceptTouchEvent(event)
                    && dragHelper.isViewUnder(draggableView, event.x.toInt(), event.y.toInt())
        } else {
            dragHelper.cancel()
        }
        return handled || super.onInterceptTouchEvent(event)
    }

    /**
     * Dispatches touch event to the draggable view.
     * The touch is realized only if is over the draggable view.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (uiBlocked) return true // Ignore event when ui blocked
        dragHelper.processTouchEvent(event)
        return isViewTouched(draggableView, event.x.toInt(), event.y.toInt())
    }

    /**
     * Automatic settles the draggable view when it is released.
     */
    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    /**
     * Returns the draggable view id.
     */
    fun getDraggableViewId(): Int = draggableViewId

    /**
     * Sets the draggable view id.
     */
    fun setDraggableViewId(@IdRes draggableViewId: Int) {
        this.draggableViewId = draggableViewId
        invalidate()
        requestLayout()
    }

    /**
     * Returns the draggable container id.
     */
    fun getDraggableContainerId(): Int = draggableContainerId

    /**
     * Sets the draggable container id.
     */
    fun setDraggableContainerId(@IdRes draggableContainerId: Int) {
        this.draggableContainerId = draggableContainerId
        invalidate()
        requestLayout()
    }

    /**
     * Checks whether finish activity is activated or not.
     */
    fun isFinishActivity(): Boolean = finishActivity

    /**
     * Sets finish activity attribute. If true, the activity is closed when
     * the view is dragged out. Default: true.
     */
    fun setFinishActivity(finishActivity: Boolean) {
        this.finishActivity = finishActivity
    }

    /**
     * Checks whether close on click is activated or not.
     */
    fun isCloseOnClick(): Boolean = closeOnClick

    /**
     * Sets close on click attribute. If true, the draggable container is slid down
     * when the draggable view is clicked. Default: false.
     */
    fun setCloseOnClick(closeOnClick: Boolean) {
        if (closeOnClick) {
            initOnClickListener(draggableView)
        } else {
            draggableView.setOnClickListener(null)
        }
        this.closeOnClick = closeOnClick
    }

    /**
     * Sets drag listener.
     */
    fun setDragListener(listener: DragListener) {
        this.listener = listener
    }

    /**
     * Slides down draggable container out of the DragToClose view.
     */
    fun closeDraggableContainer() {
        uiBlocked = true
        slideViewTo(draggableContainer, paddingLeft + draggableContainerLeft, verticalDraggableRange)
    }

    /**
     * Slides up draggable container to its original position.
     */
    fun openDraggableContainer() {
        slideViewTo(draggableContainer, paddingLeft + draggableContainerLeft,
                paddingTop + draggableContainerTop)
        uiBlocked = false
    }

    /**
     * Invoked when the view has just started to be dragged.
     */
    internal fun onStartDraggingView() {
        listener?.onStartDraggingView()
    }

    /**
     * Returns the draggable range.
     */
    fun getDraggableRange(): Int = verticalDraggableRange

    /**
     * Notifies the listener that the view has been closed
     * and finishes the activity (if need be).
     */
    internal fun closeActivity() {
        listener?.onViewCosed()
        if (finishActivity) {
            val activity = context as Activity
            activity.finish()
            activity.overridePendingTransition(0, R.anim.fade_out)
        }
    }

    /**
     * Modify dragged view alpha based on the vertical position while the view is being
     * vertical dragged.
     */
    internal fun changeDragViewViewAlpha() {
        draggableContainer.alpha = 1 - getVerticalDragOffset()
    }

    /**
     * Drags the draggable container to given position.
     */
    internal fun smoothScrollToY(settleDestY: Int) {
        if (dragHelper.settleCapturedViewAt(paddingLeft, settleDestY)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    /**
     * Initializes XML attributes.
     */
    private fun initializeAttributes(attrs: AttributeSet?) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DragToClose, 0, 0)
        try {
            draggableViewId = array.getResourceId(R.styleable.DragToClose_draggableView, -1)
            draggableContainerId = array.getResourceId(R.styleable.DragToClose_draggableContainer, -1)
            finishActivity = array.getBoolean(R.styleable.DragToClose_finishActivity, true)
            closeOnClick = array.getBoolean(R.styleable.DragToClose_closeOnClick, false)
            if (draggableViewId == -1 || draggableContainerId == -1) {
                throw IllegalArgumentException("draggableView and draggableContainer attributes are required.")
            }
            uiBlocked = false
        } finally {
            array.recycle()
        }
    }

    /**
     * Initializes views.
     */
    private fun initViews() {
        draggableContainer = findViewById(draggableContainerId)
                ?: throw IllegalArgumentException("draggableContainer not found!")
        draggableContainerTop = draggableContainer.top
        draggableContainerLeft = draggableContainer.left
        draggableView = findViewById(draggableViewId)
                ?: throw IllegalArgumentException("draggableView not found!")
        if (closeOnClick) {
            initOnClickListener(draggableView)
        }
    }

    /**
     * Initializes on OnClickListener (if need be).
     */
    private fun initOnClickListener(clickableView: View) {
        clickableView.setOnClickListener { closeDraggableContainer() }
    }

    /**
     * Initializes ViewDragHelper.
     */
    private fun initViewDragHelper() {
        dragHelper = ViewDragHelper.create(this, DRAG_SENSITIVITY,
                DragHelperCallback(this, draggableContainer))
    }

    /**
     * Determines if position (x, y) is below given view.
     */
    private fun isViewTouched(view: View, x: Int, y: Int): Boolean {
        val viewLocation = IntArray(2)
        view.getLocationOnScreen(viewLocation)
        val parentLocation = IntArray(2)
        this.getLocationOnScreen(parentLocation)
        val screenX = parentLocation[0] + x
        val screenY = parentLocation[1] + y
        return (screenX >= viewLocation[0]
                && screenX < viewLocation[0] + view.width
                && screenY >= viewLocation[1]
                && screenY < viewLocation[1] + view.height)
    }

    /**
     * Calculate the dragged view top position normalized between 1 and 0.
     */
    private fun getVerticalDragOffset(): Float = Math.abs(draggableContainer.top).toFloat() / height.toFloat()

    /**
     * Slides down a view.
     */
    private fun slideViewTo(view: View?, left: Int, top: Int) {
        dragHelper.smoothSlideViewTo(view!!, left, top)
        invalidate()
    }

    companion object {

        // Sensitivity detecting the start of a drag (larger values are more sensitive)
        private val DRAG_SENSITIVITY = 1.0f
        // If the view is dragged with a higher speed than the threshold, the view is
        // closed automatically
        internal val SPEED_THRESHOLD_TO_CLOSE = 800.0f
        // If dragging finishes below this threshold the view returns to its original position,
        // if the threshold is exceeded, the view is closed automatically
        internal val HEIGHT_THRESHOLD_TO_CLOSE = 0.5f
    }
}
