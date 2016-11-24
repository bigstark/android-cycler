package com.bigstark.cycler;

import android.content.Intent;

/**
 * Created by bigstark on 2016. 11. 24..
 *
 * Create the Cycler concept for using Activity's or Fragment's Life Cycle in the other object.
 */

public interface Cycler {

    /**
     * It is called when BaseActivity add the cycler.
     */
    public void onAttached();


    /**
     * It is called when Life Cycle is started.
     */
    void onLifeCycleStarted();


    /**
     * It is called when Life Cycle is stopped.
     */
    void onLifeCycleStopped();



    /**
     * It is called when activity called onActivityResult
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);


    /**
     * It is called when Life Cycle is destroyed
     */
    void onLifeCycleDestroyed();

}
