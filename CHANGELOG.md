# Changelog drag-to-close

## `1.1.0` (04/12/19)

- Unregister dragging listener when closing card programatically #13
- Fix onViewCosed() never called if the screen size changes while closing view #25
- Target Android 10 (API 29)
- Update dependencies

## `1.0.0` (20/12/18)

- Migrate project to Kotlin #22
- Add dragtoclose_ resource prefix #24 (breaking change)
- Add onDragging(dragOffset) callback method #21 (breaking change)

### Migration

Add the prefix `dragtoclose_` to all the library attributes.
E.g.
```
Before:  drag:draggableContainer="@+id/card"
Now:     drag:dragtoclose_draggableContainer="@+id/card"
```

Add the new method `onDragging(dragOffset)` to your  `DragListener`

## `0.3.1` (26/11/18)

- Card gets stuck if you touch it while it's been closed programmatically #20

## `0.3.0` (26/11/18)

- Migrate to AndroidX #12 (only compatible with projects that have been migrated to androidx)

## `0.2.0` (09/07/18)

- Update gradle, compileSdkVersion, buildToolsVersion and targetSdkVersion
- Check if draggableContainer or draggableView is null #16

## `0.1.0` (24/06/17)

- Initial release
