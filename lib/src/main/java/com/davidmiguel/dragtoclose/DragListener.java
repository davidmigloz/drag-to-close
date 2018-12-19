/*
 * Copyright (c) 2017 David Miguel Lozano.
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

/**
 * Enables to listen drag events.
 */
public interface DragListener {

    /**
     * Invoked when the view has just started to be dragged.
     */
    void onStartDraggingView();

    /**
     * Invoked when the view has being dragged out of the screen
     * and just before calling activity.finish().
     */
    void onViewCosed();

    /**
     * Invoked during the view is dragging.
     *
     * @param dragOffset vertical drag offset between 0 and 1.
     */
    void onDragging(float dragOffset);
}
