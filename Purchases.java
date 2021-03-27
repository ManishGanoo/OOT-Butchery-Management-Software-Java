package BackEnd;

/**
 * Created by erosennin on 3/22/17.
 */
public class Purchases {
    private int PurchasesID;
    private int Invoice_No;
    private String P_Date;
    private int Pid;
    private int quantity;
    private double Weight_Kg;
    private double Total;
    private int S_id;


    public Purchases(int PurchasesID, int Invoice_No, String P_Date,int Pid,int quantity,double Weight_Kg,double Total,int S_id){
        this.PurchasesID = PurchasesID;
        this.Invoice_No = Invoice_No;
        this.P_Date = P_Date;
        this.Pid = Pid;
        this.quantity = quantity;
        this.Weight_Kg = Weight_Kg;
        this.Total = Total;
        this.S_id = S_id;
    }

    public int getPurchasesID(){
        return PurchasesID;
    }

    public int getInvoice_No(){
        return Invoice_No;
    }

    public String getP_Date(){
        return P_Date;
    }

    public int getPid(){
        return Pid;
    }
    public int getQuantity(){
        return quantity;
    }

    public double getWeight_Kg(){
        return Weight_Kg;
    }

    public double getTotal(){
        return Total;
    }

    public int getS_id(){
        return S_id;
    }


}
