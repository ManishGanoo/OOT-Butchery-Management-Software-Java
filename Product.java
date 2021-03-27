package BackEnd;

/**
 * Created by erosennin on 2/26/17.
 */
public class Product {
    private int ProdID;
    private String Name;
    private double bp;
    private double sp;
    private int stock;

    public Product(int prodID, String name, double bp, double sp) {
        ProdID = prodID;
        Name = name;
        this.bp = bp;
        this.sp = sp;
    }

    public Product(String name, double bp, double sp,int stock) {
        Name = name;
        this.bp = bp;
        this.sp = sp;
        this.stock=stock;
    }

    public Product(int prodID,String name,int stock){
        ProdID=prodID;
        Name=name;
        this.stock=stock;
    }

    public int getProdID() {
        return ProdID;
    }

    public String getName() {
        return Name;
    }

    public double getBp() {
        return bp;
    }

    public double getSp() {
        return sp;
    }

    public int getStock() {
        return stock;
    }
}
