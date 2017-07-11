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


//    public Node createOrUpdateNode(Node node)
//    {
//
//        ContentValues contentValues;
//        Cursor cursor;
//
//        contentValues= notebook.toContentValues();
//        mDatabase.update(Notebook.TABLE, contentValues, Notebook.ID+ "=" +notebook.getGuid(), null);
//
//        String  query = "select * from " +Notebook.TABLE
//                + " where " +Notebook.ID+ "=" +notebook.getGuid();
//
//        //Receive update notebook and assign
//        cursor = mDatabase.rawQuery(query, null);
//        cursor.moveToFirst();
//        notebook= Notebook.fromCursor(cursor);
//        cursor.close();
//
//        return notebook;
//
//
//        return
//    }
















}       ///// END CLASS /////