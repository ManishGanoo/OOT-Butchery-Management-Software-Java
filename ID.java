package BackEnd;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by erosennin on 3/12/17.
 */
public class ID extends dbManipulation{
    private ArrayList<Integer> id=new ArrayList<>();
    private Sales sales;
    private Product product;
    private Users users;
    private Supplier supplier;
    private Supplier supplier2;
    private Users user;
    private Customer customer;
    private Purchases purchases;
    private Paymentcust paymentcust;
    private Paymentsup paymentsup;
    private ArrayList<Product> products=new ArrayList<>();

    public void Query(String sql,String columnName){
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                id.add(rs.getInt(columnName));
            }
        }
        catch (SQLException e){}

    }

    public ArrayList<Integer> getId(String sql,String columnName) {
        Query(sql,columnName);
        return id;
    }

    public Sales searchSalesRecord(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                sales=new Sales(rs.getInt("Invoice_No"),
                        rs.getString("sDate"),
                        rs.getInt("Pid"),
                        rs.getInt("quantity"),
                        rs.getDouble("Weight_Kg"),
                        rs.getDouble("Total"),
                        rs.getString("Paid").charAt(0),
                        rs.getString("DeliveredBy"),
                        rs.getInt("Cid"));
            }
        }
        catch (SQLException e){}
        return sales;
    }

    public Product searchProductRecord(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                product=new Product(
                        rs.getString("Name"),
                        rs.getDouble("buyingPrice"),
                        rs.getDouble("sellingPrice"),
                        rs.getInt("Stock"));
            }
        }
        catch (SQLException e){}
        return product;
    }

    public Users searchUserRecord(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                users=new Users(rs.getInt("UserID"),rs.getString("Name"),rs.getString("l_password"));
            }
        }
        catch (SQLException e){}
        return users;
    }

    public Supplier searchSupplierRecord(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                supplier =new Supplier(rs.getInt("SupplierID"),
                        rs.getString("Name"),
                        rs.getInt("telephone"),
                        rs.getString("email"),
                        rs.getFloat("balanceDue"));
            }
        }
        catch (SQLException e){}
        return supplier;
    }
    public double searchSupplier2Record(String sql){
        double bal=0;
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                bal=rs.getDouble("balanceDue");
            }
        }
        catch (SQLException e){}
        return bal;
    }

    public static int getStockLevel(int Pid){
        int level=0;
        try {
            String sql="SELECT Stock FROM tblProduct WHERE ProductID="+Pid;
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                level=rs.getInt("Stock");
            }

        }
        catch (SQLException e){}

        return level;
    }

    public ArrayList<Integer> getUsersid(String sql,String columName) {
        Query(sql,columName);
        return id;
    }

    public Users searchUsers(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                user = new Users(
                        rs.getString("Name"),
                        rs.getString("l_password"));

            }
        }
        catch (SQLException e){}

        return user;
    }

    public Customer searchCustomerRecord(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                customer = new Customer(
                        rs.getString("Name"),
                        rs.getInt("telephone"),
                        rs.getString("email"),
                        rs.getFloat("balanceDue"));
            }
        }
        catch (SQLException e){}
        return customer;
    }

    public Purchases searchPurchaseRecord(String sql){
        try{
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                purchases=new Purchases(
                        rs.getInt("PurchasesID"),
                        rs.getInt("Invoice_No"),
                        rs.getString("P_Date"),
                        rs.getInt("Pid"),
                        rs.getInt("Quantity"),
                        rs.getDouble("Weight_Kg"),
                        rs.getDouble("Total"),
                        rs.getInt("S_id")
                );
            }
        }
        catch(SQLException e){}
        return purchases;
    }

    public Paymentcust searchPaymentcustRecord(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                paymentcust = new Paymentcust(
                        rs.getInt("custID"),
                        rs.getInt("invoice_no"),
                        rs.getString("Date_paid"),
                        rs.getFloat("Amount_paid"));
            }
        }
        catch (SQLException e){}
        return paymentcust;
    }

    public Paymentsup searchPaymentsupRecord(String sql){
        try {
            rs = stmt.executeQuery( sql );
            while (rs.next()){
                paymentsup = new Paymentsup(
                        rs.getInt("supID"),
                        rs.getInt("invoice_No"),
                        rs.getString("Date_paid"),
                        rs.getFloat("Amount_Paid"));
            }
        }
        catch (SQLException e){}
        return paymentsup;
    }


}

