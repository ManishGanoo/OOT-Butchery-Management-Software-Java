package BackEnd;

/**
 * Created by Rawshirao on 11/03/2017.
 */
public class Customer {

    private String Name;
    private int telephone;
    private String email;
    private float balanceDue;
    private int Cid;


    public Customer(String Name, int telephone, String email, float balanceDue) {

        this.Name = Name;
        this.telephone = telephone;
        this.email = email;
        this.balanceDue = balanceDue;

    }
    public Customer(int Cid, String Name) {
        this.Cid=Cid;
        this.Name=Name;
    }

    public String getName() {
        return Name;
    }

    public int getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public float getBalanceDue() {
        return balanceDue;
    }

    public int getCid() {
        return Cid;
    }
}