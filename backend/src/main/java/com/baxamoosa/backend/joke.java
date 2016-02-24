package com.baxamoosa.backend;

import com.baxamoosa.JokeSource;

public class Joke extends JokeSource {

    private static String joke;

    public String getMyJoke() {
        return joke;
    }

    public static void setMyJoke(int index) {
        JokeSource jokeSource = new JokeSource();
        joke = jokeSource.getJoke(index);
    }
}
