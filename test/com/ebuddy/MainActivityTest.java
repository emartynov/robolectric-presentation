package com.ebuddy;

public class MainActivityTest
{
    private MainActivity activity;

    public void setUp () throws Exception
    {
        activity = new MainActivity();
        activity.onCreate( null );
    }

    public void checkInitialTexts () throws Exception
    {
    }
}
