public class Customer {
    public String name;
    private String bankAccount;
    private double borrowedAmount;
    private double accountBalance;
//assigns class variables to be the same value as local variables in constructor
    public Customer(String name,String bankAccount, double depositAmount) {
        this.name = name;
        this.bankAccount = bankAccount;
        this.accountBalance = depositAmount;
    }
    //gets bank account type
    public String getBankAccount () {
        return bankAccount;
    }
    // gets account balance for customer
    public double getAccountBalance() {
        return accountBalance;
    }
    //gets name of customer
    public String getName () {
        return name;
    }
    //gets borrowed amount
    public double getBorrowedAmount () {
        return borrowedAmount;
    }
    //sets borrowed amount for loan
    public void setBorrowedAmount (double loanAmount) {
        borrowedAmount += loanAmount;
    }
    //sets deposit amount for what the customer has entered
    public void setDepositAmount (double newDepositAmount) {
        accountBalance = accountBalance + newDepositAmount;

    }
    //takes money away from customer's account balance to make a withdrawal
    public void withdrawAmount(double withdrawalAmount) {
        accountBalance = accountBalance - withdrawalAmount;
    }
    //since acceptLoanPayment needs to take money away from the borrowed amount
    //this is because the customer has already payed off some of it
    //it also needs to deduct that amoun of money from the accountBalance
    //So, it calls the withdraw amount method which does the same logic
    public void acceptLoanPayment(double loanPaymentAmount) {
        withdrawAmount(loanPaymentAmount);
        borrowedAmount = borrowedAmount  - loanPaymentAmount;
    }
}