 Bank Management System


## What This Project Does

This is a simple banking system where you can create accounts, deposit/withdraw money, transfer funds between users, and keep track of all your transactions. Everything saves to a file so your data doesn't disappear when you close the program.

### Object-Oriented Programming
- **Classes and Objects**: I made separate classes for User, Transaction, and Bank to keep everything organized
- **Encapsulation**: All the important data is private with getters and setters
- **toString() and equals()**: Overrode these methods to make my objects work better
- **Copy Constructors**: Used these to avoid weird reference bugs (learned this the hard way!)

### Data Structures
- **ArrayLists**: Used these to store all users and transactions
- **Searching**: Wrote methods to find users by username and filter transactions

### File I/O
- **Serialization**: Figured out how to save and load objects to files
- **Exception Handling**: Used try-catch blocks so the program doesn't crash if files are missing
- **Persistent Data**: Your account info sticks around even after closing the program

### Security Stuff
- **Password Hashing**: Passwords get turned into SHA-256 hashes (no plain text storage!)
- **Admin vs Regular Users**: Different permission levels
- **Input Validation**: Lots of checks to make sure you can't break things

## Features

**For Everyone:**
- Create an account with a username and password
- Deposit and withdraw money
- Transfer money to other users
- Check your balance
- See your full transaction history

**Admin Only:**
- Add new users
- Remove users
- See all accounts in the system

## How to Run

1. Compile everything:
```bash
javac *.java
```

2. Run the program:
```bash
java BankDriver
```

3. First time running? You'll create the first admin account. After that, just login!

## How It Works

### The Classes

**User.java**
- Stores username, password (hashed!), balance, and admin status
- Has methods to add/remove money from balance

**Transaction.java**
- Keeps a record of every deposit, withdrawal, and transfer
- Tracks if a transaction failed (like if you don't have enough money)

**Bank.java**
- Manages all the users and transactions
- Has all the methods for deposits, withdrawals, transfers, etc.
- Handles saving and loading data

**BankDriver.java**
- The main program with all the menus
- Handles user input and displays everything

**hashing.java**
- Just has one method to hash passwords using SHA-256

### Data Storage

All your data saves to a file called `bank_data.ser`. The program automatically:
- Loads everything when you start
- Saves everything when you exit

## Example Usage

```
--- Welcome to the Bank System ---
1. Login
2. Exit

Choice: 1
Enter your username: alice
Enter password: password123

--- User Dashboard (Standard) ---
Login in as alice
1. Check Balance
2. Deposit
3. Withdrawal
4. Transfer Money
5. View Transaction
6. Logout

Choice: 4
Enter target username for transfer: bob
Enter amount to transfer: 50
Transfer complete.
Current Balance: $450.0
```

## Things I Learned

1. **Scanner Buffer Issues** - Had to add `keyboard.nextLine()` after `nextDouble()` or it would skip inputs
2. **Data Persistence** - Pretty cool that you can save entire objects to files with serialization

## Known Issues

- If you change the class structure (like add a new field), you need to delete the save file
- No GUI, just command line
- Can't recover forgotten passwords (maybe I'll add that later?)

## What I'd Add If I Had More Time

- A GUI with buttons and stuff
- Password recovery
- Interest on savings accounts
- Better error messages
- Maybe connect it to a real database instead of just a file
- Account statements you can export

## Challenges I Faced

**The Transaction Bug**: My biggest problem was that when I saved transactions, they would show the current balance instead of the balance at the time of the transaction. Took me forever to realize I needed to store copies of User objects, not references!

**Input Validation**: Making sure users can't enter negative amounts, transfer to themselves, or mess things up in other ways took a lot of if-statements.

**The Save File**: Understanding how serialization works and why `serialVersionUID` matters was confusing at first.

## Testing

Things I tested to make sure it works:
- Creating multiple accounts
- Depositing and withdrawing money
- Transferring between users
- What happens if you try to withdraw more than you have
- Making sure transactions show up in history
- Closing and reopening the program to check if data saves
---

*Note: This is a learning project, not actual banking software! Don't use it for real money lol*
