package dt.types;

import android.content.ContentValues;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.ExecutionError;
import android.util.Log;

import org.junit.After;
import org.junit.Before;

import dt.db.DataSource;
import dt.db.TableMaster;

/**
 * Created by ian on 7/30/2017.
 */

public class NodeTest
{
    @Before
    public void setUp() throws Exception
    {

        return;
    }


    @After
    public void tearDown() throws Exception
    {
        return;
    }


    /**
     * todo: this method is DUMB and unneccessary I believe. It only helps maintain retarded
     * object-relational model degeneratism
     * @throws Exception
     */
    public void toContentValuesTest() throws Exception
    {
        Node node= new Node();

        node.setParentId(-1);
        node.setId(1);
        node.setText("h");
        //node.




    }


}
