package com.ebuddy;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import roboguice.activity.RoboActivity;

public class MainActivity extends RoboActivity implements View.OnClickListener
{

    EditText editUrl;
    Button downloadButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );

        editUrl = (EditText) findViewById( R.id.editUrl );
        downloadButton = (Button) findViewById( R.id.downloadButton );

        downloadButton.setOnClickListener( this );
    }

    @Override
    public void onClick ( View view )
    {
    }

    @Override
    public boolean onCreateOptionsMenu ( Menu menu )
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main_activity, menu );

        return true;
    }

    @Override
    public boolean onMenuItemSelected ( int featureId, MenuItem item )
    {
        return false;
    }
}
