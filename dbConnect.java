package BackEnd;

import GUI.Nav;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public  class dbConnect {
    private  Connection connect;
    private static boolean instance_flag=false;


    public static dbConnect getInstance(){
        if (instance_flag){
            PopUp.DisplayMessage(new JFrame(),"ONLY 1 INSTANCE AT A TIME",
                    "ERROR",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        else {
            instance_flag=true;
            return new dbConnect();
        }
    }
    private dbConnect(){
        Connect();
    }

    public void Connect(){
        try{
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            }
            catch(Exception e){}
            String url = "jdbc:sqlserver://localhost:1433;databaseName=S_Sandooyea";
            connect = DriverManager.getConnection(url,"SA","SANd0456");

        }

        catch(Exception e){
            //System.out.println("Not Connected");
            //System.out.println(e.getMessage());
        }
    }
    public  Connection getConnection(){
        Connect();
        return connect;
    }

    public void CloseConnection(){
        try{
            connect.close();
        }
        catch(Exception e){}
    }
}
