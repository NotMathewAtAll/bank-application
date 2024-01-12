import bankAccountInterfaces.BankAccountOperations;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.time.LocalTime;

public class BankAccount implements BankAccountOperations {
	private static Date currentDate = new Date();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	private static String date = dateFormat.format(currentDate);
	private static String username;
	private static String password;
	public static String pinCode;
	private static String lastAction;
	public static String strFileContent;
	
	public static int balance = 0;
	private static int moneyToTopUp;
	private static int moneyToWithdraw;
	
	private static Scanner scan = new Scanner(System.in);
	public static Path file = Path.of("bankAccountData.txt");
	public static Path operationsHistory = Path.of("operationsHistory.txt");
	private static LocalTime time = LocalTime.now();
	private static String strTime = String.valueOf(time);
	
	private static boolean checkIfPasswordIsStrong(String password) {
		boolean lengthMoreThanEight = false;
		boolean containsSymbols = false;
		boolean containsDigit = false;
		boolean containsLowerCase = false;
		boolean containsUpperCase = false;
		
		char[] passwordSymbols = password.toCharArray();
		final String NECESSARY_SYMBOLS =
				"1234567890`~!@#$%^&*()_+-=№;:?[]{}\\|/'\".,qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";  
		
		for (char symbol : passwordSymbols) {
			if (NECESSARY_SYMBOLS.contains(String.valueOf(symbol))) {
				containsSymbols = true;
			} if (Character.isDigit(symbol)) {
				containsDigit = true;
			} if (Character.isLowerCase(symbol)) {
				containsLowerCase = true;
			} if (Character.isUpperCase(symbol)) {
				containsUpperCase = true;
			}
		}
		
		if (password.length() >= 8) {
			lengthMoreThanEight = true;
		} else {
			System.out.println("Error: your password must contain at least 8 characters.");
		}
		
		return lengthMoreThanEight && containsSymbols && containsDigit && containsLowerCase && containsUpperCase;
	}
	
	private static String showUsername() throws IOException {
		String line = "";
		Scanner fileReader = new Scanner(file);
		
		while (fileReader.hasNextLine()) {
			line = fileReader.nextLine();
		}
		
		fileReader.close();
		
		line = line.substring(0, line.length() - 9);
		username = line;
		
		return username;
	}
	
