package com.google.android.gms.common.api.internal;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<C0301zaa> zacl;

    public zaa(Activity activity) {
        this(C0301zaa.zaa(activity));
    }

    private zaa(C0301zaa zaa) {
        this.zacl = new WeakReference<>(zaa);
    }

    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C0301zaa zaa = (C0301zaa) this.zacl.get();
        if (zaa != null) {
            zaa.zaa(runnable);
            return this;
        }
        throw new IllegalStateException("The target activity has already been GC'd");
    }

    /* renamed from: com.google.android.gms.common.api.internal.zaa$zaa  reason: collision with other inner class name */
    static class C0301zaa extends LifecycleCallback {
        private List<Runnable> zacm = new ArrayList();

        /* access modifiers changed from: private */
        public static C0301zaa zaa(Activity activity) {
            C0301zaa zaa;
            synchronized (activity) {
                LifecycleFragment fragment = getFragment(activity);
                zaa = (C0301zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", C0301zaa.class);
                if (zaa == null) {
                    zaa = new C0301zaa(fragment);
                }
            }
            return zaa;
        }

        private C0301zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        /* access modifiers changed from: private */
        public final synchronized void zaa(Runnable runnable) {
            this.zacm.add(runnable);
        }

        public void onStop() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zacm;
                this.zacm = new ArrayList();
            }
            for (Runnable run : list) {
                run.run();
            }
        }
    }
}