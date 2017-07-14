package dt.db;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dt.types.Node;
import dt.types.Util;

import static org.junit.Assert.*;

/**
 * Created by ian on 7/12/2017.
 */
public class DataSourceTest
{
    String TAG= "DataSourceTest";
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
        Log.d(TAG,"teardown");
        //super.tearDown();    //To change body of overridden methods use File | Settings | File Templates.

        mDataSource.close();
        context.deleteDatabase(TableMaster.DEBUG_NOTES_DB);

        return;
    }




    //todo: make CREATE NOT UPDATE!!!!!!!!!
    @Test
    public void createNode() throws Exception
    {
        Log.d(TAG,"createNode()");

        Node    node= new Node(),
                nodeExpected;


        if (node.equals(new Node()))
        {
            Log.d(TAG,"createNode()");
        }

        node.setId(1);
        node.setParentId(node.NO_ID);
        node.setText("happpppppy");
        node.addChild(2);

        nodeExpected= mDataSource.createNode(node);
        //Assert.assertTrue(nodeExpected.equals(node));
        Assert.assertEquals(nodeExpected.getId(), node.getId());
        Assert.assertEquals(nodeExpected.getText(), node.getText());
        Assert.assertEquals(nodeExpected.getParentId(), node.getParentId());
        Assert.assertEquals(Util.integerListToString(nodeExpected.getChildIdList()), Util.integerListToString(node.getChildIdList()));
        //Assert.assertTrue(!(nodeExpected.equals(new Node())) );






    }


    @Test
    public void createOrUpdateNode2() throws Exception
    {
        Log.d(TAG,"createOrUpdateNode2");



    }


}
























