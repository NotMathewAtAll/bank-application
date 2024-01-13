package bankAccountInterfaces;

import java.io.IOException;

public interface BankAccountOperations { 
	void setUsername() throws IOException;
	void setPassword() throws IOException;
	void setPin() throws IOException;
	void changePassword() throws IOException;
	void changePin() throws IOException;
	void topUpBalance() throws IOException;
	void withdrawMoney() throws IOException;
	void deleteAccount() throws IOException;
	
	String getBalance();
	String getUsername() throws IOException;
	String getOperationsHistory() throws IOException;
}

