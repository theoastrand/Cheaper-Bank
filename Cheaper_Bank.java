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
        String[] insruction = line.split(" ");
    }
}