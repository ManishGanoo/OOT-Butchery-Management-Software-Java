package BackEnd;

import java.sql.SQLException;
import java.util.ArrayList;

public class FillCBcustomer extends dbManipulation{
    private ArrayList customers=new ArrayList<Customer>();

    public FillCBcustomer(){
        super();
    }

    public void Query(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                customers.add(new Customer(rs.getInt("CustID"),rs.getString("Name")));
            }
        }
        catch (SQLException e){}

    }

    public ArrayList getCustomers() {
        return customers;
    }
}
