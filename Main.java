import java.io.IOException;
import java.util.Scanner;

public class Main {
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {
		BankAccount bankAccount = new BankAccount();
		bankAccount.getFileContent();
		
		if (BankAccount.strFileContent.trim().equals("")) {
			System.out.println("Hello! Let's get started by creating your bank account.");

			bankAccount.setUsername();
			bankAccount.setPassword();
			bankAccount.setPin();
			System.out.println("Account has been successfully created.");
			System.exit(0);
		}

		System.out.print("Enter PIN code: ");
		String[] lines = BankAccount.strFileContent.split("\n");
		String enteredPin = scan.nextLine();
		String strPin = "";
		
		for (String line : lines) {
			if (line.trim().endsWith("pincode")) {
				strPin = line.trim();
				break;
			} else {
				continue;
			}
		}
		
		strPin = strPin.substring(0, strPin.length() - 8);	
		
		while (!strPin.equals(enteredPin.trim())) {
			System.out.println("Wrong PIN.");
			System.out.print("Enter PIN code: ");
			enteredPin = scan.nextLine();
		}
		
		Scanner fileReader = new Scanner(BankAccount.file);
		String nickname = "";
		
		nickname = fileReader.nextLine() + System.lineSeparator();
		
		fileReader.close();
		
		nickname = nickname.substring(0, nickname.length() - 11);
		
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
				System.out.println(bankAccount.getUsername());
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
