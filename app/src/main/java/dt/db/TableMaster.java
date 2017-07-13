package dt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dt.types.Node;
import dt.types.NodeChildPair;

/**
 * Created by ian on 7/9/2017.
 */

public class TableMaster extends SQLiteOpenHelper
{
    public static final String TAG= TableMaster.class.getName();


    /**
     * Databases
     */
    public static final String      nodes_DB      = "nodes.db",
                                    TEMP_DB      = "tempDb.db",
                                    DEBUG_NOTES_DB = "DEBUG_nodes.db",
                                    DEBUG_TEMP_DB      = "tempDb.db";
    public static final int DATABASE_VERSION = 1;
    /**
     * Node table
     */
    private static final String CREATE_NODE_TABLE =                                  //nodes
            "create table " + Node.TABLE + "("
                            + Node.ID + " integer primary key autoincrement, "
                            + Node.TEXT + " text not null " + " , "
                            + Node.CHILD_IDS + " text, "
                            + Node.PARENT_ID + " integer )";




//    public static final String CREATE_NODECHILDPAIRS_TABLE=
//            "create table " + NodeChildPair.TABLE + "("
//                    + NodeChildPair.NODE_ID + " integer, "
//                    + NodeChildPair.CHILD_ID + " integer, "
//                    + " unique (" + NodeChildPair.NODE_ID +" , "+ NodeChildPair.CHILD_ID + ") "
//                    + " foreign key (" +NodeChildPair.NODE_ID+ ") references " +Node.TABLE+" (" +Node.ID+ ")"
//                    + " foreign key (" +NodeChildPair.CHILD_ID+ ") references " +Node.TABLE+" (" +Node.ID+ ")"
//                    + " )";




    public TableMaster(Context context, String dbFilename)
    {
        super(context, dbFilename, null, DATABASE_VERSION);


        return;
    }



    @Override
    public void onCreate(SQLiteDatabase database) //Only is called if DB does not exist
    {
        database.execSQL(CREATE_NODE_TABLE);
        //database.execSQL(CREATE_NODECHILDPAIRS_TABLE);

        return;
    }


    @Override              //todo: just drops the whole table!!!!
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        return;
    }

    /**
     * Begin database upgrade methods
     *
     */







    public void deleteDb()
    {
        //SQLiteDatabase (new File("lol");)


        return;
    }

    public static void deleteAllTables(Context context, String dbFilename)
    {
        TableMaster dbHelper;
        SQLiteDatabase db;

        dbHelper = new TableMaster(context, dbFilename);
        db = dbHelper.getWritableDatabase();


        db.execSQL("DROP TABLE IF EXISTS " + Node.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NodeChildPair.TABLE);

        db.close();

        return;
    }


}           /////END CLASS//////
