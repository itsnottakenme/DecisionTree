package dt.types;

import java.util.List;

/**
 * Created by ian on 7/9/2017.
 */

public class Node
{
    //Table infromation for SQL database
    public static final String
            TABLE =        "nodes",
            ID =            "_id",    //primary key
            TEXT =         "text", //name of notebook   //todo change to COL_TITLE!!!
            PARENT=         "parent";











    private int id;

    private int parent;
    private List children;


    String text;



}
