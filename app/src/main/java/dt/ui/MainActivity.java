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
import android.util.Log;
import android.widget.ListView;


import java.util.List;

import dt.db.DataSource;
import dt.db.NodeProvider;
import dt.db.TableMaster;
import dt.types.Node;
import dt.R;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>

{
    ListView mListView;
    SimpleCursorAdapter mAdapter;

    private DataSource mDataSource;
    private String mDbFilename;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView= (ListView)findViewById(R.id.node_listview);








        //set the filename of the database
       // mDbFilename = getIntent().getStringExtra(NdbIntent.DATABASE_NAME);
        if (mDbFilename == null)
        {
            mDbFilename = TableMaster.nodes_DB;
        }

        mDataSource = new DataSource(this, mDbFilename);
        mDataSource.open(); //opens DB

        //Load dummy values into db
        List<Node> nodes= Node.createTestData();
        for (Node a: nodes)
        {
            mDataSource.createNode(a);
        }


        //mNotebookList = mDatasource.getAllNotebooks();

//        mAdapter = new NotebookAdapter(this, R.layout.notebook_item, (ArrayList) mNotebookList, mDatasource);
//        mListView.setAdapter(mAdapter);





        ///////////////////////////////////////
        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new SimpleCursorAdapter(this/*getActivity()*/,
                R.layout.node_list_item, null,
                new String[] {Node.TEXT/*, Contacts.CONTACT_STATUS */},
                new int[] { R.id.node_text/*, android.R.id.text2 */}, 0);

        mListView.setAdapter(mAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getSupportLoaderManager().initLoader(0, null, this);



        /////////////////////////////////////////////////////////






        return;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.



//         //SHOWS AVAILABLE ContentProviders
//        for (PackageInfo pack : getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS)) {
//            ProviderInfo[] providers = pack.providers;
//            if (providers != null) {
//                for (ProviderInfo provider : providers) {
//                    Log.d("Example", "provider: " + provider.authority);
//                }
//            }
//        }

        Uri uri= Uri.parse("content://kanana.decisiontree/nodes");

        return new CursorLoader(this, uri/*Node.CONTENT_URI*//*"content://dt.db.NodeProvider"*/, new String[]{Node.ID, Node.TEXT},
                                "parent_id=-1", null,  null); //todo: change "parent_id=-1"

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
