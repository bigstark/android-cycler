package com.bigstark.cycler.sample;

import android.content.Intent;
import android.util.Log;

import com.bigstark.cycler.Cycler;

/**
 * Created by bigstark on 2016. 11. 24..
 */

public class SampleCycler implements Cycler {

    @Override
    public void onAttached() {
        Log.i("Cycler", "onAttached");
    }

    @Override
    public void onLifeCycleStarted() {
        Log.i("Cycler", "onStarted");
    }

    @Override
    public void onLifeCycleStopped() {
        Log.i("Cycler", "onStopped");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i("Cycler", "onActivityResult");
    }

    @Override
    public void onLifeCycleDestroyed() {
        Log.i("Cycler", "onDestroyed");
    }
}
