import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.*;
public class CustomerPopulator {
    public static void readCustomers(Bank bank) throws IOException {
        File Customers = new File(
                "C:\\Users\\samar\\IdeaProjects\\BankApplication\\src\\Customers.txt");
        BufferedReader br
                = new BufferedReader(new FileReader(Customers));
        String st;
        String[] splitString;
        //now
        while ((st = br.readLine()) != null){
            splitString = st.split(" ");
            double depositAmount = Double.parseDouble(splitString[2]);
            Customer customer = new Customer(splitString[0], splitString[1], depositAmount);
            bank.registerCustomer(customer);
        }

    }


}
