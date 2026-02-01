import java.io.Serializable;
public final class Transaction implements Serializable {
    private static final long serialVersionUID = 2L;
    private String type = "";
    private User user1;
    private User user2;
    private double balance = 0.0;
    public Transaction(User user1, double balance, String type){
        this.user2 = null;
        this.user1 = new User(user1);
        if (type.equalsIgnoreCase("withdrawal")){
            if (balance > user1.getBalance()){
                this.balance = 0.00;
                this.type = "failed";
            }
            else {
                user1.add(balance * -1);
                this.type = type;
                this.balance = balance;
            }
        }
        else{
            if (balance < 0.0){
                this.balance = 0.0;
                this.type = "failed";
            }
            else{
                this.type = type;
                user1.add(balance);
                this.balance = balance;
            }
        }
    }
    public Transaction(User user1, double balance, User user2){
        this.user1 = new User(user1);
        this.user2 = new User(user2);
        if (user1.getBalance() < balance || balance <= 0){
            this.balance = 0.0;
            this.type = "failed";
        }
        else{
            this.balance = balance;
            this.type = "Transfer";
            user1.add(balance * -1);
            user2.add(balance);
        }
    }
    public User getUserOne(){
        return new User(user1);
    }
    public User getUserTwo(){
        if (user2 == null){
            return null;
        }
        return new User(user2);
    }
    public String getType(){
        return this.type;
    }
    @Override
    public String toString() {
        // 1. Check for Transfers (where user2 exists)
        if (user2 != null) {
            if (type.equalsIgnoreCase("failed")){
                return "[FAILED TRANSFER] " + user1.getUserName() + " attempted to transfer $" + balance + " to " + user2.getUserName() + " (insufficient funds)";
            }
            return "[" + type + "] " + user1.getUserName() + " transferred $" + balance + " to " + user2.getUserName();
        } 
        
        // 2. Check for Failed transactions
        if (type.equalsIgnoreCase("failed")) {
            return "[FAILED] Transaction attempt for " + user1.getUserName();
        }

        // 3. Handle Deposits and Withdrawals (only user1 exists)
        return "[" + type.toUpperCase() + "] " + user1.getUserName() + ": $" + balance;
    }
}
