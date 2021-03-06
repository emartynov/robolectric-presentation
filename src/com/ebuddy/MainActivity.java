package com.ebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ebuddy.events.DownloadedData;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import javax.inject.Inject;


@ContentView( R.layout.main )
public class MainActivity extends RoboActivity implements View.OnClickListener
{
    static final String MIMETYPE = "mimetype";
    static final String DATA = "data";

    @InjectView(R.id.editUrl)
    EditText editUrl;

    @InjectView(R.id.downloadButton)
    Button downloadButton;

    @Inject
    Bus bus;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        downloadButton.setOnClickListener( this );

        bus.register( this );
    }

    @Override
    public void onClick ( View view )
    {
        startDownload();
    }

    private void startDownload ()
    {
        //download
        downloadButton.setEnabled( false );
        editUrl.setEnabled( false );
    }

    @Override
    public boolean onCreateOptionsMenu ( Menu menu )
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main_activity, menu );

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu ( Menu menu )
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected ( MenuItem item )
    {
        if ( item.getItemId() == R.id.cancel )
        {
            stopDownload();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected( item );
        }
    }

    private void stopDownload ()
    {
        //cancel download
        downloadButton.setEnabled( true );
        editUrl.setEnabled( true );
    }

    @Override
    protected void onDestroy ()
    {
        bus.unregister( this );
        super.onDestroy();
    }

    @Subscribe
    public void dataDownloaded ( DownloadedData data )
    {
        stopDownload();

        Intent childActivity = new Intent( this, DisplayDownloadedDataActivity.class );
        childActivity.putExtra( MIMETYPE, data.getMimeType() );
        childActivity.putExtra( DATA, data.getData() );
        startActivity( childActivity );
    }
}
