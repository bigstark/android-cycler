package com.bigstark.cycler.mvvm;

import android.content.Intent;
import android.view.View;

import com.bigstark.cycler.Cycler;
import com.bigstark.cycler.mvp.BasePresenter;

import java.util.HashSet;
import java.util.Set;

import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bigstark on 2016. 11. 25..
 *
 * In ViewModel, It is used for using lifecycle, subscriptions, other cyclers, presenters etc...
 *
 * It need to be attached to {@link com.bigstark.cycler.CyclerActivity} or {@link com.bigstark.cycler.CyclerFragment}
 */

public class CyclerViewModel implements Cycler {

    public CyclerViewModel(View itemView) {
        // bind ButterKnife and setUnbinder
    }

    private Set<Cycler> cyclers = new HashSet<>();
    private Set<BasePresenter> presenters = new HashSet<>();
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private Unbinder unbinder;

    private boolean stopped = false;

    protected boolean isStopped() {
        return stopped;
    }

    /**
     * Add {@param cycler} in {@link CyclerViewModel}
     *
     * It helps object that implements cycler take MVVM's Life Cycle.
     */
    protected void addCycler(Cycler cycler) {
        if (cycler == null) {
            return;
        }

        // prevent the duplicated cycler
        cyclers.remove(cycler);
        cyclers.add(cycler);

        cycler.onAttached();
    }


    /**
     * Remove {@param cycler} from {@link CyclerViewModel}
     */
    protected void removeCycler(Cycler cycler) {

        if (cycler == null) {
            return;
        }

        cyclers.remove(cycler);
    }


    /**
     * Add {@param presenter} in {@link CyclerViewModel}
     */
    protected void addPresenter(BasePresenter presenter) {
        if (presenter == null) {
            return;
        }

        // prevent the duplicated presenter
        presenters.remove(presenter);
        presenters.add(presenter);
    }


    /**
     * Remove {@param presenter} from {@link CyclerViewModel}
     */
    protected void removePresenter(BasePresenter presenter) {
        if (presenter == null) {
            return;
        }

        presenters.remove(presenter);
    }


    /**
     * Add {@param subscription} in {@link CyclerViewModel}
     */
    protected void addSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }

        subscriptions.add(subscription);
    }


    /**
     * Remove {@param subscription} from {@link CyclerViewModel}
     */
    protected void removeSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }

        subscriptions.remove(subscription);
    }


    /**
     * Set {@param unbinder} in {@link CyclerViewModel} after constructor
     */
    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }


    @Override
    public void onAttached() {
        // do nothing
    }

    @Override
    public void onLifeCycleStarted() {
        stopped = false;

        for (Cycler cycler : cyclers) {
            cycler.onLifeCycleStarted();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Cycler cycler : cyclers) {
            cycler.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onLifeCycleStopped() {
        for (Cycler cycler : cyclers) {
            cycler.onLifeCycleStopped();
        }

        stopped = true;
    }


    @Override
    public void onLifeCycleDestroyed() {
        for (Cycler cycler : cyclers) {
            cycler.onLifeCycleDestroyed();
        }

        cyclers.clear();

        for (BasePresenter presenter : presenters) {
            presenter.stopRequest();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }

        subscriptions.unsubscribe();
    }
}
