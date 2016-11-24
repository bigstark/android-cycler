package com.bigstark.cycler.sample.mvp;

import com.bigstark.cycler.mvp.MVPViewGetter;

import java.lang.ref.WeakReference;

/**
 * Created by bigstark on 2016. 11. 24..
 */

public class SamplePresenterImpl implements SamplePresenter {

    private WeakReference<SampleView> weakView;


    public SamplePresenterImpl(SampleView sampleView) {
        weakView = new WeakReference<>(sampleView);
    }


    @Override
    public void getSomething() {
        SampleView sampleView = MVPViewGetter.getView(weakView);
        if (sampleView == null) {
            return;
        }


        sampleView.doSomething();
    }

    @Override
    public void stopRequest() {
        // stop requests
    }
}
