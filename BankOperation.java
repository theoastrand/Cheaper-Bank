package CheaperBanking;
public class BankOperation {
    private String type;
    private int amount;
    
    public BankOperation(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    // This is so the printing all operations is easier
    @Override
    public String toString() {
        return type + ", amount :" + amount;
    }
}
