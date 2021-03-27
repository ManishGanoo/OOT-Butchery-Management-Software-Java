package BackEnd;

public class Users {
    private int UserID;
    private String Name;
    private String l_password;


    public Users(int UserID, String Name, String l_password){
        this.UserID = UserID;
        this.Name = Name;
        this.l_password=l_password;
    }

    public Users(String Name, String l_password){

        this.Name = Name;
        this.l_password = l_password;

    }

    public int getUserID(){
        return UserID;
    }

    public String getName(){
        return Name;
    }

    public String getPassword(){
        return l_password;
    }

}