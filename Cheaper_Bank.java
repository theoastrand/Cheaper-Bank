package CheaperBanking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Cheaper_Bank {
    //private static BufferedReader reader;
    private static String dataBaseName = "CheaperBanking/accounts.txt";
    private ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
    private static int commandCounter; // This counts which command is executing to give better feedback
    public static void main(String[] args) {
        Cheaper_Bank bank = new Cheaper_Bank();

        String fileName = args[0];
        System.out.println(fileName);
        createTxtFile();
        bank.setAccounts(loadAccounts());

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String currentLine;

            commandCounter = 0;
            while ((currentLine = reader.readLine()) != null) {
                commandCounter++;
                bank.parseLine(currentLine);
            }
            reader.close();

            
        } catch( Exception e) {
            e.printStackTrace();
            System.out.print("Error: Inputfile does not exist");

        }
        
        // Writing all the updated accounts to the data file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataBaseName));
            for (BankAccount b : bank.getAccounts()) {
                writer.append(b.getID() + ";" + b.getBalance() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("This should never happen, the file is created always");
        }

    }
    //Function that checks if txt database file of bank accounts exists, if not it is created//
    public static void createTxtFile(){
        try {
            FileReader wr = new FileReader(dataBaseName);
            wr.close();
        } catch (Exception e) {
            File file = new File(dataBaseName);
            try{
                file.createNewFile();
            } catch (Exception f) {
                
            }
        }
    }

    // A method that reads all existing accounts from file, will only run once
    public static ArrayList<BankAccount> loadAccounts() {
        ArrayList<BankAccount> loadedAccounts = new ArrayList<BankAccount>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataBaseName));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] accountInfo = currentLine.split(";"); // When storing a account info is devided by ;
                String accountID = accountInfo[0];
                String balance = accountInfo[1];
                BankAccount acc = new BankAccount(accountID, Integer.parseInt(balance));
                loadedAccounts.add(acc);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("This should never happen, the file is created always");
        }
        return loadedAccounts;

    }

    // Determines which command is used and runs it
    public void parseLine(String line) {
        String[] instruction = line.split(" ");
        String command = "";
        if (instruction.length > 0) {
            command = instruction[0];
        } 

        if (command.equals("-c")) {
            List<String> bankInfo = Arrays.asList(instruction);
            System.out.println(bankInfo.size());
            BankAccount newAccount = createAccount(bankInfo.subList(1,4));

            if (newAccount != null) {
                bankAccounts.add(newAccount);
            }

        } else if (command.equals("-l")) {
            if (instruction.length > 1) {
                String id = instruction[1];
                if (login(id)) {
                    runCommand(Arrays.asList(instruction).subList(1, instruction.length));
                }
            } else {
                System.out.println("No AccountID was entered");
            }
        }
    }

    // Method to check if the proposed AccountID is a correct and a existing one
    public Boolean login(String id) {
        if (id.length() != 8) {
            System.out.println("The account you tried to log into was not formated correctly");
            return false;
        } else if (!bankAccounts.contains(new BankAccount(id, 0))) {
            System.out.println("There is no existing account with the ID: " + id);
            return false;
        } else {
            return true;
        }
    }

    // Creates a new BankAccount with the given data
    // if the data is incorrect it will ask for new data and run again
    public BankAccount createAccount(List<String> bankData) {
        Boolean validAccount = true;
        String name = "";
        String age = "";
        String work = "";

        System.out.println(bankData);
        
        if (bankData.size() == 3) {

            if (bankData.get(0).equals("q")) return null;

            name = bankData.get(0);
            String[] names = name.split("_");
            int charsInName = 0;
            for (String s : names) charsInName += s.length();
            // Check if name is correct format, correct amount if names, correct length
            if (charsInName > 20 || names.length < 2 || names.length > 3 || !name.matches("\\w+")) validAccount = false;
        

            // Checks if age is entered correctly
            age = bankData.get(1);
            if (!isNumeric(age)) {
                validAccount = false;
            } else {
                int ageInt = Integer.parseInt(age);
                if (ageInt < 16 || ageInt > 100) validAccount = false;
            }

            // Checks if name of workplace id formatted correctly
            work = bankData.get(2);
            if (work.length() > 100 || !work.matches("\\w+")) validAccount = false;

        } else if (bankData.size() > 0 && bankData.get(0).equals("q")) {
            return null;
        } else {
            validAccount = false;
        }

        if (validAccount) { // If the entered data from file was correct

            // Randomizing a ID until we find one that is not in use
            Random rnd = new Random();
            int newID = 10000000 + rnd.nextInt(90000000);
            while (bankAccounts.contains(new BankAccount(Integer.toString(newID), 0))) {
                newID = 10000000 + rnd.nextInt(90000000);
            }
            String id = Integer.toString(newID);

            return new BankAccount(name, age, work, id);
        } else {
            // If the data from the file was incorrect we will ask for new data
            System.out.println("Invalid data for new bankaccount!");
            System.out.println("Please enter new data in the form: <Name_Surname> <age> <Work place> ");
            System.out.println("Enter this data here (without '<>'):");

            Scanner sc = new Scanner(System.in);
            String newLine = sc.nextLine();
            sc.close();

            String[] newData = newLine.split(" ");

            // Recursive call to create a new account with the new data
            return createAccount(Arrays.asList(newData));
        }
    }

    // Method for running specific command after logging in
    public void runCommand(List<String> input) {
        String id = input.get(0);
        // Retrives the BankAccount that we have logged in to
        // Should wotk since indexOf uses equals wich has been override to compare only id
        BankAccount logedinAccount = bankAccounts.get(bankAccounts.indexOf(new BankAccount(id, 0)));


        if (input.size() < 2) {
            System.out.println("No operation");
        } else {
            String command = input.get(1);

            if (command.equals("-b")) {
                logedinAccount.checkBalance();
            } else if (command.equals("-d")) {

                if (input.size() > 2 && isNumeric(input.get(2))) {
                    logedinAccount.deposit(Integer.parseInt(input.get(2)));
                } else {
                    System.out.println("The deposit-amount was not correctly formatted");
                }
            } else if (command.equals("-w")) {

                if (input.size() > 2 && isNumeric(input.get(2))) {
                    logedinAccount.withdraw(Integer.parseInt(input.get(2)));
                } else {
                    System.out.println("The withdrawl-amount was not correctly formatted");
                }
            } else if (command.equals("-o")) {

                logedinAccount.getOperations();
            } else if (command.equals("-r")) {

                bankAccounts.remove(logedinAccount);
                System.out.println("The account with number :" + id + " has been deleted");
            } else {
                System.out.println("Incorrect command");
            }
        }
    }

    // Help-method to check if a string is numeric
    public boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }

      public ArrayList<BankAccount> getAccounts() {
        return this.bankAccounts;
      }

      public void addAccount(BankAccount account) {
        if (account != null) {
            this.bankAccounts.add(account);
        }
      }

      public BankAccount getAccount(String id) {
        return this.bankAccounts.get(bankAccounts.indexOf(new BankAccount(id, 0)));
      }

      public void setAccounts(ArrayList<BankAccount> list) {
        bankAccounts = list;
      }
}