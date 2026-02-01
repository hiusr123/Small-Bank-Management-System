import java.io.Serializable;
public class User implements Serializable {
    private static final long serialVersionUID = 2L;
    private String userName = "";
    private double balance = 0.0;
    private String password = "";
    private boolean isAdmin = false;
    public User(String userName, double balance, String password, boolean isAdmin){
        this.userName = userName;
        if (balance < 0){
            System.out.println("Cannot set negative balance, balance will be 0");
        }
        else{
            this.balance = balance;
        }
        this.password = hashing.sha256(password);
        this.isAdmin = isAdmin;
    }
    public User(User copyUser){
        if (copyUser!= null){
            this.userName = copyUser.userName;
            this.balance = copyUser.balance;
            this.password = copyUser.password;
            this.isAdmin = copyUser.isAdmin;
        }
    }
    public String getUserName(){
        return this.userName;
    }
    public double getBalance(){
        return this.balance;
    } 
    public String gethashedpassword(){
        return this.password;
    }
    public boolean getLevel(){
        return this.isAdmin;
    }
    public void changeUserName(String userName){
        this.userName = userName;
    }
    public void changePassword(String password){
        this.password = hashing.sha256(password);
    }
    public void add(double balance){
        double tempBalance = getBalance() + balance;
        if (tempBalance < 0){
            System.out.println("The result cannot be negative number");
            return;
        }
        this.balance = tempBalance;
    }
    @Override 
    public boolean equals(Object object){
        if (object == null || object.getClass() != getClass()){
            return false;
        }
        User otherUser = (User) object;
        if (!this.getUserName().equals(otherUser.getUserName())){
            return false;
        }
        return true;
    }
    @Override
    public String toString(){
        return this.userName + " has $" + this.balance;
    }

    
}
