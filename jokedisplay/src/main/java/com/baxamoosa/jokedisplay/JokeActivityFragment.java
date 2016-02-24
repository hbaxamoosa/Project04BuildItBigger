package com.baxamoosa.jokedisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import timber.log.Timber;

public class JokeActivityFragment extends Fragment {

    public JokeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            // Timber.v("onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");
            Log.v("JokeActivityFragment", "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");
        }
        View root = inflater.inflate(R.layout.fragment_joke_activity, container, false);
        Intent intent = getActivity().getIntent();
        String joke = intent.getStringExtra(JokeActivity.JOKE_KEY);
        if (BuildConfig.DEBUG) {
            Timber.v("joke is " + joke);
            Log.v("JokeActivityFragment", "joke is " + joke);
        }
        TextView jokeTextView = (TextView) root.findViewById(R.id.joke_textview);
        if (joke != null && joke.length() != 0) {
            jokeTextView.setText(joke);
            if (BuildConfig.DEBUG) {
                // Timber.v("jokeTextView.setText(joke)");
                Log.v("JokeActivityFragment", "jokeTextView.setText(joke)");
            }
        }

        return root;
    }
}
