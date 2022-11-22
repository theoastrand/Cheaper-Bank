package CheaperBanking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Cheaper_Bank {
    private static BufferedReader reader;
    public static void main(String[] args) {
        String fileName = "";
        for (String arg : args) {
            fileName = arg;
        }

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                parseLine(currentLine);
            }
        } catch( Exception e) {
            System.out.print("Error");

        }

    }
    public static void parseLine(String line) {
        String[] instruction = line.split(" ");
        String command = instruction[0];
        if (command.equals("-c")) {
            Boolean validAccount = true;  // Variable to check if the information of the proposed account is correct
            String name = instruction[1];
            String[] names = name.split("_");
            if (names.length < 2 || names.length > 3) validAccount = false;
            if (String.join("", names).length() > 20) validAccount = false;

            String age = instruction[2];
            if (!isNumeric(age)) validAccount = false;

            String work = instruction[3];

            if (validAccount) {
                BankAccount newAccount = new BankAccount(name, Integer.parseInt(age), work);
            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Data fro new account was incorrect, pleasr enter new: ");
                String newLine = sc.nextLine();
                sc.close();
                parseLine(newLine); //Risky som fan
            }
        } else if (command.equals("-l")) {

        }
    }

    private static boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }
}