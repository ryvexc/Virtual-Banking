import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Account {
    public static final Scanner scanner = new Scanner(System.in);
    protected static boolean isAuthented = false;
    protected static final ArrayList<String> 
        storedUserAccountName = new ArrayList<>(), 
        storedUserPins = new ArrayList<>(),
        storedUserAccountNumber = new ArrayList<>();
    protected static final ArrayList<Integer> userBalance = new ArrayList<>();
    protected static int __LoggedID;

    protected static final void load() throws IOException {
        storedUserAccountName.clear();
        storedUserPins.clear();
        storedUserAccountNumber.clear();
        userBalance.clear();
        BufferedReader data_collect = new BufferedReader(new FileReader("src/Accounts.data"));
        String line;
        while((line = data_collect.readLine()) != null) {
            String[] userData = line.split("=");

            userBalance.add(Integer.parseInt(userData[1]));
            storedUserAccountName.add(userData[0].split(",")[0]);
            storedUserPins.add(userData[0].split(",")[1]);
            storedUserAccountNumber.add(userData[0].split(",")[2]);
        }
        data_collect.close();
    }

    protected static final String loadWithout(int id) throws IOException {
        BufferedReader data_collect = new BufferedReader(new FileReader("src/Accounts.data"));
        String line, collected_data = "";
        int counter = 0;
        while((line = data_collect.readLine()) != null) {
            if(counter != id) {
                collected_data += (line + "\n");
            } 
            counter++;
        }
        data_collect.close();
        return collected_data;
    }

    protected static final void authentication() throws IOException, InterruptedException {
        Account.load();
        while(true) {
            System.out.print("Name: ");
            String accountInput = scanner.nextLine();
            if(!storedUserAccountName.contains(accountInput)) {
                System.out.println("Account not found\n");
                continue;
            }
            
            System.out.print("Pin: ");
            String pinInput = scanner.nextLine();
            if(!storedUserPins.contains(pinInput)) {
                System.out.println("pin Incorrect\n");
                continue;
            }

            System.out.println("Login successfull");
            __LoggedID = storedUserAccountName.indexOf(accountInput);
            isAuthented = true;
            break;
        }
    }

    protected static final void logout() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        if(YesOrNo("Are you sure you wanna logout? [y/n]: ")) {
            while(true) {
                System.out.print("Type your pin: ");
                String userpinInput = scanner.nextLine();
                if(userpinInput.equals(storedUserPins.get(__LoggedID))) {
                    System.out.println("Saving data...");
                    __LoggedID = -1;
                    isAuthented = false;
                    System.out.println("Logout Success...");
                    break;
                } else {
                    System.out.println("pin Incorrect...");
                    if(!YesOrNo("Retry to logout? [y/n]: ")) break; 
                    System.out.println();
                }
            }
        } 
        else System.out.println("Abort.");
    }

    protected static String createAccountFormat(String account, String pins, String bankAccountNumber, int balance) {
        return account+","+pins+","+bankAccountNumber+"="+String.valueOf(balance);
    }
    protected static final void changePasword() throws IOException {
        if(YesOrNo("Are you sure you wanna change your pin? [y/n]: ")) {
            while(true) {
                System.out.print("Type your pin: ");
                String userpinInput = scanner.nextLine();
                if(userpinInput.equals(storedUserPins.get(__LoggedID))) {
                    System.out.print("Enter your new pin: ");
                    String newpin = scanner.nextLine();
                    String collected_data = createAccountFormat(
                        storedUserAccountName.get(__LoggedID), newpin, storedUserAccountNumber.get(__LoggedID), userBalance.get(__LoggedID)
                    );
                    if(YesOrNo("Are you sure you wanna change your pin into "+newpin+"? [y/n]: ")) {
                        BufferedWriter write_data = new BufferedWriter(new FileWriter("src/Accounts.data"));
                        System.out.println("Writing data...");
                        write_data.write(collected_data+"\n"+loadWithout(__LoggedID));
                        System.out.println("pin successfully changed...");
                        __LoggedID = -1;
                        isAuthented = false;
                        System.out.print("\nPress enter to logout...");
                        scanner.next();
                        System.out.print("Logouting...");
                        write_data.close();
                        break;
                    };
                } else {
                    System.out.println("pin Incorrect...");
                    if(!YesOrNo("Retry changing pin? [y/n]: ")) break; 
                    System.out.println();
                }
            }
           
        }
        else System.out.println("Abort");
    } 

    private static final String createBankAccountNumber() {
        String bankAccountNumber = "";
        for(int i=0; i<5; i++) 
            bankAccountNumber += String.valueOf((int)(Math.random() * 10));
        return bankAccountNumber;
    }

    protected static final void createAccount() throws IOException {
        System.out.print("Enter your name: ");
        String nameInput = scanner.nextLine();
        System.out.print("Enter pin: ");
        String pinInput = scanner.nextLine();
        while(true) {
            System.out.print("Retype pin: ");
            if(scanner.nextLine().equals(pinInput)) {
                BufferedWriter write_account = new BufferedWriter(new FileWriter("src/Accounts.data", true));
                write_account.write(createAccountFormat(nameInput, pinInput, createBankAccountNumber(), 0)+"\n");
                write_account.close();
                System.out.println("Account successfully created.");
                break;
            } else {
                System.out.println("Pin incorrect.");
                if(YesOrNo("Retype pin? [y/n]: ")) continue;
                else break;
            }
        }
    }

    protected static final void deleteAccount() throws IOException {
        if(YesOrNo("Are you sure you wanna delete this account? [y/n]: ")) {
            while(true) {
                System.out.print("Type your pin: ");
                String userpinInput = scanner.nextLine();
                if(userpinInput.equals(storedUserPins.get(__LoggedID))) {
                    if(YesOrNo("Continue deleting? [y/n]: ")) {
                        String collected_data = loadWithout(__LoggedID);
                        BufferedWriter writeNewData = new BufferedWriter(new FileWriter("src/Accounts.data"));
                        writeNewData.write(collected_data);
                        writeNewData.close();
                        System.out.println("Account was successfully deleted.");
                        __LoggedID = -1;
                        isAuthented = false;
                        break;
                    }
                } else {
                    System.out.println("pin Incorrect...");
                    if(!YesOrNo("Retry to delete this account? [y/n]: ")) break; 
                    System.out.println();
                }
            }
           
        }
        else System.out.println("Abort");
    }

    protected static final void transferAuthentication() throws IOException, InterruptedException {
        load();
        System.out.print("Enter your pin: ");
        if(scanner.nextLine().equals(storedUserPins.get(__LoggedID))) {
            while(true) {
                System.out.print("Enter bank account: ");
                String accountInput = scanner.nextLine();
                if(storedUserAccountNumber.indexOf(accountInput) != -1) {
                    System.out.print("Enter Amount: ");
                    String Amount = scanner.nextLine();

                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    System.out.println("\nName: "+storedUserAccountName.get(
                        storedUserAccountNumber.indexOf(accountInput)
                    ) + "\nBank Account: " + accountInput + "\nAmount: " + Amount + "\n");

                    System.out.print("Are you sure you wanna transfer? [y/n]: ");
                    String sure = scanner.nextLine();
                    if(sure.equals("y")) Balance.transferTo(userBalance.get(__LoggedID), accountInput, Integer.parseInt(Amount));
                    else System.out.println("Abort");
                    break;
                } else {
                    System.out.println("Couldn't find any account.\n");
                    continue;
                }
            }
        } else {
            System.out.println("Pin incorrect");
        }
    }
    
    protected static final String[] getLoggedaccount() {
        return new String[] {storedUserAccountName.get(__LoggedID), storedUserAccountNumber.get(__LoggedID)};
    }

    protected static final int balance() throws IOException {
        load();
        return userBalance.get(__LoggedID);
    }

    private static boolean YesOrNo(String message) {
        System.out.print(message);
        if(scanner.nextLine().equals("y")) return true;
        else return false;
    }
}