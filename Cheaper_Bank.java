package CheaperBanking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
            String work = instruction[3];
        } else if (command.equals("-l")) {

        }
    }
}