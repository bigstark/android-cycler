package com.bigstark.cycler;

import android.content.Intent;

/**
 * Created by bigstark on 2016. 11. 24..
 *
 * For preventing multi implementing method.
 */

public class SimpleCycler implements Cycler {

    @Override
    public void onAttached() {
        // do nothing
    }

    @Override
    public void onLifeCycleStarted() {
        // do nothing
    }

    @Override
    public void onLifeCycleStopped() {
        // do nothing
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // do nothing
    }

    @Override
    public void onLifeCycleDestroyed() {
        // do nothing
    }
}
