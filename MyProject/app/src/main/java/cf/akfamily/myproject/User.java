package cf.akfamily.myproject;

public class User {
    public String fullName,age,email,password,phoneNumber,programe,role;

    public User(){

    }
    public  User(String fullName ,String age , String email , String password , String phoneNumber , String programe ){
        this.fullName=fullName;
        this.age=age;
        this.email=email;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.programe=programe;
        role="student";


    }
}
