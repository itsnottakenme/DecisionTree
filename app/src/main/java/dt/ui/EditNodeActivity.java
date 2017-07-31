package dt.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dt.R;
import dt.db.DataSource;
import dt.types.DTIntent;
import dt.types.Node;

/**
 * Created by ian on 7/20/2017.
 */

public class EditNodeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    Button  bOk,
            bCancel;
    EditText etText;
    DataSource mDataSource;
    String mDbFilename;

    //String mIntentAction;       //tells whether to update or insert

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_node);


        bOk= (Button)findViewById(R.id.ok_button);
        bCancel= (Button)(findViewById(R.id.cancel_button));
        etText= (EditText)(findViewById(R.id.text_edittext));

        mDbFilename= getIntent().getExtras().getString(DTIntent.DATABASE_NAME);
        mDataSource = new DataSource(this, mDbFilename);
        mDataSource.open(); //opens DB



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

                String intentAction;
                //String text= etText.getText().toString();
                Node node;
                ContentValues cvNode= new ContentValues();



  //              node= new Node();
                //todo: ARE THESE PASSED IN INTENT????

//                node.setText(etText.getText().toString());
//                node.setParentId(getIntent().getExtras().getInt(Node.PARENT_ID));
                //node.setId(getIntent().getExtras().getInt(Node.ID)); //todo: only needed for update case
                //todo: should any of these be pulled from DB? Perhaps these can be filled out
                //in onCreate() since the activty only deals with a SINGLE NODE


                /**
                 * Use loader to insert new node with  parentId and text
                 * (Loader not needed since just single INSERT/UPDATE
                 */


                intentAction= getIntent().getExtras().getString(DTIntent.ACTION_TYPE);

                if (intentAction.equals(DTIntent.INSERT))
                {
                    cvNode.put(Node.TEXT, etText.getText().toString());
                    cvNode.put(Node.PARENT_ID, getIntent().getIntExtra(Node.PARENT_ID,-1));

                    mDataSource.insertNode(cvNode);
                    /////////////////////////////////////////////
                    //todo; MODIFY!!!!!!
//                    Intent returnIntent;
//
//
//                            returnIntent= new Intent();
//                            setResult(RESULT_OK, returnIntent);
//                            mIsActivityClosing= true;
//                            setResult(RESULT_OK);
                    finish();
                }
                else if (intentAction.equals(DTIntent.UPDATE))
                {
                    Toast.makeText(EditNodeActivity.this, "IMPLEMENT UPDATE!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EditNodeActivity.this, "Error - Unknown intent: "+intentAction , Toast.LENGTH_SHORT).show();
                }

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
