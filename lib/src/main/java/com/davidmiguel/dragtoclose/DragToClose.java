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
package com.davidmiguel.dragtoclose;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * View group that extends FrameLayout and allows to finish an activity dragging down a view.
 */
@SuppressWarnings("unused")
public class DragToClose extends FrameLayout {

    // Sensitivity detecting the start of a drag (larger values are more sensitive)
    private static final float DRAG_SENSITIVITY = 1.0f;
    // If the view is dragged with a higher speed than the threshold, the view is
    // closed automatically
    static final float SPEED_THRESHOLD_TO_CLOSE = 800.0f;
    // If dragging finishes below this threshold the view returns to its original position,
    // if the threshold is exceeded, the view is closed automatically
    static final float HEIGHT_THRESHOLD_TO_CLOSE = 0.5f;

    @IdRes
    private int draggableContainerId;
    @IdRes
    private int draggableViewId;

    private View draggableContainer;
    private View draggableView;
    private ViewDragHelper dragHelper;
    private DragListener listener;

    private int verticalDraggableRange;

    public DragToClose(@NonNull Context context) {
        super(context);
    }

    public DragToClose(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttributes(attrs);
    }

    public DragToClose(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeAttributes(attrs);
    }

    /**
     * Configures draggable view and initializes DragViewHelper.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
        initViewDragHelper();
    }

    /**
     * Gets the height of the DragToClose view and configures the vertical
     * draggable threshold base on it.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        verticalDraggableRange = h;
    }

    /**
     * Intercepts only touch events over the draggable view.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean handled = false;
        if (isEnabled()) {
            handled = dragHelper.shouldInterceptTouchEvent(event)
                    && dragHelper.isViewUnder(draggableView, (int) event.getX(), (int) event.getY());
        } else {
            dragHelper.cancel();
        }
        return handled || super.onInterceptTouchEvent(event);
    }

    /**
     * Dispatches touch event to the draggable view.
     * The touch is realized only if is over the draggable view.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return isViewTouched(draggableView, (int) event.getX(), (int) event.getY());
    }

    /**
     * Automatic settles the draggable view when it is released.
     */
    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * Returns the draggable view id.
     */
    public int getDraggableViewId() {
        return draggableViewId;
    }

    /**
     * Sets the draggable view id.
     */
    public void setDraggableViewId(@IdRes int draggableViewId) {
        this.draggableViewId = draggableViewId;
        invalidate();
        requestLayout();
    }

    /**
     * Returns the draggable container id.
     */
    public int getDraggableContainerId() {
        return draggableContainerId;
    }

    /**
     * Sets the draggable container id.
     */
    public void setDraggableContainerId(@IdRes int draggableContainerId) {
        this.draggableContainerId = draggableContainerId;
        invalidate();
        requestLayout();
    }

    /**
     * Sets drag listener.
     */
    public void setDragListener(@NonNull DragListener listener) {
        this.listener = listener;
    }

    /**
     * Invoked when the view has just started to be dragged.
     */
    void onStartDraggingView() {
        if (listener != null) {
            listener.onStartDraggingView();
        }
    }

    /**
     * Returns the draggable range.
     */
    int getDraggableRange() {
        return verticalDraggableRange;
    }

    /**
     * Finishes the activity.
     */
    void closeActivity() {
        if (listener != null) {
            listener.onViewCosed();
        }
        Activity activity = (Activity) getContext();
        activity.finish();
        activity.overridePendingTransition(0, android.R.anim.fade_out);
    }

    /**
     * Modify dragged view alpha based on the vertical position while the view is being
     * vertical dragged.
     */
    void changeDragViewViewAlpha() {
        draggableContainer.setAlpha(1 - getVerticalDragOffset());
    }

    /**
     * Drags the draggable container to given position.
     */
    void smoothScrollToY(int settleDestY) {
        if (dragHelper.settleCapturedViewAt(getPaddingLeft(), settleDestY)) {
            ViewCompat.postInvalidateOnAnimation(DragToClose.this);
        }
    }

    /**
     * Initializes XML attributes.
     */
    private void initializeAttributes(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.DragToClose, 0, 0);
        try {
            draggableViewId = array.getResourceId(R.styleable.DragToClose_draggableView, -1);
            draggableContainerId = array.getResourceId(R.styleable.DragToClose_draggableContainer, -1);
            if (draggableViewId == -1 || draggableContainerId == -1) {
                throw new IllegalArgumentException("The attributes are required.");
            }
        } finally {
            array.recycle();
        }
    }

    /**
     * Initializes views.
     */
    private void initViews() {
        draggableContainer = findViewById(draggableContainerId);
        draggableView = findViewById(draggableViewId);
    }

    /**
     * Initializes ViewDragHelper.
     */
    private void initViewDragHelper() {
        dragHelper = ViewDragHelper.create(this, DRAG_SENSITIVITY,
                new DragHelperCallback(this, draggableContainer));
    }

    /**
     * Determines if position (x, y) is below given view.
     */
    private boolean isViewTouched(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0]
                && screenX < viewLocation[0] + view.getWidth()
                && screenY >= viewLocation[1]
                && screenY < viewLocation[1] + view.getHeight();
    }

    /**
     * Calculate the dragged view top position normalized between 1 and 0.
     */
    private float getVerticalDragOffset() {
        return (float) Math.abs(draggableContainer.getTop()) / (float) getHeight();
    }
}
