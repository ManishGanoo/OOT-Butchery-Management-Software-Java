package BackEnd;

/**
 * Created by erosennin on 3/6/17.
 */
public abstract class Status {
    private static boolean changeInSales=false;
    private static boolean changeInProduct=false;
    private static boolean changeInCustomer=false;
    private static boolean changeInPaymentsup=false;
    private static boolean changeInPaymentcust=false;
    private static boolean changeInSupplier=false;


    public static void setChangeInSales(boolean value) {
        changeInSales = value;
    }

    public static void setChangeInProduct(boolean value){
        changeInProduct=value;
    }

    public static void setChangeInCustomer(boolean value){ changeInCustomer = value;}

    public static void setChangeInSupplier(boolean value) {
        changeInSupplier = value;
    }

    public static void setChangeInPaymentcust(boolean value){ changeInPaymentcust = value;}

    public static void setChangeInPaymentsup(boolean value){ changeInPaymentsup = value;}

    public static boolean isChangeInSales() {
        return changeInSales;
    }

    public static boolean isChangeInProduct(){
        return changeInProduct;
    }

    public static boolean isChangeInCustomer() {
        return changeInCustomer;
    }

    public static boolean isChangeInPaymentcust() {
        return changeInPaymentcust;
    }

    public static boolean isChangeInPaymentsup() {
        return changeInPaymentsup;
    }

    public static boolean isChangeInSupplier() {
        return changeInSupplier;
    }
}
