import java.io.IOException;
import java.util.Scanner;

public class Main {
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {
		BankAccount bankAccount = new BankAccount();
		bankAccount.getLastAction();
		
		if (BankAccount.strFileContent == null || BankAccount.strFileContent.equals("")) {
			System.out.println("Hello! Let's get started by creating your bank account.");

			bankAccount.setUsername();
			bankAccount.setPassword();
			bankAccount.setPin();
			System.exit(0);
		}

		System.out.print("Enter PIN code: ");
		String[] lines = BankAccount.strFileContent.split("\n");
		String enteredPin = scan.nextLine();
		String strPin = "";
		
		for (String line : lines) {
			if (line.endsWith("Bobbydown")) {
				strPin = line;
			} else {
				continue;
			}
		}
		
		strPin = strPin.substring(0, strPin.length() - 10);
			
		while (!strPin.equals(enteredPin)) {
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
				break;
			case ("2"):
				bankAccount.topUpBalance();
				break;
			case ("3"):
				bankAccount.withdrawMoney();
				break;
			case ("4"):
				System.out.println(bankAccount.getLastAction());
				break;
			case ("5"):
				System.out.println(bankAccount.getOperationsHistory());
				break;
			case ("6"):
				System.out.println(bankAccount.getUsername());
				break;
			case ("7"):
				bankAccount.setUsername();
				break;
			case ("8"):
				bankAccount.changePassword();
				break;	
			case ("9"):
				bankAccount.changePin();
				break;
			case ("0"):
				bankAccount.deleteAccount();
				break;
			default:
				System.out.println("Error: no such an option.");
				option = scan.nextLine();
			}
		}
	}
}
