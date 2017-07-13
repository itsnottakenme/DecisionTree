package dt.db;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ian on 7/12/2017.
 */
public class DataSourceTest
{
    private DataSource mDataSource;
    private Context context;


    private Activity activity;


    @Before
    public void setUp() throws Exception
    {
        Log.d("h","setup");
        context= InstrumentationRegistry.getTargetContext();
        mDataSource = new DataSource(context, TableMaster.DEBUG_NOTES_DB);
        mDataSource.open();



        return;
    }


    @After
    public void tearDown() throws Exception
    {
        Log.d("h","teardown");
        //super.tearDown();    //To change body of overridden methods use File | Settings | File Templates.

        mDataSource.close();
        context.deleteDatabase(TableMaster.DEBUG_NOTES_DB);

        return;
    }



    @Test
    public void createOrUpdateNode() throws Exception
    {
        Log.d("h","createOrUpdateNode");

    }


    @Test
    public void createOrUpdateNode2() throws Exception
    {
        Log.d("h","createOrUpdateNode2");

    }


}