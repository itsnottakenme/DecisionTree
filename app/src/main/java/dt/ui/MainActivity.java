package dt.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import dt.db.DataSource;
import dt.db.NodeProvider;
import dt.db.TableMaster;
import dt.types.DTIntent;
import dt.types.Node;
import dt.R;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>

{
    ListView mChildListView;
    SimpleCursorAdapter mAdapter;

    private DataSource mDataSource;
    private String mDbFilename;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Bundle bundle= new Bundle();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChildListView= (ListView)findViewById(R.id.node_listview);




        //set the filename of the database
       // mDbFilename = getIntent().getStringExtra(NdbIntent.DATABASE_NAME);
        if (mDbFilename == null)
        {
            mDbFilename = TableMaster.nodes_DB;
        }

        mDataSource = new DataSource(this, mDbFilename);
        mDataSource.open(); //opens DB

        //Load dummy values into db //todo: remove for ALPHA
        List<Node> nodes= Node.createTestData();
        for (Node a: nodes)
        {
            mDataSource.createNode(a);
        }




        ///////////////////////////////////////
        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new SimpleCursorAdapter(this/*getActivity()*/,
                R.layout.node_list_item, null,
                new String[] {Node.TEXT, Node.ID },
                new int[] { R.id.node_text, R.id.node_id}, 0);
        mChildListView.setAdapter(mAdapter);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        bundle.putString(DTIntent.ACTION_TYPE, DTIntent.VIEW);
        bundle.putInt(Node.ID, Node.ID_NONE);

        getSupportLoaderManager().initLoader(0, bundle, this);        // 0 indicates default instruction
        /////////////////////////////////////////////////////////

        setupListeners();

        return;
    }

    void setupListeners()
    {

        mChildListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                LinearLayout llItem= (LinearLayout)view;
                int nodeId;
                String nodeText;
                Bundle bundle= new Bundle();

                /**
                 * Get data from clicked item
                 */
                nodeId= Integer.parseInt(((TextView)llItem.findViewById(R.id.node_id)).getText().toString());
                nodeText= ((TextView)llItem.findViewById(R.id.node_text)).getText().toString();

                /**
                 *  todo:
                 *  1) get id of clicked view
                 *  2) load children of clicked node into mChildListView
                 *  3) add clicked view text into SCrollview at top 
                 *
                 */
                bundle.putString(DTIntent.ACTION_TYPE, DTIntent.VIEW);
                bundle.putString(Node.TEXT, nodeText);
                bundle.putInt(Node.ID, nodeId);

                //todo: Put an INTENT in bundle to
                getSupportLoaderManager().initLoader(nodeId , bundle, MainActivity.this);

                return;
            }
        });




        return;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {

        Uri uri= Uri.parse("content://kanana.decisiontree/nodes");
        String intentString;
        int nodeId;
        Loader<Cursor> loaderCursor= null;


        /**
         * Unpack bundle and decide action
         */
        if (args!=null)
        {

            intentString = (String) args.get(DTIntent.ACTION_TYPE);


            if (intentString.equals(DTIntent.VIEW))
            {
                nodeId = (int) args.get(Node.ID);
                loaderCursor = new CursorLoader(this, uri, new String[]{Node.ID, Node.TEXT},
                        "parent_id=" + nodeId, null, null); //todo: change "parent_id=-1"

            } else
            {
                Toast.makeText(getApplicationContext(), "Not handled:" + intentString, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Bundle is null", Toast.LENGTH_SHORT).show();
        }




        return loaderCursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mAdapter.swapCursor(null);
    }
}       ////END CLASS////
