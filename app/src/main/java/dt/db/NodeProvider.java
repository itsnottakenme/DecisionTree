package dt.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ian on 7/16/2017.
 */

/**
 * This ContentProvider needs to handle
 * 1) Insert node
 * 2) Delete Node
 * 3) Query for all children of a parent node
 * 4) Query for a single node's data
 * 5)
 *
 *
 */
public class NodeProvider extends ContentProvider
{

    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        sUriMatcher.addURI("dt.db.NodeProvider", "table3", 1);

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.example.app.provider/table3/3" matches, but
         * "content://com.example.app.provider/table3 doesn't.
         */
        sUriMatcher.addURI("com.example.app.provider", "table3/#", 2);
    }


    DataSource mDataSource;

    @Override
    public boolean onCreate()
    {
        mDataSource= new DataSource(getContext(), TableMaster.nodes_DB);
        mDataSource.open();


        return false;
    }

    /**
     *
     * @param uri
     * @param strings
     * @param selection for the where clause, for example: Node.ID + " = " + parent_id
     * @param strings1
     * @param s1
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String selection, @Nullable String[] strings1, @Nullable String s1)
    {
        /**
         * todo: learn how to parse the URI and then translate the arguments into
         * SQL queries. ACTUALLY the arguments should be able to be passed as is
         * into an SQL QUERY. I don't need DataSource's help!!!!!!!!!!!
         *
         * NOOOOOOOOOOOOO NOOOOOOOOOO ALL WRONG!!!!!!!
         * This will ONLY be used to populate a LiztView THEREFORE the URI doesn't
         * need to be handled as there is only a single case where it is needed - CursorAdaper
         *
         */

        return mDataSource.getNodes(selection);


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues)
    {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings)
    {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings)
    {
        return 0;
    }
}
