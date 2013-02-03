package com.ebuddy;

import org.junit.Before;
import org.junit.Test;

public class MainActivityTest
{
    private MainActivity activity;

    @Before
    public void setUp () throws Exception
    {
        activity = new MainActivity();
        activity.onCreate( null );
    }

    @Test
    public void checkInitialTexts () throws Exception
    {
    }
}
