# Math App
This app was built to be a tool for math and algorithms. To be able to enter an equation into an field and get a step by step on how to solve an equation or simplify it.

## API Keys
I have a Wolfram id which is my email, but I don't ever use it in my app, the api only needs the url and the key

WOLFRAM_KEY = "9QLYGR-UV2EUVQ8XK"

add this to WolframConstants:
public static final String WOLFRAM_URL = "http://api.wolframalpha.com/v2/query?";
public static final String WOLFRAM_KEY = BuildConfig.WOLFRAM_KEY;

WolframConstants should look like this:

package com.example.kstedman.mathapplication;

public final class WolframConstants {

    public static final String WOLFRAM_KEY = BuildConfig.WOLFRAM_KEY;
    public static final String WOLFRAM_ID = BuildConfig.WOLFRAM_ID; //ID not in use
    public static final String WOLFRAM_URL = "http://api.wolframalpha.com/v2/query?";

    public static final String PREFERENCES_TOPIC_KEY = "Location";

    public static final String FIREBASE_CHILD_SEARCHED_TOPIC = "searchedTopic";

    public static final String FIREBASE_CHILD_QUESTIONS = "questions";
}
//END

## Further Exploration
I would like to build out maybe a visual map of different basic equations, maybe have a input output of that equation. Enter a variable and see how it would change the equation.

#### Created By Keith Stedman
This is a MIT License and is free to use.   