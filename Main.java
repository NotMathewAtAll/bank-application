import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Scanner;

public class Main {
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {
		BankAccount bankAccount = new BankAccount();
		BankAccount.main(args);
		try {
			bankAccount.getFileContent();
		} catch (AccessDeniedException e) {
			System.out.println("Access to the file with all the data that this program creates is denied. Check your file permissions.");
			System.exit(0);
		}
		
		if (BankAccount.strFileContent.trim().equals("")) {
			System.out.println("Hello! Let's get started by creating your bank account.");

			bankAccount.setUsername();
			bankAccount.setPassword();
			bankAccount.setPin();
			System.out.println("Account has been successfully created.");
			System.exit(0);
		}
		
		System.out.print("Enter PIN code: ");
		String enteredPin = scan.nextLine();
		String pinCode = BankAccount.getLastPin().substring(0, BankAccount.getLastPin().length() - 8);
		
		while (!pinCode.equals(enteredPin.trim())) {
			System.out.println("Wrong PIN.");
			System.out.print("Enter PIN code: ");
			enteredPin = scan.nextLine();
		}
		
		String nickname = BankAccount.getLastUsername().substring(0, BankAccount.getLastUsername().length() - 9);
		
		System.out.println("Hello, " + nickname + "!");
		System.out.println(bankAccount.toString());
		String option = scan.nextLine();
		
		while (true) {
			switch (option) {
			case ("1"):
				System.out.println(bankAccount.getBalance());
				System.exit(0);
			case ("2"):
				bankAccount.topUpBalance();
				System.exit(0);
			case ("3"):
				bankAccount.withdrawMoney();
				System.exit(0);
			case ("4"):
				System.out.println(bankAccount.getOperationsHistory());
				System.exit(0);
			case ("5"):
				System.out.println(BankAccount.getLastUsername().substring(0, BankAccount.getLastUsername().length() - 9));
				System.exit(0);
			case ("6"):
				bankAccount.setUsername();
				System.exit(0);
			case ("7"):
				bankAccount.changePassword();
				System.exit(0);	
			case ("8"):
				bankAccount.changePin();
				System.exit(0);
			case ("9"):
				bankAccount.deleteAccount();
				System.exit(0);
			default:
				System.out.println("Error: no such an option.");
				option = scan.nextLine();
			}
		}
	}
}
