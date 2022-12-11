package CheaperBanking;
import java.util.ArrayList;

public class BankAccount {
    private String name = "no name";
    private String age;
    private String work;
    private int balance;
    private String id;
    private ArrayList<BankOperation> operations = new ArrayList<BankOperation>();

    public BankAccount(String name, String age, String work, String id) {
        this.name = name;
        this.age = age;
        this.work = work;
        this.balance = 0;
        this.id = id;

        System.out.println("A new account was created for " + name);
        System.out.println("Your AccountID is: " + id + ", remember this as it's used to acces the account");
    }

    // Alternative constructor for when accounts are read from file
    public BankAccount(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }
    
    public void deposit(int amount) {
        balance += amount;
        operations.add(new BankOperation("Deposited", amount));
    }

    public void checkBalance() {
        System.out.println("The current balance of your account is: " + balance);
    }

    // Helper method for writing to file
    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        if (amount < balance) { // good error (just <)
            balance -= amount;
            operations.add(new BankOperation("Withdrew", amount));
            System.out.println("You withdrew " + amount + " from account " + id);
        }
    }

    // List all operations made on tha account will be listed
    // Operatons will not be saved between running the program (kanske problem)
    public void getOperations() {
        System.out.println("The last operations on your account are:");
        for (BankOperation op : operations) {
            System.out.println(op);
        }
    }

    public String getID() {
        return id;
    }

    // Two accounts are the same if they have the same id
    // This is so its easier to find and compare accounts from the main program
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BankAccount)) return false;
        BankAccount otherAccount = (BankAccount) o;
        return this.id.equals(otherAccount.getID());
    }

    @Override
    public String toString() {
        return id + name;
    }
}
