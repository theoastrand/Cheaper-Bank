package CheaperBanking;

import java.util.ArrayList;

public class BankAccount {
    private Person person;
    private String name;
    private int age;
    private String work;
    private int balance;
    private String id;
    private ArrayList<BankOperation> operations = new ArrayList<BankOperation>();

    public BankAccount(String name, int age, String work) {
        this.name = name;
        this.age = age;
        this.work = work;
    }

    public Boolean idCheck(String enteredID) {
        return id.equals(enteredID);
    }
    
    public void deposit(int amount) {
        balance += amount;
        operations.add(new BankOperation("deposit", amount));
    }

    public int checkBalance() {   // Kanske att vi vill formatera allt som ska printas som string direkt
        return balance;
    }

    public void withdraw(int amount) {
        if (amount < balance) {
            balance -= amount;
            operations.add(new BankOperation("withdraw", amount));
        }
    }

    public ArrayList<BankOperation> getOperations() {
        return operations;
    }
}
