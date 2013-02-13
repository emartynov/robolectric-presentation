package com.ebuddy;

import android.view.Menu;
import com.ebuddy.events.DownloadedData;
import com.google.inject.AbstractModule;
import com.squareup.otto.Bus;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith (InjectingTestRunner.class)
public class MainActivityTest extends InjectedTest
{
    private MainActivity activity;
    private Bus bus = mock( Bus.class );

    @Before
    public void setUp () throws Exception
    {
        activity = new MainActivity();
        activity.onCreate( null );
    }

    @Test
    public void checkInitialTexts () throws Exception
    {
        assertThat( activity.downloadButton.getText() ).isEqualTo(
                                                                         activity.getResources().getString( R.string.download ) );
    }

    @Test
    public void disableControlWhenDownload ()
    {
        activity.downloadButton.performClick();

        assertThat( activity.downloadButton.isEnabled() ).isFalse();
        assertThat( activity.editUrl.isEnabled() ).isFalse();
    }

    @Test
    public void enableControlsWhenCancel ()
    {
        activity.downloadButton.performClick();

        Menu menu = new TestMenu();
        activity.onCreateOptionsMenu( menu );
        activity.onPrepareOptionsMenu( menu );

        activity.onOptionsItemSelected( menu.findItem( R.id.cancel ) );

        assertThat( activity.downloadButton.isEnabled() ).isTrue();
        assertThat( activity.editUrl.isEnabled() ).isTrue();
    }

    @Test
    public void registerToBusWhenCreated ()
    {
        verify( bus ).register( activity );
    }

    @Test
    public void whenDestroyThenUnregister ()
    {
        activity.onDestroy();

        verify( bus ).unregister( activity );
    }

    @Test
    public void whenDownloadFinishedDetailsActivityIsShownWithCorrectParameters ()
    {
        activity.dataDownloaded( new DownloadedData( "html", new byte[] {} ) );

        ShadowActivity shadowActivity = Robolectric.shadowOf( activity );
        ShadowIntent shadowIntent = Robolectric.shadowOf( shadowActivity.getNextStartedActivity() );

        //fest assert is going to have assertThat().isEqualsTo() fro classes in the next release
        assertEquals( DisplayDownloadedDataActivity.class, shadowIntent.getIntentClass() );
        assertThat( shadowIntent.getCharSequenceExtra( MainActivity.MIMETYPE ) )
                .isEqualTo( "html" );
        assertThat( shadowIntent.getByteArrayExtra( MainActivity.DATA ) )
                .isEqualTo( new byte[] {} );
    }


    @Override
    public AbstractModule getTestModule ()
    {
        return new AbstractModule()
        {
            @Override
            protected void configure ()
            {
                bind( Bus.class ).toInstance( bus );
            }
        };
    }
}

