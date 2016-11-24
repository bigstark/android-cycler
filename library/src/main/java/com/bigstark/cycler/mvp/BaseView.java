package com.bigstark.cycler.mvp;

/**
 * Created by bigstark on 2016. 11. 24..
 */

public interface BaseView {

    /**
     * For checking Life Cycle is destroyed.
     * It is usually used in {@link BasePresenter}.
     *
     * @return whether life cycle is destroyed.
     */
    boolean isLifeCycleFinishing();

}
