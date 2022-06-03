import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        UserInteract();
    }

    private static void UserInteract() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("Welcome To Ryve Virtual Bank 1.0!\n");

        if(Account.isAuthented) System.out.println(
            "Name: "+Account.getLoggedaccount()[0]+"\n"+"Bank Account: "+Account.getLoggedaccount()[1]+"\n"
        );
        String[] authentedMenu = {
            "View Balance",
            "Transfer",
            "Logout",
            "Change Password",
            "Delete Account"
        };
        String[] unAuthentedMenu = {
            "Login",
            "Create Account",
        };
        for(int i=0; i<(Account.isAuthented? authentedMenu : unAuthentedMenu).length; i++) {
            System.out.println((i+1)+". "+(Account.isAuthented? authentedMenu[i] : unAuthentedMenu[i]));
        }
        System.out.println(((Account.isAuthented? authentedMenu.length : unAuthentedMenu.length)+1)+". Exit");

        // User Interaction
        while(true) {
            System.out.print("\n>");
            int userInput = scanner.nextInt();
            if(Account.isAuthented) {
                if(userInput == 1) {
                    Balance.printFormatRupiah(Account.balance());
                }
                else if(userInput == 2) {
                    Account.transferAuthentication();
                    UserInteract();
                }
                else if(userInput == 3) {
                    Account.logout();
                    UserInteract();
                }
                else if(userInput == 4) {
                    Account.changePasword();
                    UserInteract();
                }
                else if(userInput == 5) {
                    Account.deleteAccount();
                    UserInteract();
                }
                else if(userInput == authentedMenu.length+1) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
            else {
                if(userInput == 1) {
                    Account.authentication();
                    UserInteract();
                } 
                else if(userInput == 2) {
                    Account.createAccount();
                    UserInteract();
                }
                else if(userInput == unAuthentedMenu.length+1) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
        }
    }
}
