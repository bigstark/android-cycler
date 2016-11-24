package com.bigstark.cycler.sample;

import android.os.Bundle;

import com.bigstark.cycler.CyclerActivity;
import com.bigstark.cycler.mvp.BasePresenter;
import com.bigstark.cycler.mvp.BaseView;
import com.bigstark.cycler.sample.mvp.SamplePresenter;
import com.bigstark.cycler.sample.mvp.SamplePresenterImpl;
import com.bigstark.cycler.sample.mvp.SampleView;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class SampleActivity extends CyclerActivity implements SampleView {

    SampleCycler sampleCycler;

    Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    SamplePresenter samplePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        // support butterknife. It helps unbind when object destroyed.
        setUnbinder(ButterKnife.bind(this));


        // add cycler. It helps Sample Cycler listening life cycle.
        sampleCycler = new SampleCycler();
        addCycler(sampleCycler);


        // add rx subscription. It helps unsubscribe when activity is destroyed.
        Subscription subscription = bus
                .observeOn(AndroidSchedulers.mainThread())
                .ofType(String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        // do something
                    }
        });
        addSubscription(subscription);


        // add mvp presenter. It helps stop requests when activity is destroyed.
        samplePresenter = new SamplePresenterImpl(this);
        addPresenter(samplePresenter);
        samplePresenter.getSomething();
    }


    @Override
    public void doSomething() {
        // do something
    }

    @Override
    public boolean isLifeCycleFinishing() {
        return isFinishing();
    }
}
