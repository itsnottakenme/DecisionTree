package dt.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ian on 7/11/2017.
 */

public class Util
{
    public static String integerListToString(List<Integer> list)
    {
        StringBuilder sb = new StringBuilder();
        for (Integer i : list)
        {
            sb.append(i);
            sb.append(" ");
        }

        return sb.toString();
    }

    public static List<Integer> stringToIntegerList(String string)
    {
        List<String> stringList= Arrays.asList(string.split(" "));
        List<Integer> intList= new ArrayList();

        for (String s: stringList)
        {
            intList.add(Integer.valueOf(s));
        }



        return intList;
    }




} //// END CLASS ////
