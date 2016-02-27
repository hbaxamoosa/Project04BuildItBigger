package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.ProgressBar;

import com.baxamoosa.backend.jokeApi.JokeApi;
import com.baxamoosa.jokedisplay.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import timber.log.Timber;

public class FetchJokeTask extends AsyncTask<Pair<Context, Integer>, Void, String> {

    private final static String ENDPOINTS_TAG = "ENDPOINTS_TAG";
    private static JokeApi myApiService = null;
    private FetchJokeTaskListener mListener = null;
    private Exception mError = null;
    private Context context;
    private ProgressBar mSpinner;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (BuildConfig.DEBUG) {
            Timber.v("onPreExecute()");
        }
    }

    @Override
    protected String doInBackground(Pair<Context, Integer>... params) {

        if (BuildConfig.DEBUG) {
            Timber.v("doInBackground(Pair<Context, Integer>... params)");
        }

        context = params[0].first;
        int index = params[0].second;

        if (myApiService == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(context.getString(R.string.api_root_url));

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke(index).execute().getMyJoke();
        } catch (IOException e) {
            mError = e;
            // return e.getMessage();
            return context.getString(R.string.no_server);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (BuildConfig.DEBUG) {
            Timber.v("onPostExecute(String s)");
        }

        if (this.mListener != null) {
            this.mListener.onComplete(s, mError);
        }

        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, s);

        // only starting an activity when the context is prevents an error when running connectedCheck
        if (context instanceof Activity) {
            context.startActivity(intent);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        if (BuildConfig.DEBUG) {
            Timber.v("onProgressUpdate(Void... values)");
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (BuildConfig.DEBUG) {
            Timber.v("onCancelled()");
        }
    }


    public FetchJokeTask setListener(FetchJokeTaskListener listener) {
        if (BuildConfig.DEBUG) {
            Timber.v("setListener(FetchJokeTaskListener listener)");
        }
        this.mListener = listener;
        return this;
    }

    // This listener is used in AsyncTaskAndroidTest to test the AsyncTask
    public interface FetchJokeTaskListener {
        void onComplete(String result, Exception e);
    }
}
