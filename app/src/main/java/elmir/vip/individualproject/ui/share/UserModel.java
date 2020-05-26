package elmir.vip.individualproject.ui.share;

public class UserModel {

    String fName, email;

    public UserModel(){
    }

    public UserModel(String fName, String email) {
        this.fName = fName;
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public String getEmail() {
        return email;
    }
}