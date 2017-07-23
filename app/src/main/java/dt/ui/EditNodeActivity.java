package dt.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import dt.R;
import dt.types.Node;

/**
 * Created by ian on 7/20/2017.
 */

public class EditNodeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    Button  bOk,
            bCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_node);

        bOk= (Button)findViewById(R.id.ok_button);
        bCancel= (Button)(findViewById(R.id.cancel_button));




        setupListeners();
        return;
    }
    private void setupListeners()
    {
        bOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /**
                 * Use loader to insert new node with  parentId and text
                 */
                return;

            }
        });

    }



        @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {

    }
}
