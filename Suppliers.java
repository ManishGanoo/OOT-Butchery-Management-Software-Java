package BackEnd;

public class Suppliers {
    private int supId;
    private String Name;
    private int tel;
    private String email;
    private float balDue;

    public Suppliers() {
    }

    public Suppliers(int supId, String Name, int tel, String email,float baldue) {
        this.supId = supId;
        this.Name = Name;
        this.tel = tel;
        this.email = email;
        this.balDue = baldue;
    }

    Suppliers(int supInt, String Name) {
        this.supId=supInt;
        this.Name = Name;
    }

    public int getSupid(){
        return supId;
    }

    public String getName(){
        return Name;
    }

    public int getTel(){
        return tel;
    }

    public String getEmail(){
        return email;
    }

    public float getBalDue(){
        return balDue;
    }

    int getSid() {
        return supId;
    }
}