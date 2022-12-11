package CheaperBanking;
import CheaperBanking.Cheaper_Bank;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

public class TestLogic
{
    @Test
    public void TestIsNumeric()
    {
        assertTrue(null,Cheaper_Bank.isNumeric("1"));
        assertFalse(null, Cheaper_Bank.isNumeric("kcjgwdjshgcv"));
    }

    @Test
    public void TestLoadAccount()
    {
        File accounts = new File("accounts.txt");
        try{
            accounts.createNewFile();

        } catch (Exception e) {
        }
        
        System.out.println();
        assertEquals(0, Cheaper_Bank.loadAccounts().size());
    }

    @Test
    public void TestParseLine() {
        Cheaper_Bank bankC = new Cheaper_Bank();
        Cheaper_Bank bankL = new Cheaper_Bank();

        // test -c
        bankC.parseLine("-c Rich_Fisk 30 loop");
        assertEquals(1, bankC.getAccounts().size());

        // test -l with id
        BankAccount acc = new BankAccount("00000000", 0);
        bankL.addAccount(acc);
        bankL.parseLine("-l 00000000 -d 10");
        assertEquals(10, acc.getBalance());

        // test -l without id
        PrintStream previousConsole = System.out;
 
        // Set the standard output to use newConsole.
        ByteArrayOutputStream newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));
        BankAccount acc2 = new BankAccount("00000002", 0);
        bankL.addAccount(acc2);
        bankL.parseLine("-l");

        System.setOut(previousConsole);
        assertEquals("No AccountID was entered", newConsole.toString());
    }

    @Test
    public void TestCreateAccount()
    {
        Cheaper_Bank bank = new Cheaper_Bank();
        ArrayList<String> info = new ArrayList<>();

        // test q feedback and if no info
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("q".getBytes());
        System.setIn(in);
        bank.addAccount(bank.createAccount(info));

        //System.setIn(sysInBackup);
        assertEquals(0, bank.getAccounts().size());

        //test q feedback with inputlength 3
        in = new ByteArrayInputStream("q three char".getBytes());
        System.setIn(in);
        bank.addAccount(bank.createAccount(info));

        //System.setIn(sysInBackup);
        assertEquals(0, bank.getAccounts().size());

        // test if name is wrong format, age is not numeric and work is wrong format
        ArrayList<String> badInfo = new ArrayList<>();
        badInfo.add("theodor_oscar_gustav_astrand");
        badInfo.add("tio");
        badInfo.add("söderberg");
        in = new ByteArrayInputStream("q".getBytes());
        System.setIn(in);
        bank.addAccount(bank.createAccount(badInfo));

        //System.setIn(sysInBackup);
        assertEquals(0, bank.getAccounts().size());


        // test if age is out of range
        badInfo = new ArrayList<>();
        badInfo.add("Rich_Goth");
        badInfo.add("10");
        badInfo.add("soder");
        in = new ByteArrayInputStream("q".getBytes());
        System.setIn(in);
        bank.addAccount(bank.createAccount(badInfo));

        System.setIn(sysInBackup);
        assertEquals(0, bank.getAccounts().size());

        // test with correct info
        info.add("Rich_Goth");
        info.add("38");
        info.add("soder");
        bank.addAccount(bank.createAccount(info));
        
        assertEquals(1, bank.getAccounts().size());
    }    
    
    @Test
    public void testLogin() {
        Cheaper_Bank bank = new Cheaper_Bank();
        assertFalse(bank.login("00000000"));
        bank.addAccount(new BankAccount("00000000", 0));
        assertTrue(bank.login("00000000"));
        assertFalse(bank.login("0000000"));
    }
}



