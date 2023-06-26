package org.loka.screensharekit;

import android.os.IInterface;
import android.view.IRotationWatcher;

public final class WindowManager {
    private final IInterface manager;

    public WindowManager(IInterface manager) {
        this.manager = manager;
    }

    public int getRotation() {
        try {
            Class<?> cls = manager.getClass();
            try {
                return (Integer) manager.getClass().getMethod("getRotation").invoke(manager);
            } catch (NoSuchMethodException e) {
                // method changed since this commit:
                // https://android.googlesource.com/platform/frameworks/base/+/8ee7285128c3843401d4c4d0412cd66e86ba49e3%5E%21/#F2
                return (Integer) cls.getMethod("getDefaultDisplayRotation").invoke(manager);
            }
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public void registerRotationWatcher(IRotationWatcher rotationWatcher) {
        try {
            Class<?> cls = manager.getClass();
            try {
                cls.getMethod("watchRotation", IRotationWatcher.class).invoke(manager, rotationWatcher);
            } catch (NoSuchMethodException e) {
                // display parameter added since this commit:
                // https://cs.android.com/android/platform/superproject/+/master:frameworks/base/services/core/java/com/android/server/wm/WindowManagerService.java;drc=03de64bece0fe350f249b691682ffc44cf9fff73;l=4463
                // https://cs.android.com/android/platform/superproject/+/master:frameworks/base/core/java/android/view/IRotationWatcher.aidl;drc=9066cfe9886ac131c34d59ed0e2d287b0e3c0087;l=23
                cls.getMethod("watchRotation", IRotationWatcher.class, int.class).invoke(manager, rotationWatcher, 0);
            }
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
