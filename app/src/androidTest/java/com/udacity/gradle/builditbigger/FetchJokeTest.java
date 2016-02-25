package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import java.util.concurrent.CountDownLatch;

import timber.log.Timber;

public class FetchJokeTest extends AndroidTestCase {


    private static final String ASYNC_TEST_TAG = "ASYNC_TEST_TAG";
    private static final String TEST_JOKE_STRING = "What's brown and sticky?\n\nA stick!";
    private String mJokeString = null;
    private Exception mError = null;
    private CountDownLatch signal = null;

    public FetchJokeTest() {
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testEndpointGetJoke() throws InterruptedException {

        if (BuildConfig.DEBUG) {
            Timber.v("testEndpointGetJoke()");
            Log.v("FetchJokeTest", "testEndpointGetJoke()");
        }
        FetchJokeTask task = new FetchJokeTask();

        task.setListener(new FetchJokeTask.FetchJokeTaskListener() {
            @Override
            public void onComplete(String result, Exception e) {
                mJokeString = result;
                mError = e;
                signal.countDown();
            }
        }).execute(new Pair<>(getContext(), MainActivity.TEST_JOKE));

        signal.await();

        assertNull(mError);
        Log.v("FetchJokeTest", "mJokeString is " + mJokeString);
        assertFalse(TextUtils.isEmpty(mJokeString));
        assertTrue(mJokeString.equals(TEST_JOKE_STRING));
        Timber.v(ASYNC_TEST_TAG, "Returned joke: " + mJokeString);
    }
}
