import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Bank implements Serializable {
    private static final long serialVersionUID = 2L;
    private ArrayList<User> users;
    private ArrayList<Transaction> transactions;
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MIN_PASSWORD_LENGTH = 6;
    
    public Bank(){
        users = new ArrayList<>();
        transactions = new ArrayList<>();
    }
    public void addUser(Scanner keyboard){
        String userName = "";
        String password = "";
        double balance = 0.0;
        boolean hasUserName = false;
        while (!hasUserName){
            System.out.println("Enter your user name");
            try {
                userName = keyboard.next();
                if (userName == null || userName.isBlank()){
                    continue;
                }
                if (userName.length() < MIN_USERNAME_LENGTH){
                    System.out.println("Username must be at least 3 characters");
                    continue;
                }
                hasUserName = true;
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }
        boolean hasPassword = false;
        while (!hasPassword){
            System.out.println("Enter your password");
            password = keyboard.next();
            if (password == null || password.isBlank()){
                continue;
            }
            if (password.length() < MIN_PASSWORD_LENGTH){
                System.out.println("Password must be at least 6 characters");
                continue;
            }
            hasPassword = true;
        }
        boolean hasbalance = false;
        while (!hasbalance){
            System.out.println("Enter how many money you want to put in");
            try {
                balance = keyboard.nextDouble();
                keyboard.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a number");
                keyboard.nextLine();
                continue;
            }
            hasbalance = true;
        }
        System.out.println("Is this admin status? Enter 'admin' to confirm:");
        String userinput = keyboard.next(); 
        boolean admin = userinput.equalsIgnoreCase("admin");
        User newUser = new User(userName, 0.0, password, admin);
        if (users.isEmpty()) {
            users.add(newUser);
            Transaction newTranscation = new Transaction(newUser, balance, "deposit");
            transactions.add(newTranscation);
            System.out.println("First user added successfully!");
            return;
        }
        boolean exist = false;
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUserName().equalsIgnoreCase(newUser.getUserName())){
                exist = true;
                break;
            }
        }
        if (exist){
            System.out.println("This user already exists");
        }
        else{
            users.add(newUser);
            Transaction newTranscation = new Transaction(newUser, balance, "deposit");
            transactions.add(newTranscation);
            System.out.println("User added");
        }
        
    }
    public void removeUser(String userName){
        if (userName == null){
            System.out.println("Cannot remove null user");
            return;
        }
        boolean found = false;
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUserName().equalsIgnoreCase(userName)){
                users.remove(i);
                found = true;
                System.out.println("removed");
                break;
            }
        }
        if (!found){
           System.out.println("Cannot find that user"); 
        }
    }
    public int findUserindex(String userName){
        if (userName == null){
            return -1;
        }
        else{
            for (int i = 0; i < users.size(); i++){
                if (users.get(i).getUserName().equalsIgnoreCase(userName)){
                    return i;
                }
            }
        }
        return -1;
    }
    public boolean findUser(String userName){
        if (userName == null){
            return false;
        }
        else{
            for (int i = 0; i < users.size(); i++){
                if (users.get(i).getUserName().equalsIgnoreCase(userName)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean getUserLevel(int index){
        if (index < 0 || index >= users.size()){
            System.out.println("Cannot access that user");
        }
        return users.get(index).getLevel();
    }
    
    public int validLogin(String userName, String password){
        if (userName != null && password != null){
            password = hashing.sha256(password);
            for (int i = 0; i < users.size(); i++){
                if (users.get(i).getUserName().equals(userName) && users.get(i).gethashedpassword().equals(password)){
                    return i;
                }
            }
        }
        return -1;
    }
    public double getBalance(int index){
        if (index < 0 || index >= users.size()){
            System.out.println("Cannot get that user");
            return -1;
        }
        return users.get(index).getBalance();
    }
    public double withdrawal(double balance, int index){
        if (index < 0 || index >= users.size()){
            System.out.println("Cannot get that user");
            return -1;
        }
        if (balance < 0){
            System.out.println("Cannot withdrawl negative balance");
            return -1;
        }
        Transaction newTranscation = new Transaction(users.get(index), balance, "withdrawal");
        transactions.add(newTranscation);
        return users.get(index).getBalance();
        
    }
    public double deposits(double balance, int index){
        if (index < 0 || index >= users.size()){
            System.out.println("Cannot get that user");
            return -1;
        }
        if (balance < 0){
            System.out.println("Cannot deposits negative balance");
            return -1;
        }
        Transaction newTranscation = new Transaction(users.get(index), balance, "deposit");
        transactions.add(newTranscation);
        return users.get(index).getBalance();
    }
    public boolean transcation(int selfIndex, double balance, int targetIndex){
        if (targetIndex < 0 || selfIndex < 0 || selfIndex >= users.size() || targetIndex >= users.size()){
            System.out.println("Cannot get that user");
            return false;
        }
        if (balance < 0){
            System.out.println("Cannot be negative number");
            return false;
        }
        if (selfIndex == targetIndex){
            System.out.println("Cannot transfer to yourself");
            return false;
        }
        Transaction newTranscation = new Transaction(users.get(selfIndex), balance, users.get(targetIndex));
        transactions.add(newTranscation);
        if (newTranscation.getType().equalsIgnoreCase("failed")){
            return false;
        }
        return true;
    }
    public boolean isEmpty(){
        return users.isEmpty();
    }
    public String showAllUserData(){
        String result = "";
        for (int i = 0; i < users.size(); i++){
            result += users.get(i).toString() + ", ";
        }
        return result;
    }
    public String showTranscation(String username){
        StringBuilder history = new StringBuilder();
        for (int i = 0; i < transactions.size(); i++){
            if (transactions.get(i).getUserOne().getUserName().equalsIgnoreCase(username) || (transactions.get(i).getUserTwo() != null && transactions.get(i).getUserTwo().getUserName().equalsIgnoreCase(username))){
                history.append(transactions.get(i).toString()).append("\n");
            }
        }
        return history.toString();
    }
    public void saveBankData(String fileName) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
        out.writeObject(this.users);
        out.writeObject(this.transactions);
        System.out.println("Data saved successfully.");
    } catch (IOException e) {
        System.out.println("Error saving bank data: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    public void loadBankData(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            this.users = (ArrayList<User>) in.readObject();
            this.transactions = (ArrayList<Transaction>) in.readObject();
            System.out.println("Data loaded successfully.");
        } 
        catch (FileNotFoundException e) {
            System.out.println("No previous save file found. Starting fresh.");
        } 
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
