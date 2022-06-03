import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Balance extends Account {
    public static final void printFormatRupiah(int number) {
        String str_number = String.valueOf(number);
        String[] str_array_reversed = Array.reverseCharArrayToString(str_number.toCharArray());

        // Format titik rupiah
        for(int i=0; i<str_array_reversed.length; i++) {
            if(i%3 == 0 && i>1) str_array_reversed[i] = str_array_reversed[i] + ".";
        }

        String[] array_formated = Array.reverseStringArray(str_array_reversed);
        String number_formated = Array.joinableArrayString(array_formated)+",00";
        System.out.println(number_formated);
    }

    public static final void transferTo(int currentBalance, String bankAccountNumber, int amount) throws IOException, InterruptedException {
        if(currentBalance - amount >= 0) {
            String destinationAccountLoad = createAccountFormat(
                storedUserAccountName.get(storedUserAccountNumber.indexOf(bankAccountNumber)), 
                storedUserPins.get(storedUserAccountNumber.indexOf(bankAccountNumber)), 
                bankAccountNumber, userBalance.get(storedUserAccountNumber.indexOf(bankAccountNumber))
            );
            String newDestinationAccount = createAccountFormat(
                storedUserAccountName.get(storedUserAccountNumber.indexOf(bankAccountNumber)), 
                storedUserPins.get(storedUserAccountNumber.indexOf(bankAccountNumber)), 
                bankAccountNumber, userBalance.get(storedUserAccountNumber.indexOf(bankAccountNumber)) + amount
            );
            String userAccountLoad = createAccountFormat(
                storedUserAccountName.get(__LoggedID),
                storedUserPins.get(__LoggedID),
                storedUserAccountNumber.get(__LoggedID),
                userBalance.get(__LoggedID)
            );
            String newUserAccount = createAccountFormat(
                storedUserAccountName.get(__LoggedID),
                storedUserPins.get(__LoggedID),
                storedUserAccountNumber.get(__LoggedID),
                (userBalance.get(__LoggedID) - amount)
            );

            BufferedReader __data = new BufferedReader(new FileReader("src/Accounts.data"));
            String loaded_data = "", line;
            while((line = __data.readLine()) != null) 
                loaded_data += (line + "\n");
            __data.close();

            BufferedWriter write_data = new BufferedWriter(new FileWriter("src/Accounts.data"));
            String loaded_data_towrite = loaded_data.replaceAll(userAccountLoad, newUserAccount).replaceAll(destinationAccountLoad, newDestinationAccount);
            write_data.write(loaded_data_towrite);
            write_data.close();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Object transfer_date = formatter.format(new Date());

            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("Transfer success!");
            System.out.println("Name: "+storedUserAccountName.get(storedUserAccountNumber.indexOf(bankAccountNumber)));
            System.out.println("Bank Account Number: "+bankAccountNumber);
            System.out.println("Amount: "+amount);
            System.out.println("Date: "+transfer_date+"\n");
        } else {
            System.out.println("\nYour balance isn't enough\n");
        }
        new ProcessBuilder("cmd", "/c", "pause").inheritIO().start().waitFor();
    }
}
