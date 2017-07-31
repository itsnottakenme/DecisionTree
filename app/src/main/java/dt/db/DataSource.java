package dt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import dt.types.Node;

/**
 * Created by ian on 7/9/2017.
 */

public class DataSource
{
    ///////////////////////////////STATIC//////////////////////////////////////////////
    private static int FALSE = 0,
            TRUE = 1;
    ///////////////////////////////////////////////////////////////////////////////////

    private SQLiteDatabase mDatabase;
    private String mDbFilename;
    private TableMaster dbMaster;
    private Context mContext;


    public DataSource(Context context, String fileName)
    {
        dbMaster = new TableMaster(context, fileName);
        mContext = context;
        mDbFilename = fileName;
        init();


        return;
    }


    /**
     * 1) Is this needed?
     * 2) hjhjjh
     */

    private void init()
    {

        return;
    }

    public void open() throws SQLException
    {
        mDatabase = dbMaster.getWritableDatabase();
        return;
    }


    public void close()  //TODO: DOUBLECHECK TO MAKE SURE I AM CALLING THIS (onPause???)
    {
        dbMaster.close();
        return;
    }


    public Node createNode(Node node)
    {
        Node newNode = null;
        ContentValues nodeRow;

        Cursor cursor;
        long insertId = -1;       //TO Avert compiler error


        if (node != null)
        {
            nodeRow = node.toContentValues();

            try
            {
                mDatabase.beginTransaction();
                insertId = mDatabase.insert(Node.TABLE, null, nodeRow);
                mDatabase.setTransactionSuccessful();
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                mDatabase.endTransaction();
            }

            cursor = mDatabase.query(Node.TABLE,
                    Node.ALL_COLUMNS, Node.ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            newNode = Node.fromCursor(cursor);
            cursor.close();
        }


        return newNode;
    }


    public long insertNode(ContentValues cvNode)
    {
        long insertId = -1;       //TO Avert compiler error

        try
        {
            mDatabase.beginTransaction();
            insertId = mDatabase.insert(Node.TABLE, null, cvNode);
            mDatabase.setTransactionSuccessful();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            mDatabase.endTransaction();
        }
        return insertId;
    }




    /**
     * Returns a cursor to all nodes that have the given parent_id
     * @param parent_id
     * @return
     */
    Cursor getNodes(String selection)
    {

        return mDatabase.query(Node.TABLE,
                new String[]{Node.ID, Node.TEXT}, selection, null, null, null, null);

    }






















    /***
     *
     * @param node
     * @return the id of a newly created record or 0 if not successful
     */
//    public long createNodeWithProvider(Node node)
//    {
//        ContentValues nodeRow;
//
//
//        long insertId = -1;       //TO Avert compiler error
//
//
//        if (node != null)
//        {
//            nodeRow = node.toContentValues();
//
//            try
//            {
//                mDatabase.beginTransaction();
//                insertId = mDatabase.insert(Node.TABLE, null, nodeRow);
//                mDatabase.setTransactionSuccessful();
//            } catch (Exception e)
//            {
//                e.printStackTrace();
//            } finally
//            {
//                mDatabase.endTransaction();
//            }
//        }
//
//
//        return insertId;
//    }



    public Node updateNode(Node node)       //todo: code is EXACT duplicate of create node
    {
        ContentValues contentValues;
        Cursor cursor;
        int result;

        contentValues= node.toContentValues();
        //TODO: does this create a new row if id is not found in database? updateOnConflict
        result= mDatabase.update(Node.TABLE, contentValues, Node.ID+ "=" +node.getId(), null);
        result++;
        String  query = "select * from " +Node.TABLE
                + " where " +Node.ID+ "=" +node.getId();

        //Receive update notebook and assign
        cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        node= Node.fromCursor(cursor);
        cursor.close();

        return node;
    }
















}       ///// END CLASS /////