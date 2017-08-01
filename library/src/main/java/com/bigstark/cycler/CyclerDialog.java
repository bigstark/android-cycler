package com.bigstark.cycler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import com.bigstark.cycler.mvp.BasePresenter;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bigstark on 2016. 12. 2..
 */

public class CyclerDialog extends Dialog implements Cycler, CyclerManager {

    private WeakReference<Activity> weakActivity;

    private Set<Cycler> cyclers = new HashSet<>();
    private Set<BasePresenter> presenters = new HashSet<>();
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private Unbinder unbinder;

    private boolean stopped = false;

    @Override
    public boolean isStopped() {
        return stopped;
    }



    // Dialog needs Activity's context
    public CyclerDialog(Activity activity) {
        super(activity);
        weakActivity = new WeakReference<>(activity);
    }

    /**
     * Add {@param cycler} in {@link CyclerDialog}
     *
     * It helps object that implements cycler take Dialog's Life Cycle.
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
     * Remove {@param cycler} from {@link CyclerDialog}
     */
    protected void removeCycler(Cycler cycler) {

        if (cycler == null) {
            return;
        }

        cyclers.remove(cycler);
    }


    /**
     * Add {@param presenter} in {@link CyclerDialog}
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
     * Remove {@param presenter} from {@link CyclerDialog}
     */
    protected void removePresenter(BasePresenter presenter) {
        if (presenter == null) {
            return;
        }

        presenters.remove(presenter);
    }


    /**
     * Add {@param subscription} in {@link CyclerDialog}
     */
    protected void addSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }

        subscriptions.add(subscription);
    }


    /**
     * Remove {@param subscription} from {@link CyclerDialog}
     */
    protected void removeSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }

        subscriptions.remove(subscription);
    }


    /**
     * Set {@param unbinder} in {@link CyclerDialog} after constructor
     */
    protected void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }


    protected Activity getActivity() {
        return weakActivity == null ? null : weakActivity.get();
    }


    protected boolean isActivityFinishing() {
        return getActivity() == null || getActivity().isFinishing();
    }


    @Override
    public void onAttached() {
        // Do Nothing
    }

    @Override
    public void onLifeCycleStarted() {
        // Do Nothing
    }

    @Override
    public void onLifeCycleStopped() {
        // Do Nothing
    }

    @Override
    protected void onStart() {
        super.onStart();
        stopped = false;

        for (Cycler cycler : cyclers) {
            cycler.onLifeCycleStarted();
        }
    }

    @Override
    protected void onStop() {
        for (Cycler cycler : cyclers) {
            cycler.onLifeCycleStopped();
        }

        stopped = true;
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Cycler cycler : cyclers) {
            cycler.onActivityResult(requestCode, resultCode, data);
        }
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
