import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
public class BankApplication {
    public static Customer customer;
    public static String accountType;
    public static String customerName;
    public static double inDepositAmount;
  // prints all the options available to choose from for the user
public static void showBankMenu(){
    System.out.println("Choose one of the following to proceed:");
    System.out.println("1. Select Customer");
    System.out.println("2. Unselect a Customer");
    System.out.println("3. Add a customer.");
    System.out.println("4. Get a loan.");
    System.out.println("5. Make a deposit.");
    System.out.println("6. Print all customers");
    System.out.println("7. Remove customers");
    System.out.println("8. View balance");
    System.out.println("9. Make a withdrawal");
    System.out.println("10. Display loan amount");
    System.out.println("11. Pay off a loan");
}
//adds default customers Samarth and Eeshan by adding them to the bank object
public static void addDefaultCustomers(Bank bank) throws IOException {
    System.out.println("Adding Samarth as a default customer");
    Customer Samarth = new Customer("Samarth", "Checking", 12);
    bank.registerCustomer(Samarth);
    System.out.println("Adding Eeshan as a default customer");
    Customer Eeshan = new Customer("Eeshan", "Savings", 4.0);
    bank.registerCustomer(Eeshan);
    CustomerPopulator.readCustomers(bank);

}
//selects customer by calling the other selectCustomer method, and asks user which customer account they want to select.
    public static Customer selectCustomer(Bank bank) throws Exception {
        Scanner in = new Scanner (System.in);
        System.out.println("Enter the customer name to select.");
        String selectedCustomer = in.nextLine();
        return selectCustomer(bank,selectedCustomer);

    }
    //selects a default customer(Samarth) by checking if he/she exists and makes the customer that is beingselected
    //to be the customer object to conduct the program on
    public static Customer selectCustomer(Bank bank, String selectedCustomer) throws Exception {
        // Choice is known
        if (!bank.checkCustomerExists(selectedCustomer)) {
            System.out.println("Customer does not exist. Try again");
            return null;
        } else {
            try {
                customer = bank.getCustomer(selectedCustomer);
            } catch (Exception e) {
                System.out.println("get customer resulted in an error" + e);
            }
            System.out.println(selectedCustomer +" is successfully selected as customer");
            Bank.printCustomerInfo(customer);
            return customer;
        }
    }
    //gets the loan amount by asking user
public static void getLoan(Customer customer,Bank bank){
    Scanner in  = new Scanner(System.in);
    System.out.println(customer.name + ", how much do you want for the loan?");
    double loanAmount = in.nextDouble();
    boolean loanApproved = bank.processLoan(loanAmount, customer);
    if (loanApproved) {
        System.out.println(customer.name +", your loan is approved.");
    } else {
        System.out.println("Not approved.");
        System.out.println(bank.getReason());
    }
}
    //main method, conducts whole program
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String choice1;
        int choice2;
        double depositAmount;
        double withdrawalAmount;
        double loanPayment;

