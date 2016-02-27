package com.udacity.gradle.builditbigger.paid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.BuildConfig;
import com.udacity.gradle.builditbigger.FetchJokeTask;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.Utility;

import timber.log.Timber;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private View mRoot;

    public MainActivityFragment() {
        if (BuildConfig.DEBUG) {
            Timber.v("MainActivityFragment()");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_main, container, false);

        Button jokeButton = (Button) mRoot.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isNetworkAvailable(getContext())) {
                    tellJoke();
                } else {
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = getString(R.string.err_no_network);
                    Toast toast = Toast.makeText(getContext(), text, duration);
                    toast.show();
                }
            }
        });

        return mRoot;
    }

    public void tellJoke() {
        new FetchJokeTask(mRoot).execute(new Pair<Context, Integer>(getContext(), MainActivity.RANDOM_JOKE));
    }
}