import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    private String name;
    private double totalBalance;
    private static final double maxLoanAmount = 10000;
    private ArrayList<Customer> customers = new ArrayList <>();
    private loanRejectReason reason;
    public Bank (String name, double totalbalance) {
        this.name = name;
        this.totalBalance = totalbalance;
    }
    //Calls add method from arraylist class in java
    //If customer object is added, returns true; Else returns false
    public boolean registerCustomer(Customer customer)  {
            return customers.add(customer);
    }

    public double getTotalBalance(){
        return this.totalBalance;
    }

    /**
     *Calls getCustomer method to use for loop to run through customer arraylist to get a customer object
     *
     * @param customerName  name of the customer
     * @return boolean
     * @throws Exception exception
     */
    public boolean checkCustomerExists (String customerName) throws Exception {
       Customer customer = getCustomer(customerName);
       return customer!=null;
    }
    //Uses for loop to go through ArrayList object "customers" to find a customer object.
    public Customer getCustomer(String customerName) throws Exception{
        for( Customer customer: customers){
            if(customerName.equals(customer.getName()))
                return customer;
        }
        throw new Exception("Customer Not Found");
    }
    //Prints all customer objects from customer arraylist object
    public void printCustomers(){
            if(customers.size() > 0){
                for(int i=0;i<customers.size();i++){
                    System.out.println("Customer Number "+ (i+1) +" = "+ customers.get(i).getName() );
                }
        }else{
                System.out.println("List of customers is empty. Please at least add default customers using the menu.");
            }
    }
    //enum to store all the reasons for a loan rejection
    enum loanRejectReason {
        LoanAmountTooBig,
        NoFundsLeft,
        customerLoanLimitReached
    }
    /*
    If customer tries to enter an amoun that is greater than the remainder of the money they can borrow, returns false to reject
    If customer enters a number greater than 10000 even though they have not gotten any loans yet, rejects
     */
    private boolean canLoanBeApproved(double loanAmount,Customer customer) {
        if (customer.getBorrowedAmount() + loanAmount > maxLoanAmount) {
            reason = loanRejectReason.customerLoanLimitReached;
            return false;
        }
        if(customer.getBorrowedAmount() > maxLoanAmount ) {
            reason = loanRejectReason.customerLoanLimitReached;
            return false;
        }
        if(customer.getBorrowedAmount()< maxLoanAmount && totalBalance >= loanAmount) {
            return true;
        }
        if (totalBalance == 0){
            reason = loanRejectReason.NoFundsLeft;
            return false;
        }
        else if (totalBalance < loanAmount) {
            reason = loanRejectReason.LoanAmountTooBig;
            return false;
        }
        return false;
    }
    //gets the loanrejectreason from the enum
    public loanRejectReason getReason () {
        return reason;
    }
    //updates the total balance of the bance by subtracting the loan amount given to the customer from its total balance
    private void updateTotalBalance(double loanAmount) {
        totalBalance -= loanAmount;
        System.out.println("Bank Provided Loan for " + loanAmount);
        System.out.println("Bank Now has  " + totalBalance);
    }
    //takes the updateTotalBalance method and canLoanBeApproved reason and uses return values to conduct them.
    public boolean processLoan (double loanAmount,Customer customer) {
        if (canLoanBeApproved(loanAmount,customer)) {
            customer.setBorrowedAmount(loanAmount);
            updateTotalBalance(loanAmount);
            return true;
        } else {
            if (maxLoanAmount-customer.getBorrowedAmount() > 0.0 || loanAmount > customer.getBorrowedAmount()) {
                System.out.println("You can only borrow a loan that is less than or equal to " + (maxLoanAmount - customer.getBorrowedAmount()));
            }
            else if (maxLoanAmount-customer.getBorrowedAmount() == 0.0){
                System.out.println("You have reached the limit for your loan amount. You cannot get any more loans from this bank.");
            }
            return false;
        }

    }
    //uses boolean value to confirm a deposit
    public boolean deposit (double depositAmount, Customer customer) {
        customer.setDepositAmount(depositAmount);
        return true;
    }
    //Sees whether the amount of money a customer wants to pay off a loan for is valid.
    public  boolean acceptLoanPayment(double paymentAmount, Customer customer) {

        if (paymentAmount > customer.getAccountBalance()) {
            System.out.println("Your payment amount is more than your account balance. Can not apply towards the loan.");
            return false;
        }else{
            customer.acceptLoanPayment(paymentAmount);
            System.out.println("Your loan payment in the amount of "+paymentAmount +" was accepted");
            System.out.println("Your Account Balance " + customer.getAccountBalance());
            System.out.println("Your Loan Balance is " + customer.getBorrowedAmount());
            return true;
        }

    }
    //prints the customers info in a nice border.
    public static void printCustomerInfo(Customer customer) {
        System.out.println("-----------------------------------");
        System.out.println("| Customer name: " + customer.getName());
        System.out.println("| Account type: " + customer.getBankAccount());
        System.out.println("| Account balance: " + customer.getAccountBalance());
        System.out.println("-----------------------------------");
    }
    //checks if a customer withdrawal input is valid or not
    public boolean withdrawal (double withdrawalAmount, Customer customer) {
        if (withdrawalAmount > customer.getAccountBalance()) {
            System.out.println("You do not have enough money to withdraw that amount. You have $" + customer.getAccountBalance() + " in your account.");
            return false;
        }
        customer.withdrawAmount(withdrawalAmount);
        return true;
    }
//adds a customer by asking for info then adding it to arraylist object 'customers'
    public void addCustomer () {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter your name.");
            String customerName = in.nextLine();
            System.out.println("What type of account do you want?");
            String accountType = in.nextLine();
            System.out.println("How much do you want to initially deposit?");
            double inDepositAmount = in.nextDouble();
           Customer customer = new Customer(customerName, accountType, inDepositAmount);
           customers.add(customer);
    }
//removes a customer by taking it out off the arraylist and usees boolean to confirm
    public boolean removeCustomer (String customerName) throws Exception {
        Customer customer = getCustomer(customerName);
        if (customer.getName().equals(customerName)) {
            customers.remove(customer);
            return true;
        }
        return false;
    }
    //gets the account balance from customer object
    public double getCustomerAccountBalance(Customer customer) throws Exception {
        if(customer!=null){
            return customer.getAccountBalance();
        }else{
            throw new Exception("Customer not found.");
        }
    }
    //gets loan amount for customer
    public double getCustomerBorrowedAmount(Customer customer) throws Exception {
        if(customer!=null){
            return customer.getBorrowedAmount();
        }else{
            throw new Exception("Customer not found.");
        }
    }

}



