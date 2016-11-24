package com.bigstark.cycler.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by bigstark on 2016. 11. 24..
 */

public class MVPViewGetter {


    /**
     * It helps that get view from weak reference.
     *
     * @param weakView contains {@link BaseView}
     * @return T that implements {@link BaseView}
     */
    public static <T extends BaseView> T getView(WeakReference<? extends BaseView> weakView) {
        return  weakView == null ||
                weakView.get() == null ||
                weakView.get().isLifeCycleFinishing() ?
                null :
                (T) weakView.get();
    }
}
