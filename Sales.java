/**
 * Created by erosennin on 3/7/17.
 */
package BackEnd;

public class Sales {
    private int invNo;
    private String date;
    private int Pid;
    private double quantity;
    private double weight;
    private double total;
    private char paid;
    private String delivered;
    private int Cid;

    public Sales() {
    }

    public Sales(int invNo, String date, int pid, double quantity,
                 double weight, double total, char paid, String delivered,
                 int cid) {
        this.invNo = invNo;
        this.date = date;
        Pid = pid;
        this.quantity = quantity;
        this.weight = weight;
        this.total = total;
        this.paid = paid;
        this.delivered = delivered;
        Cid = cid;
    }

    public int getInvNo() {
        return invNo;
    }

    public String getDate() {
        return date;
    }

    public int getPid() {
        return Pid;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public double getTotal() {
        return total;
    }

    public char getPaid() {
        return paid;
    }

    public String getDelivered() {
        return delivered;
    }

    public int getCid() {
        return Cid;
    }
}
