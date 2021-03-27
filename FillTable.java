package BackEnd;

import java.sql.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;




public class FillTable extends dbManipulation{



    private ArrayList row_data = new ArrayList();
    private Vector columnNamesVector = new Vector();
    private Vector dataVector = new Vector();


    public FillTable(String sql,ArrayList columnNames) {


        //  Connect to an SQL Database, run query, get result set


        try{
            rs = stmt.executeQuery( sql );



            ResultSetMetaData resultSetMetaData = rs.getMetaData(); //interface to that allow functions like getColumnCount/Name/TableName

            int NumColumns = resultSetMetaData.getColumnCount();


            //  Get row data
            while (rs.next())
            {
                ArrayList row = new ArrayList(NumColumns);

                for (int i = 1; i <= NumColumns; i++)
                {
                    row.add( rs.getObject(i) );
                }

                row_data.add( row );
            }
        }
        catch (SQLException e)
        {
            System.out.println( e.getMessage() );
        }



        for (int i = 0; i < row_data.size(); i++)
        {
            ArrayList subArray = (ArrayList)row_data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++)
            {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++ )
            columnNamesVector.add(columnNames.get(i));

    }

    public Vector getColumnNamesVector() {
        return columnNamesVector;
    }

    public Vector getDataVector() {
        return dataVector;
    }

    public Object changeValue(String sql){
        String name="";

        try {
                ResultSet rs = stmt.executeQuery( sql );

            //  Get row data
            while (rs.next())
            {
                name=rs.getString("Name");
            }
        }
        catch (SQLException e)
        {
            System.out.println( e.getMessage() );
        }
        return name;
    }

    public double getTotal(String sql){
        double total=0;
        try{
            rs = stmt.executeQuery( sql );
            while (rs.next())
            {
                total=rs.getDouble("TOTAL");
            }
        }
        catch (SQLException e)
        {
            System.out.println( e.getMessage() );
        }

        return total;
    }

}