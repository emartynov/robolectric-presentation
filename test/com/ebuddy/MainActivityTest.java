package com.ebuddy;

import android.view.Menu;
import com.google.inject.AbstractModule;
import com.squareup.otto.Bus;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith ( InjectingTestRunner.class)
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

        Menu menu = new TestMenu(  );
        activity.onCreateOptionsMenu( menu );
        activity.onPrepareOptionsMenu( menu );

        activity.onOptionsItemSelected( menu.findItem( R.id.cancel ) );

        assertThat( activity.downloadButton.isEnabled() ).isTrue();
        assertThat( activity.editUrl.isEnabled() ).isTrue();
    }

    @Test
    public void registerToBusWhenCreated ()
    {
        verify( bus ).register(activity);
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
        };  //To change body of implemented methods use File | Settings | File Templates.
    }
}

