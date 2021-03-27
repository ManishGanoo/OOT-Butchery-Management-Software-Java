package BackEnd;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by erosennin on 2/26/17.
 */
public class FillCBProduct extends dbManipulation {
    private ArrayList products=new ArrayList<Product>();

    public FillCBProduct(){
        super();
    }

    public void Query(String sql) {
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                products.add(new Product(rs.getInt("ProductID"),rs.getString("Name"),
                        rs.getDouble("buyingPrice"),rs.getDouble("sellingPrice")));
            }
        }
        catch (SQLException e){}
    }
    public ArrayList getProducts(){
        return products;
    }

}