        Bank chaseBank = new Bank("Chase", 100000.00);
        addDefaultCustomers(chaseBank);
        selectCustomer(chaseBank, "Samarth");
        while (true) {
            showBankMenu();
            try{
                choice1 = in.nextLine();
                choice2 = Integer.parseInt(choice1);
            }catch (NumberFormatException nfe){
                System.out.println("Not a valid inuput. Please pick something that is given." + nfe.getLocalizedMessage());
                continue;
            }
            if (!validateUserInput(choice2)) {
                System.out.println("Please choose one of the options from the menu.");
                continue;
            }
            switch (choice2) {
                case 1: //select customer
                    if(customer == null){
                        customer = selectCustomer(chaseBank);
                        break;
                    }
                    else
                    {
                        System.out.println("Customer that is already selected is " + customer.getName());
                    }
                    break;
                case 2: // Unselect a customer
                    if (customer != null) {
                     customer = null;
                     System.out.println("Customer is unselected.");
                    }
                    else
                        System.out.println("Customer is already unselected. Please select a customer.");
                     break;
                case 3:
                    chaseBank.addCustomer();
                    break;
                case 4: // get a loan
                    if(customer == null){
                        customerNotSelected(chaseBank);
                    }else{
                        getLoan(customer,chaseBank);
                    }
                    break;
                case 5: // make a deposit
                    if(customer == null){
                        customerNotSelected(chaseBank);
                        break;
                    }else{
                        System.out.println(customer.name + ", how much do you want to deposit into your account?");
                        depositAmount = in.nextDouble();
                        if (chaseBank.deposit(depositAmount, customer)) {
                            System.out.println("Your deposit has been put into your account.");
                            System.out.println("The amount of money in your account is now " + customer.getAccountBalance());
                        }
                    }
                    break;
                case 6:
                    chaseBank.printCustomers();
                    break;
                case 7:
                    if(customer == null){
                        customerNotSelected(chaseBank);
                        break;
                    }else {
                        System.out.println("do you want to remove" +customer.getName() +" as a customer? (Y/N)");
                        char choice = in.nextLine().charAt(0);
                        switch (choice) {
                            case 'N':
                                System.out.println("Then which customer do you want to select?");
                                String name = in.nextLine();
                                try {
                                    if (chaseBank.removeCustomer(name)) {
                                        System.out.println(name + " removed successfully ");
                                    } else {
                                        System.out.println(name + " could not be removed");
                                    }
                                }catch (Exception e){
                                    System.out.println("Bank application could not remove for some reason.");
                                }
                            case 'Y':
                                try {
                                    chaseBank.removeCustomer(customer.getName());
                                } catch (Exception e){
                                    System.out.println("Bank application could not remove customer for some reason.");
                            }

                        }
                        break;
                    }
                    case 8:
                        if(customer == null){
                           customerNotSelected(chaseBank);
                        }
                        else {
                             double customerBalance = chaseBank.getCustomerAccountBalance(customer);
                             System.out.println(customer.getName() + ", your balance is "+customerBalance + " dollars.");
                        }
                        break;
                    case 9:
                        if(customer == null){
                            customerNotSelected(chaseBank);
                            break;
                        }else{
                            System.out.println(customer.name + ", how much do you want to withdraw? Enter your amount below.");
                            withdrawalAmount = in.nextDouble();
                            in.nextLine();
                            if (chaseBank.withdrawal(withdrawalAmount, customer)) {
                                System.out.println("Your money has been withdrawn from your account.");
                                System.out.println("Your balance is now " + "$" + customer.getAccountBalance());
                            }
                        }
                        break;
                    case 10:
                        if(customer == null){
                            customerNotSelected(chaseBank);
                        }
                        else {
                            double loanAmount = chaseBank.getCustomerBorrowedAmount(customer);
                            System.out.println(customer.getName() + ", your loan balance is "+loanAmount + " dollars.");
                        }
                        break;
                    case 11:
                        if(customer == null){
                            customerNotSelected(chaseBank);
                            break;
                        }else{
                            if (customer.getBorrowedAmount() > 0) {
                                System.out.println(customer.getName() + ", how much do you want to pay off?");
                                loanPayment = in.nextDouble();
                                in.nextLine();
                               if(chaseBank.acceptLoanPayment(loanPayment,customer)) {
                                   System.out.println(customer.getName() + ", loan payment accepted.");
                               }else{
                                   System.out.println(customer.getName() + ", loan payment NOT accepted.");
                               }
                            }
                        }
            }

        }
    }
    //makes sure the user only enters a number option that is on the menu
    public static boolean validateUserInput (int choice1) {
        Set <Integer> validOptions = new HashSet<>();
        for (int i = 1; i < 12; i++) {
            validOptions.add(i);
        }
        return validOptions.contains(choice1);
    }
    //prints a message if a customer is not selected yet, and prints the list of customers that are possible to choose
    public static void customerNotSelected (Bank bank) {
        System.out.println("Please select the customer first from this list.");
        bank.printCustomers();
    }
}