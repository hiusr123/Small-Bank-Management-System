import java.util.Scanner;

public class BankDriver {
    public static void main(String[] args) {
        Bank newBank = new Bank();
        String saveData = "bank_data.ser";
        newBank.loadBankData(saveData);
        Scanner keyboard = new Scanner(System.in);
        if (newBank.isEmpty()){ 
            newBank.addUser(keyboard);
        }
        boolean exit = false;

        while (!exit) {
            System.out.println("--- Welcome to the Bank System ---");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            
            int userInput = getValidInt(keyboard, 1, 2);

            if (userInput == 1) {
                System.out.println("Enter your username:");
                String userName = keyboard.next();
                
                if (newBank.findUser(userName)) {
                    System.out.println("Enter password:");
                    String password = keyboard.next();

                    int userIndex = newBank.validLogin(userName, password);
                    if (userIndex != -1) {
                        System.out.println("Successfully entered your account!");
                        handleUserSession(newBank, userIndex, keyboard, saveData, userName);
                    } else {
                        System.out.println("Password error.");
                    }
                } else {
                    System.out.println("Cannot find that user.");
                }
            } else {
                exit = true;
            }
        }
        keyboard.close();
        newBank.saveBankData(saveData);
        System.out.println("Bank system shutting down...");
    }

    public static void handleUserSession(Bank bank, int index, Scanner keyboard, String saveData, String username) {
        boolean logout = false;
        boolean isAdmin = bank.getUserLevel(index);

        while (!logout) {
            System.out.println("\n--- User Dashboard (" + (isAdmin ? "Admin" : "Standard") + ") ---");
            System.out.println("Login in as " + username );
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdrawal");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transcation");
            
            if (isAdmin) {
                System.out.println("6. Add New User");
                System.out.println("7. Remove user");
                System.out.println("8. show all user");
                System.out.println("9. Logout");
            } else {
                System.out.println("6. Logout");
            }

            int choice = keyboard.nextInt();

            if (choice == 1) {
                System.out.println("Current Balance: $" + bank.getBalance(index));
            } else if (choice == 2) {
                System.out.println("Enter deposit amount:");
                double amount = keyboard.nextDouble();
                bank.deposits(amount, index);
                System.out.println("Current Balance: $" + bank.getBalance(index));
            } else if (choice == 3) {
                System.out.println("Enter withdrawal amount:");
                double amount = keyboard.nextDouble();
                bank.withdrawal(amount, index);
                System.out.println("Current Balance: $" + bank.getBalance(index));
            } else if (choice == 4) {
                System.out.println("Enter target username for transfer:");
                String targetName = keyboard.next();
                int targetIndex = bank.findUserindex(targetName);
                if (targetIndex != -1) {
                    System.out.println("Enter amount to transfer:");
                    double amount = keyboard.nextDouble();
                    boolean transfterDone = bank.transcation(index, amount, targetIndex);
                    if (transfterDone){
                        System.out.println("Transfer complete.");
                    }
                    else{
                        System.out.println("Transfer failed.");
                    }
                    System.out.println("Current Balance: $" + bank.getBalance(index));
                } else {
                    System.out.println("Target user not found.");
                }
            }
            else if(choice == 5){
                System.out.println(bank.showTranscation(username));
            }  
             else if (isAdmin && choice == 6) {
                bank.addUser(keyboard);
            }
            else if (isAdmin && choice == 7){
                System.out.println("Enter target username to remove:");
                String targetName = keyboard.next();
                bank.removeUser(targetName);
                bank.saveBankData(saveData);
            }
              else if(isAdmin && choice == 8) {
                System.out.println(bank.showAllUserData());
              } 
              else if ((isAdmin && choice == 9) || (!isAdmin && choice == 6)) {
                logout = true;
            }
        }
    }

    public static int getValidInt(Scanner keyboard, int min, int max) {
        while (true) {
            try {
                int input = keyboard.nextInt();
                if (input >= min && input <= max) return input;
            } catch (Exception e) {
                keyboard.next(); 
            }
            System.out.println("Enter a number between " + min + "-" + max);
        }
    }
}
