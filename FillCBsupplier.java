package BackEnd;

import java.sql.SQLException;
import java.util.ArrayList;

public class FillCBsupplier extends dbManipulation{
    private ArrayList suppliers=new ArrayList<Supplier>();

    public FillCBsupplier(){
        super();

    }

    public void Query(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                suppliers.add(new Supplier(rs.getInt("SupplierID"),rs.getString("Name")));
            }
        }
        catch (SQLException e){}

    }

    public ArrayList getSuppliers() {
        return suppliers;
    }
}
