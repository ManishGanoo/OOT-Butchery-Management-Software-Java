package BackEnd;

/**
 * Created by Rawshirao on 19/03/2017.
 */
public class Paymentsup {

    private int supID;
    private int invoice_No;
    private String Date_Paid;
    private float Amount_Paid;


    public Paymentsup(int supID, int invoice_No, String Date_paid, float Amount_paid) {

        this.supID = supID;
        this.invoice_No = invoice_No;
        this.Date_Paid = Date_paid;
        this.Amount_Paid = Amount_paid;


    }

    public int getSupID() {
        return supID;
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