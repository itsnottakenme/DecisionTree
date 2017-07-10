package dt.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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


}       ///// END CLASS /////