	private static boolean checkIfPinIsNotEasy(String pinCode) {
		char firstSymbolInPin = pinCode.charAt(0);
		
		for (int i = 1; i < pinCode.length(); i++) {
			if (pinCode.charAt(i) != firstSymbolInPin) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean checkIfPinIsNumeric(String pinCode) {
		try {
			Integer.parseInt(pinCode);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	@Override
	public void setUsername() throws IOException {
		System.out.print("Enter your username: ");
		username = scan.nextLine().trim();
		
		while (username.equals("")) {
			System.out.println("Error: empty username.");
			System.out.print("Enter your username: ");
			username = scan.nextLine().trim();
		}
		
		Files.writeString(file, username + " scarfleg" + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		Files.writeString(operationsHistory, "Setted username " + username + " " + date + " " + strTime + "." + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

		System.out.println("Successfully setted a username.");
	}
	
	@Override
	public void setPassword() throws IOException {
		System.out.print("Enter password: ");
		password = scan.nextLine();
		
		while (!checkIfPasswordIsStrong(password)) {
			System.out.println("Error: your password must contain at least 1 small latin letter, "
							   + "1 capital latin letter, 1 number and 1 special symbol.");
			System.out.print("Enter password: ");
			password = scan.nextLine();
		}
		
		Files.writeString(file, password + " hotplay" + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		Files.writeString(operationsHistory, "Setted password " + date + " " + strTime + "." + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

		System.out.println("Successfully setted a password");
	}
	
	@Override
	public void changePassword() throws IOException {
		System.out.print("Enter old password: ");
		String enteredPassword = scan.nextLine();
		
		while (enteredPassword != password) {
			System.out.println("Error: wrong password");
			System.out.print("Enter the old password: ");
			enteredPassword = scan.nextLine();
		}
		
		System.out.print("Enter new password: ");
		password = scan.nextLine();
		
		while (!checkIfPasswordIsStrong(password)) {
			System.out.println("Error: your password must contain at least 1 small latin letter, "
					   + "1 capital latin letter, 1 number and 1 special symbol.");
			System.out.print("Enter new password: ");
			password = scan.nextLine();
		}
		
		System.out.print("Repeat the new password: ");
		enteredPassword = scan.nextLine();
		
		while (!enteredPassword.equals(password)) {
			System.out.println("Error: password don't match.");
			System.out.print("Write new password again: ");
			enteredPassword = scan.nextLine();
		}
		
		Files.writeString(file, password + " hotplay" + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		Files.writeString(operationsHistory, "Changed password " + date + " " + strTime + "." + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

		System.out.println("Successfully changed a password");
	}
	
	@Override
	public void setPin() throws IOException {
		System.out.print("Enter 4-digit PIN code: ");
		pinCode = scan.nextLine();
		
		while (!(checkIfPinIsNotEasy(pinCode)) || pinCode.equals("1234") || pinCode.length() != 4 || !(checkIfPinIsNumeric(pinCode))) {
			if (pinCode.length() != 4) {
				System.out.println("Error: your PIN is not a 4-digit number.");
				System.out.print("Enter PIN code: ");
				pinCode = scan.nextLine();
			} else if (checkIfPinIsNumeric(pinCode)) {
				System.out.println("Error: your PIN is too easy.");
				System.out.print("Enter PIN code: ");
				pinCode = scan.nextLine();
			} else {
				System.out.println("Error: your PIN contains invalid symbol.");
				System.out.print("Enter PIN code: ");
				pinCode = scan.nextLine();
			}
		}
		
		Files.writeString(file, pinCode + " Bobbydown" + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		Files.writeString(operationsHistory, "Setted PIN. " + date + " " + strTime + "." + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

		System.out.println("Successfully setted a PIN.");
	}
	
	@Override
	public void changePin() throws IOException {
		System.out.print("Enter old PIN code: ");
		String enteredPin = scan.nextLine();
		
		while (!enteredPin.equals(pinCode)) {
			System.out.println("Error: wrong PIN code.");
			System.out.print("Enter old PIN code: ");
			enteredPin = scan.nextLine();
		}
		
		System.out.print("Enter new PIN: ");
		pinCode = scan.nextLine();
		
		while (!(checkIfPinIsNotEasy(pinCode)) || pinCode.equals("1234") || pinCode.length() != 4 || !(checkIfPinIsNumeric(pinCode))) {
			if (pinCode.length() != 4) {
				System.out.println("Error: your new PIN is not a 4-digit number.");
				System.out.print("Enter PIN code: ");
				pinCode = scan.nextLine();
			} else if (checkIfPinIsNumeric(pinCode)) {
				System.out.println("Error: your PIN is too easy.");
				System.out.print("Enter PIN code: ");
				pinCode = scan.nextLine();
			} else {
				System.out.println("Error: your PIN contains invalid symbol.");
				System.out.print("Enter PIN code: ");
				pinCode = scan.nextLine();
			}
		}
		
		System.out.print("Write new PIN code again: ");
		enteredPin = scan.nextLine();
		
		while (!pinCode.equals(enteredPin)) {
			System.out.println("Error: PIN codes don't match.");
		}
		
		Files.writeString(file, pinCode + " Bobbydown" + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		Files.writeString(operationsHistory, "Changed PIN." + date + " " + strTime + "." + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		
		System.out.println("Successfully changed a PIN code.");
	}
	
	@Override
	public void topUpBalance() throws IOException {
		while (true) {
			try {
				System.out.print("Enter an amount of money to top up your balance: ");
				moneyToTopUp = scan.nextInt();
				
				balance += moneyToTopUp;
				
				System.out.println("Successfully topped up the balance by " + moneyToTopUp + "$");
				
				Files.writeString(file, balance + " playkeyboard" + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				Files.writeString(operationsHistory, "Top upped balance by " + moneyToTopUp + "$ " + date + " " + strTime + "." + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Error: invalid amount of money.");
				scan.nextLine();
				continue;
			}
		}
	}
	
	@Override
	public void withdrawMoney() throws IOException {
		while (true) {
			try {
				System.out.printf("Enter an amount of money to withdraw (your balance is %d$): ", balance);
				moneyToWithdraw = scan.nextInt();
				
				while (moneyToWithdraw > balance) {
					System.out.println("Error: not enough money on balance.");
					System.out.printf("Enter an amount of money to withdraw (your balance is %d$): ", balance);
					moneyToWithdraw = scan.nextInt();
				}
				
				balance -= moneyToWithdraw;
				
				System.out.println("Successfully withdrew " + moneyToWithdraw + "$");
				
				Files.writeString(file, moneyToWithdraw + " dripclock", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				Files.writeString(operationsHistory, "Withdrew " + moneyToWithdraw + "$ " + date + " " + strTime + "." + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

				System.out.println("Succesfully withdrew money.\nYour balance is " + balance + " now.");
				break;
			} catch (InputMismatchException e) {
				System.out.println("Error: invalid amount of money.");
				scan.nextLine();
				continue;
			}
		}
	}
	
	public void deleteAccount() throws IOException {
		BankAccount bankAccount = new BankAccount();
		bankAccount.getLastAction();
		
		if (strFileContent.trim().isEmpty()) {
			System.out.println("You don't have an account.");
			System.out.print("Do you want to create new (y\\n)?: ");
			String answer = scan.nextLine().trim().toLowerCase();
			
			while (true) {
				if (answer.equals("y")) {
					bankAccount.setUsername();
					bankAccount.setPassword();
					bankAccount.setPin();
					break;
				} else if (answer.equals("n")) {
					break;
				} else {
					System.out.println("Error: no such an option.");
					System.out.print("Do you want to create new (y\\n)?: ");
					answer = scan.nextLine().trim().toLowerCase();
				}
			}
		} else {
			System.out.println("Are you sure you want to delete your account (you'll lose all the money on the account)?");
			System.out.print("Confirm your password to delete: ");
			
			String enteredPassword = scan.nextLine().trim();
			Scanner fileReader = new Scanner(file);
			String line = "";
					
			while (fileReader.hasNextLine()) {
				line = fileReader.nextLine();
				
				if (line.endsWith("hotplay")) {
					break;
				} else {
					continue;
				}
			}
	
			fileReader.close();
			
			line = line.substring(0, line.length() - 8);
			
			password = line;
	
			while (!enteredPassword.equals(password)) {
				System.out.println("Error: passwords don't match.");
				System.out.println(password + " " + enteredPassword);
				System.out.print("Confirm your password to delete: ");
				enteredPassword = scan.nextLine();
			}
			
			Files.write(file, new byte[0]);
			Files.write(operationsHistory, new byte[0]);
			
			System.out.println("Account successfully deleted.");
		}
	}
	
	@Override
	public int getBalance() {
		return balance;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getLastAction() throws IOException {
		StringBuilder fileContent = new StringBuilder();
		Scanner fileReader = new Scanner(file);
		String line;
		
		while (fileReader.hasNextLine()) {
			line = fileReader.nextLine() + System.lineSeparator();
			fileContent.append(line);
		}
		
		strFileContent = fileContent.toString();
		if (!strFileContent.isEmpty()) {
			strFileContent = strFileContent.substring(0, strFileContent.length() - 2);
		}
		
		if (strFileContent.endsWith("scarfleg")) {
			lastAction = "Changed username to " + username;
		} else if (strFileContent.endsWith("hotplay")) {
			lastAction = "Changed password.";
		} else if (strFileContent.endsWith("Bobbydown")) {
			lastAction = "Changed PIN.";
		} else if (strFileContent.endsWith("playkeyboard")) {
			lastAction = "Topped up the balance by " + moneyToTopUp + "$";
		} else if (strFileContent.endsWith("dripclock")) {
			lastAction = "Withdrew " + moneyToWithdraw + "$";
		}
		
		fileReader.close();
		
		return lastAction;
	}
	
	@Override
	public String getOperationsHistory() throws IOException {
		StringBuilder fileContent = new StringBuilder();
		Scanner fileReader = new Scanner(operationsHistory);
		String line;

		while (fileReader.hasNextLine()) {
			line = fileReader.nextLine() + System.lineSeparator();
			fileContent.append(line);
		}
		
		fileReader.close();
		
		return fileContent.toString();
	}
	
	public String toString() {
		try {
			showUsername();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return username + "\n" + "Balance: " + balance;
	}
}
