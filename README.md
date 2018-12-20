# Drag to close  [![](https://jitpack.io/v/davidmigloz/drag-to-close.svg)](https://jitpack.io/#davidmigloz/drag-to-close)

Android library that provides a view group which allows to finish an activity by dragging a view.

![image](img/screenshot.gif)

## Usage

#### Step 1

Add the JitPack repository to your `build.gradle ` file:

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

#### Step 2

Add the dependency:

```gradle
dependencies {
	implementation 'com.github.davidmigloz:drag-to-close:1.0.0'
}
```

[CHANGELOG](https://github.com/davidmigloz/drag-to-close/blob/master/CHANGELOG.md)

#### Step 3

Use `DragToClose` view group to your layout:

```xml
<com.davidmiguel.dragtoclose.DragToClose
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:drag="http://schemas.android.com/apk/res-auto"
    ...
    drag:dragtoclose_draggableContainer="@+id/card"
    drag:dragtoclose_draggableView="@+id/close_arrow"
    drag:dragtoclose_finishActivity="false"
    drag:dragtoclose_closeOnClick="true">

    ...
</com.davidmiguel.dragtoclose.DragToClose>  
```

#### Attributes

- `drag:dragtoclose_draggableContainer="[reference]"` (required): view that is going to be dragged (in the example, the card).
- `drag:dragtoclose_draggableView="[reference]"` (required): view that is going to listen to draggable events (in the example, the arrow).
- `drag:dragtoclose_finishActivity="[true|false]"` (default: `true`): when the `draggableContainer` is dragged out of the `DragToClose` view, the activity is finished (`activity.finish()` is called).
- `drag:dragtoclose_closeOnClick="[true|false]"` (default: `false`): when the `draggableView` is clicked, `draggableContainer` is slid down out of the `DragToClose` view automatically (`activity.finish()` is called if need be).

#### Methods

- `getDraggableViewId()`: returns the draggable view id.
- `setDraggableViewId(@IdRes int draggableViewId)`: sets the draggable view id.
- `getDraggableContainerId()`: returns the draggable container id.
- `setDraggableContainerId(@IdRes int draggableContainerId)`: sets the draggable container id.
- `isFinishActivity()`: checks whether finish activity is activated or not.
- `setFinishActivity(boolean finishActivity)`: sets finish activity attribute. If true, the activity is closed when the view is dragged out. Default: true.
- `isCloseOnClick()`: checks whether close on click is activated or not.
- `setCloseOnClick(boolean closeOnClick)`: sets close on click attribute. If true, the draggable container is slid down when the draggable view is clicked. Default: false.
- `setDragListener(DragListener listener)`: sets drag listener.
- `closeDraggableContainer()`: slides down draggable container out of the `DragToClose` view (`activity.finish()` is called if need be).
- `openDraggableContainer()`: slides up draggable container to its original position.

#### Callback

If you want to listen to dragging events, you can use `DragListener`:

- `onStartDraggingView()`: invoked when the view has just started to be dragged.
- `onDragging(dragOffset)`: invoked when the view is being dragged.
- `onViewCosed()`: invoked when the view has being dragged out of the screen and just before calling `activity.finish()` (if need be).

```kotlin
dragToClose.setDragListener(object : DragListener {
    override fun onStartDraggingView() {...}
    override fun onDragging(dragOffset: Float) {...}
    override fun onViewCosed() {...}
})
```

Take a look at the [sample app](https://github.com/davidmigloz/drag-to-close/tree/master/sample) to see the library working.

## Thanks to

- [DragQueen](https://github.com/fedepaol/dragqueen): sample app that shows android's ViewDragHelper usage.
- [DraggablePanel](https://github.com/pedrovgs/DraggablePanel): Android library used to create an awesome Android UI based on a draggable element similar to the last YouTube graphic component.
- [SwipeBack](https://github.com/liuguangqiang/SwipeBack): Android library that can finish a activity by using gesture.

## Contributing

If you find any issues or you have any questions, ideas... feel free to [open an issue](https://github.com/davidmigloz/drag-to-close/issues/new).
Pull request are very appreciated.

## License

Copyright (c) 2018 David Miguel Lozano

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
