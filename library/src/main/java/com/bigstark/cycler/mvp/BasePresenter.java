package com.bigstark.cycler.mvp;

/**
 * Created by bigstark on 2016. 11. 24..
 */

public interface BasePresenter {


    /**
     * Stop requests in presenter.
     *
     * It is called when life cycle is going to be destroyed
     * or someone want to stop request such as refreshing.
     */
    void stopRequest();
}
