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

import android.support.v4.widget.ViewDragHelper;
import android.view.View;

import static com.davidmiguel.dragtoclose.DragToClose.HEIGHT_THRESHOLD_TO_CLOSE;
import static com.davidmiguel.dragtoclose.DragToClose.SPEED_THRESHOLD_TO_CLOSE;

/**
 * Dragging controller.
 */
class DragHelperCallback extends ViewDragHelper.Callback {

    private DragToClose dragToClose;
    private View draggableContainer;

    private int lastDraggingState;
    private int topBorderDraggableContainer;

    DragHelperCallback(DragToClose dragToClose, View draggableContainer) {
        this.dragToClose = dragToClose;
        this.draggableContainer = draggableContainer;
        lastDraggingState = ViewDragHelper.STATE_IDLE;
    }

    /**
     * Checks dragging states and notifies them.
     */
    @Override
    public void onViewDragStateChanged(int state) {
        // If no state change, don't do anything
        if (state == lastDraggingState) {
            return;
        }
        // If last state was dragging or settling and current state is idle,
        // the view has stopped moving. If the top border of the container is
        // equal to the vertical draggable range, the view has being dragged out,
        // so close activity is called
        if ((lastDraggingState == ViewDragHelper.STATE_DRAGGING
                || lastDraggingState == ViewDragHelper.STATE_SETTLING)
                && state == ViewDragHelper.STATE_IDLE
                && topBorderDraggableContainer == dragToClose.getDraggableRange()) {
            dragToClose.closeActivity();

        }
        // If the view has just started being dragged, notify event
        if (state == ViewDragHelper.STATE_DRAGGING) {
            dragToClose.onStartDraggingView();
        }
        // Save current state
        lastDraggingState = state;
    }

    /**
     * Registers draggable container position and changes the transparency of the container
     * based on the vertical position while the view is being vertical dragged.
     */
    @Override
    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
        topBorderDraggableContainer = top;
        dragToClose.changeDragViewViewAlpha();
    }

    /**
     * Handles the settling of the draggable view when it is released.
     * Dragging speed is more important than the place the view is released.
     * If the speed is greater than SPEED_THRESHOLD_TO_CLOSE the view is settled to closed.
     * Else if the top
     */
    @Override
    public void onViewReleased(View releasedChild, float xVel, float yVel) {
        // If view is in its original position or out of range, don't do anything
        if (topBorderDraggableContainer == 0 || topBorderDraggableContainer >= dragToClose.getDraggableRange()) {
            return;
        }
        boolean settleToClosed = false;
        // Check speed
        if (yVel > SPEED_THRESHOLD_TO_CLOSE) {
            settleToClosed = true;
        } else {
            // Check position
            int verticalDraggableThreshold = (int) (dragToClose.getDraggableRange() * HEIGHT_THRESHOLD_TO_CLOSE);
            if (topBorderDraggableContainer > verticalDraggableThreshold) {
                settleToClosed = true;
            }
        }
        // If settle to closed -> moved view out of the screen
        int settleDestY = settleToClosed ? dragToClose.getDraggableRange() : 0;
        dragToClose.smoothScrollToY(settleDestY);
    }

    /**
     * Sets the vertical draggable range.
     */
    @Override
    public int getViewVerticalDragRange(View child) {
        return dragToClose.getDraggableRange();
    }

    /**
     * Configures which is going to be the draggable container.
     */
    @Override
    public boolean tryCaptureView(View child, int pointerId) {
        return child.equals(draggableContainer);
    }

    /**
     * Defines clamped position for left border.
     * DragToClose padding must be taken into consideration.
     */
    @Override
    public int clampViewPositionHorizontal(View child, int left, int dx) {
        return child.getLeft();
    }

    /**
     * Defines clamped position for top border.
     */
    @Override
    public int clampViewPositionVertical(View child, int top, int dy) {
        final int topBound = dragToClose.getPaddingTop(); // Top limit
        final int bottomBound = dragToClose.getDraggableRange(); // Bottom limit
        return Math.min(Math.max(top, topBound), bottomBound);
    }
}
