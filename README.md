# android-cycler
[![license](https://img.shields.io/hexpm/l/plug.svg)](LICENSE)
[![jitpack](https://img.shields.io/badge/jitpack-1.0-green.svg)](https://jitpack.io/#bigstark/android-cycler)

It can help configure project base.

##Include your project
add build.gradle
```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
```
dependencies {
    compile 'com.github.bigstark:android-cycler:1.1'
}
```

##Usage
Implement Cycler for using life cycle
```java
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
```

It can be used like this.
```java
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
```


License
-------

    Copyright 2016 BigStarK

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
