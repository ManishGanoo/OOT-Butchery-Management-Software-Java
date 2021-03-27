package BackEnd;

/**
 * Created by Rawshirao on 20/03/2017.
 */
public class Paymentcust {

    private int custID;
    private int invoice_No;
    private String Date_Paid;
    private float Amount_Paid;


    public Paymentcust(int custID, int invoice_No, String Date_paid, float Amount_paid) {

        this.custID = custID;
        this.invoice_No = invoice_No;
        this.Date_Paid = Date_paid;
        this.Amount_Paid = Amount_paid;


    }

    public int getCustID() {
        return custID;
    }

    public int getInvoice_No() {
        return invoice_No;
    }

    public String getDate_Paid() {
        return Date_Paid;
    }

    public float getAmount_Paid() {
        return Amount_Paid;
    }

}