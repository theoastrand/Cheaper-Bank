package CheaperBanking;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String placeOfWork;
    private BankAccount[] accounts;

    public Person(int id, String firstName, String lastName, int age, String placeOfWork, BankAccount[] accounts)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.placeOfWork = placeOfWork;
        this.accounts = accounts;
        this.age = age; 
    }


}
