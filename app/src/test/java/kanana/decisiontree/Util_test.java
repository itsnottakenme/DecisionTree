package kanana.decisiontree;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dt.types.Util;

import static org.junit.Assert.*;

/**
 * Created by ian on 7/11/2017.
 */

public class Util_test
{
    @Test
    public void addition_isCorrect() throws Exception
    {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void integerListToString_test()
    {
        List<Integer> intList= new ArrayList();
        String string;
        intList.add(1);
        intList.add(2);
        intList.add(5);
        intList.add(70);
        intList.add(-1457);

        string= Util.integerListToString(intList).trim();

        //Assert.assertEquals(string, "1 2 5 70 -1457");
        Assert.assertTrue(string.equals("1 2 5 70 -1457"));
    }

    @Test
    public void stringToIntegerList()
    {
        List<Integer> intList= Util.stringToIntegerList("1 2 5 70 -1457");
        List<Integer> intList2= new ArrayList();
        String string;
        intList2.add(1);
        intList2.add(2);
        intList2.add(5);
        intList2.add(70);
        intList2.add(-1457);


        Assert.assertEquals(intList.size(), intList2.size());

        //compare 2 lists
        for (int i=0; i<intList.size(); i++)
        {
            Assert.assertTrue(intList.get(i).equals(intList2.get(i)));
        }




        //todo: make copy of correct output and then compare!


    }



}   ////END CLASS////



