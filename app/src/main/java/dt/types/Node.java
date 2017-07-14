package dt.types;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian on 7/9/2017.
 * This class is just a wrapper for a Nodes table row
 */

public class Node
{


    //Table infromation for SQL database
    public static final String
                                TABLE =        "nodes",
                                ID =           "_id",    //primary key
                                TEXT =         "text", //name of notebook   //todo change to COL_TITLE!!!
                                PARENT_ID=     "parent_id",
                                CHILD_IDS=     "child_ids";

    public static final String[] ALL_COLUMNS=
                               {ID, TEXT, PARENT_ID, CHILD_IDS};



    //Constants
    public static final int NO_ID= -1;











    /**
     * Member variables
     */
    private int id;         //id for database. (id == 0) means NO ID
    private int parentId;
    private List<Integer> childIdList;
    private String text;


    public Node()
    {
        id=0;
        childIdList= new ArrayList<>();

        return;
    }



    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getParentId()
    {
        return parentId;
    }

    public void setParentId(int parent)
    {
        this.parentId = parent;
    }

    public List<Integer> getChildIdList()
    {
        return childIdList;
    }

    public void setChildIdList(List<Integer> childIdList)
    {
        this.childIdList = childIdList;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void addChild(int id)
    {
        childIdList.add(id);
        return;
    }
    public void removeChild(int id)
    {
        childIdList.remove((Integer)id);  //todo double check correct method called!!
        return;
    }




    @Override
    public boolean equals(Object aNode) //todo: NEVER CALLED. WHY?????????
    {
        Node node= (Node)aNode;
        boolean result= false;


        if(
                getId() == node.getId()
            &&  getParentId() == node.getParentId()
            &&  getText() == node.getText()
            &&  Util.integerListToString(node.getChildIdList()).equals(Util.integerListToString(getChildIdList()))
          )
        {
           result= true;
        }
        return result;
    }









        /**
         * todo: GUID is currently only added  if (getGuid()>0). What is the optimal way?
         * @return
         */
    public ContentValues toContentValues()
    {
        ContentValues nodeRow;


        nodeRow = new ContentValues();



        nodeRow.put(Node.ID, getId());
        nodeRow.put(Node.PARENT_ID, getParentId());
        nodeRow.put(Node.TEXT, getText());
        nodeRow.put(Node.CHILD_IDS, Util.integerListToString(getChildIdList()));



        return nodeRow;
    }


    public static Node fromCursor(Cursor cursor)
    {
        Node node = new Node();


        node.setId(cursor.getInt(cursor.getColumnIndex(Node.ID)));
        node.setParentId(cursor.getInt(cursor.getColumnIndex(Node.PARENT_ID)));
        node.setText(cursor.getString(cursor.getColumnIndex(Node.TEXT)));
        node.setChildIdList(Util.stringToIntegerList(cursor.getString(cursor.getColumnIndex(Node.CHILD_IDS))));


        return node;
    }




//    public static Node createTestData
//    {
//        List<>
//        Node topLevelNodes= new Node();
//
//
//        topLevelNodes.parent= null; //top level node
//        topLevelNodes.text="I have pain";
//
//
//
//
//
//    }


}
