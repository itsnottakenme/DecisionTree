package dt.ui;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
    /**
     * id for Loader has to be unique. These things should be contained in bundle

    private static final int    LOADER_LISTVIEW=100,
                                LOADER_PARENTID=101;
     */


    LinearLayout mLlParents;
    ListView mChildListView;
    SimpleCursorAdapter mAdapter;
    Button bAdd;

    private DataSource mDataSource;
    private String mDbFilename;
    private int mParentId=-1; //todo: Find a better way. Probably subclass ScrollView and then
                              //get from there
                              // the id of the bottom-most parent

    private int mLoaderCounterId=0; //gives the unique id for each loader instance


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Bundle bundle= new Bundle();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChildListView= (ListView)findViewById(R.id.node_listview);
        bAdd= (Button)findViewById(R.id.add_button);



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
        /**
         * todo: need have parentId here in case returning from EditNoteActivity. How
         * do I persist that data? Use Loader to retrieve this?
         *
         * Nooooooooooooooooo! ONly a single record query. Instead get it from the
         * main thread and then store it in SharedPreferences
         */

        bundle.putInt(Node.ID, Node.ID_NONE);

        getSupportLoaderManager().initLoader(mLoaderCounterId++, bundle, this);        // 0 indicates default instruction
        /////////////////////////////////////////////////////////

        setupListeners();

        return;
    }

    /**
     * ASSERT nodeText != null
     *  adds new TextView
     *
     * ASSERT nodeText == null
     *  method does nothing
     *
     *
     *      todo: Not sure if current way makes sense or not.. maybe rename to addScrollviewChild
     *
     * @param nodeId
     * @param nodeText
     */
    private void updateParentScrollView(int nodeId, String nodeText)
    {
        TextView tvParent;
        mLlParents= (LinearLayout)findViewById(R.id.linearlayout_parents);

        if (nodeText!=null)
        {
            tvParent = new TextView(this);
            tvParent.setText(nodeId + ": " + nodeText);
            tvParent.setId(nodeId);     //
            mLlParents.addView(tvParent);
        }

        return;

    }
    private void setupListeners()
    {

        mChildListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            /**
             * This method reloads the activity using the parent id of the clicked item in ListView
             * @param parent
             * @param view
             * @param position
             * @param id
             */
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
                mParentId= nodeId; //when reloads set next parent id
                nodeText= ((TextView)llItem.findViewById(R.id.node_text)).getText().toString(); //tod: not needed I think

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

                //todo: add loader LOADER_LISTVIEW for loader cases

                //todo: Put an INTENT in bundle to
                getSupportLoaderManager().initLoader(mLoaderCounterId++, bundle, MainActivity.this);

                return;
            }
        });

        /**
         * Long clicking item -> load edit activity
         */
        mChildListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
                {
                    Intent intent= new Intent(getApplicationContext(), EditNodeActivity.class);
                    LinearLayout llItem= (LinearLayout)view;
                    int nodeId;
                    String nodeText;

                    nodeText= ((TextView)llItem.findViewById(R.id.node_text)).getText().toString(); //tod: not needed I think
                    nodeId= Integer.parseInt(((TextView)llItem.findViewById(R.id.node_id)).getText().toString());
                    intent.putExtra(DTIntent.ACTION_TYPE, DTIntent.UPDATE);
                    intent.putExtra(Node.ID, nodeId);
                    intent.putExtra(Node.PARENT_ID, mParentId);
                    intent.putExtra(Node.TEXT, nodeText);
                    intent.putExtra(DTIntent.DATABASE_NAME, mDbFilename);
                    startActivityForResult(intent,0);   //todo: add requestcode if needed

                    return true;
                }
        });

        /**
         * Launches EditNoteActivity with a new note
         */
        bAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent= new Intent(getApplicationContext(), EditNodeActivity.class);

                intent.putExtra(DTIntent.ACTION_TYPE, DTIntent.INSERT);
                intent.putExtra(Node.PARENT_ID, mParentId);
                intent.putExtra(Node.ID, -1);
                //todo: need to add parentId for new node....
                intent.putExtra(Node.TEXT, "");
                intent.putExtra(DTIntent.DATABASE_NAME, mDbFilename);
                startActivityForResult(intent,0);   //todo: add requestcode if needed

                return;

            }
        });




        return;
    }


    @Override
    public void onBackPressed()
    {
        /***
         *
         */
        Bundle bundle= new Bundle();
        int children= 0;        //number of child views
        int nodeId;

        if (mLlParents!= null)
        {
            children=  mLlParents.getChildCount();
        }

        if (children == 0)
        {       //assert: scrollview empty
            super.onBackPressed();
        }
        else
        {
            mLlParents.removeViewAt(children-1); //todo: 1 off error????????
            children--;
            //Get data to reload child view
            if (children==0)
            {
                nodeId=-1;  //no parents left so just reload top level nodes
            }
            else
            {
                TextView tvItem= (TextView)mLlParents.getChildAt(children-1);
                nodeId= tvItem.getId();

//                //LinearLayout llItem= (LinearLayout)mLlParents.getChildAt(children-1);
//                TextView tvId= (TextView)llItem.findViewById(R.id.node_id);
//                String text= tvId.getText().toString();
//                nodeId= Integer.parseInt(text);

                //nodeId = Integer.parseInt(((TextView) mLlParents.getChildAt(children-1).findViewById(R.id.node_id)).getText().toString());

            }
            bundle.putInt(Node.ID, nodeId);
           // bundle.putString(Node.TEXT,);
            bundle.putString(DTIntent.ACTION_TYPE, DTIntent.VIEW);
            getSupportLoaderManager().initLoader(mLoaderCounterId++, bundle, MainActivity.this);

        }



        return;
    }

    private void updateListView()
    {

    }


    @Override
    protected void onPause()
    {

        super.onPause();
    }

    /**
     * Assert: Reloads children listview
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Bundle bundle= new Bundle();

        //reload node listview
        bundle.putInt(Node.ID, mParentId);
        bundle.putString(DTIntent.ACTION_TYPE, DTIntent.VIEW);
        getSupportLoaderManager().initLoader(mLoaderCounterId++, bundle, MainActivity.this);
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
                if (args != null)
                {

                    intentString = (String) args.get(DTIntent.ACTION_TYPE);


                    if (intentString.equals(DTIntent.VIEW))
                    {
                        nodeId = (int) args.get(Node.ID);
                        loaderCursor = new CursorLoader(this, uri, new String[]{Node.ID, Node.TEXT},
                                "parent_id=" + nodeId, null, null); //todo: change "parent_id=-1"
                        if (nodeId > 0)
                        {
                            //todo: this is done on the main thread I believe. Only CursorLoaer is
                            //run in a different thread

                            //todo: this should not be called for onBackPressed() case MAYBEEEEEEEEE
                            updateParentScrollView(nodeId, (String) args.get(Node.TEXT));
                        }
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "Not handled:" + intentString, Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    Toast.makeText(getApplicationContext(), "Bundle is null", Toast.LENGTH_SHORT).show();
                }



        return loaderCursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        /**
         * todo: Need to create cases for different loader calls to NodeProvider
         * 1) Query for ListView items (mAdapter)
         * 2) Query for parentId of items in ListView todo: many ways what is best???
         *  That's all for this class I think
         */


        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mAdapter.swapCursor(null);
    }
}       ////END CLASS////
