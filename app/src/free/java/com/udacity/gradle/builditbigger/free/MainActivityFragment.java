package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.FetchJokeTask;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.Utility;

import timber.log.Timber;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private InterstitialAd mInterstitialAd;
    private View mRoot;

    public MainActivityFragment() {
        if (BuildConfig.DEBUG) {
            Timber.v("MainActivityFragment()");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (BuildConfig.DEBUG) {
            Timber.v("onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");
        }

        mRoot = inflater.inflate(R.layout.fragment_main, container, false);

        // Interstitial Ad
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                if (BuildConfig.DEBUG) {
                    Timber.v("onAdClosed()");
                }
                requestNewInterstitial();
                if (BuildConfig.DEBUG) {
                    Timber.v("requestNewInterstitial()");
                }
                tellJoke();
            }
        });

        if (BuildConfig.DEBUG) {
            Timber.v("requestNewInterstitial()");
        }
        requestNewInterstitial();

        // Banner Ad
        AdView mAdView = (AdView) mRoot.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        // Joke button
        Button jokeButton = (Button) mRoot.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isNetworkAvailable(getContext())) {
                    if (BuildConfig.DEBUG) {
                        Timber.v("jokeButton.setOnClickListener() onClick(View v) Utility.isNetworkAvailable(getContext())");
                    }
                    if (mInterstitialAd.isLoaded()) {
                        if (BuildConfig.DEBUG) {
                            Timber.v("jokeButton.setOnClickListener() onClick(View v) Utility.isNetworkAvailable(getContext()) mInterstitialAd.isLoaded()");
                        }
                        mInterstitialAd.show();
                    } else {
                        if (BuildConfig.DEBUG) {
                            Timber.v("jokeButton.setOnClickListener() onClick(View v) Utility.isNetworkAvailable(getContext()) else");
                        }
                        requestNewInterstitial();  // make sure there is one available next time
                        tellJoke();
                    }
                } else {
                    if (BuildConfig.DEBUG) {
                        Timber.v("jokeButton.setOnClickListener() onClick(View v) else");
                    }
                    CharSequence text = getString(R.string.err_no_network);
                    Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return mRoot;
    }

    private void requestNewInterstitial() {
        if (BuildConfig.DEBUG) {
            Timber.v("requestNewInterstitial()");
        }
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void tellJoke() {
        if (BuildConfig.DEBUG) {
            Timber.v("tellJoke()");
        }
        new FetchJokeTask(mRoot).execute(new Pair<Context, Integer>(getContext(), MainActivity.RANDOM_JOKE));
    }
}
