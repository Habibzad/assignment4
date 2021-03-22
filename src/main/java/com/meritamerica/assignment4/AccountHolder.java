package com.meritamerica.assignment4;

import java.text.ParseException;

public class AccountHolder implements Comparable<AccountHolder> {

	// Constants
	public static final long BALANCE_LIMIT = 250000;

	// Instance variables
	private String firstName;
	private String middleName;
	private String lastName;
	private String ssn;
	private CheckingAccount[] checkingAccounts;
	private SavingsAccount[] saveAccounts;
	private CDAccount[] cdAccounts;

	private static long nextAccountNumber;

	// Constructor
	public AccountHolder(String firstName, String middleName, String lastName, String ssn) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
		checkingAccounts = new CheckingAccount[0];
		saveAccounts = new SavingsAccount[0];
		cdAccounts = new CDAccount[0];
		nextAccountNumber = 1000;
	}

	// Getters and Setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSSN() {
		return ssn;
	}

	public void setSSN(String ssn) {
		this.ssn = ssn;
	}

	public CheckingAccount[] getCheckingAccounts() {
		return this.checkingAccounts;
	}

	public void setCheckingAccounts(CheckingAccount[] checkAccounts) {
		this.checkingAccounts = checkAccounts;
	}

	public int getNumberOfCheckingAccounts() {
		return this.checkingAccounts.length;
	}

	public double getCheckingBalance() {
		double total = 0;
		for (int i = 0; i < checkingAccounts.length; i++) {
			total += checkingAccounts[i].getBalance();
		}
		return total;
	}

	public SavingsAccount[] getSavingsAccounts() {
		return this.saveAccounts;
	}

	public int getNumberOfSavingsAccounts() {
		return this.saveAccounts.length;
	}

	public double getSavingsBalance() {
		double total = 0;

		for (int i = 0; i < saveAccounts.length; i++) {
			total += saveAccounts[i].getBalance();
		}

		return total;
	}

	public void setSavingsAccounts(SavingsAccount[] saveAccounts) {
		this.saveAccounts = saveAccounts;
	}

	public CDAccount[] getCdAccounts() {
		return this.cdAccounts;
	}

	public int getNumberOfCDAccounts() {
		return this.cdAccounts.length;
	}

	public double getCDBalance() {
		double total = 0;

		for (int i = 0; i < cdAccounts.length; i++) {
			total += cdAccounts[i].getBalance();
		}

		return total;
	}

	public void setCdAccounts(CDAccount[] cdAccounts) {
		this.cdAccounts = cdAccounts;
	}

	public double getCombinedBalance() {
		double total = 0;

		for (int i = 0; i < checkingAccounts.length; i++) {
			total += checkingAccounts[i].getBalance();
		}

		for (int i = 0; i < saveAccounts.length; i++) {
			total += saveAccounts[i].getBalance();
		}

		for (int i = 0; i < cdAccounts.length; i++) {
			total += cdAccounts[i].getBalance();
		}

		return total;
	}

	// Add checking account
	public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
		CheckingAccount newCheckingAccount = new CheckingAccount(openingBalance);

		if (getCombinedBalance() + openingBalance >= BALANCE_LIMIT) {
			throw new ExceedsCombinedBalanceLimitException(
					"You have reached the maximum total balance across all accounts. Cannot create another.");
		} else {
			// Add transaction
			DepositTransaction newTransaction = new DepositTransaction(newCheckingAccount, openingBalance);
			return addCheckingAccount(newCheckingAccount);
		}

	}

	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount)
			throws ExceedsCombinedBalanceLimitException {
		if (getCombinedBalance() + checkingAccount.getBalance() >= BALANCE_LIMIT) {

			throw new ExceedsCombinedBalanceLimitException(
					"You have reached the maximum total balance across all accounts. Cannot create another.");

		} else {
			CheckingAccount[] newCheckingAccounts = new CheckingAccount[checkingAccounts.length + 1];
			int i;
			for (i = 0; i < checkingAccounts.length; i++) {
				newCheckingAccounts[i] = checkingAccounts[i];
			}

			newCheckingAccounts[i] = checkingAccount;
			checkingAccounts = newCheckingAccounts;

			return checkingAccount;
		}
	}

	// Add savings account
	public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
		SavingsAccount newSavingsAccount = new SavingsAccount(openingBalance);
		if (getCombinedBalance() + openingBalance >= BALANCE_LIMIT) {

			throw new ExceedsCombinedBalanceLimitException(
					"You have reached the maximum total balance across all accounts. Cannot create another.");

		} else {
			// Add a deposit transaction
			DepositTransaction newTransaction = new DepositTransaction(newSavingsAccount, openingBalance);
			return addSavingsAccount(newSavingsAccount);
		}
	}

	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException {
		if (getCombinedBalance() + savingsAccount.getBalance() >= BALANCE_LIMIT) {
			throw new ExceedsCombinedBalanceLimitException(
					"You have reached the maximum total balance across all accounts. Cannot create another.");

		} else {
			SavingsAccount[] newArray = new SavingsAccount[saveAccounts.length + 1];
			int i;
			for (i = 0; i < saveAccounts.length; i++) {
				newArray[i] = saveAccounts[i];
			}
			newArray[i] = savingsAccount;
			saveAccounts = newArray;
			return savingsAccount;
		}
	}

	// Add CD Accounts
	public CDAccount addCDAccount(CDAccount cdAccount) {
		CDAccount[] tempArray = new CDAccount[cdAccounts.length + 1];
		int i;
		for (i = 0; i < cdAccounts.length; i++) {
			tempArray[i] = cdAccounts[i];
		}
		tempArray[i] = cdAccount;
		cdAccounts = tempArray;
		return cdAccount;
	}

	public CDAccount addCDAccount(CDOffering offering, double openingBalance)
			throws ExceedsFraudSuspicionLimitException {

		if (openingBalance > 1000) {
			throw new ExceedsFraudSuspicionLimitException(
					"The opening balance has exceeded the fraud suspicion limit.");
		}

		CDAccount newName = new CDAccount(offering, openingBalance);
		DepositTransaction newTransaction = new DepositTransaction(newName, openingBalance);

		return addCDAccount(newName);
	}

	@Override
	public String toString() {
		return "Name: " + this.firstName + " " + this.middleName + " " + this.lastName + "\n" + "SSN: " + this.ssn
				+ "\n" + this.getCheckingAccounts().toString();
	}

	public static long getNewAccountNumber() {

		return nextAccountNumber += 1;
	}

	public static AccountHolder readFromString(String accountHolderData) throws ParseException {

		String[] newAccountHolder = accountHolderData.split(",");
		return new AccountHolder(newAccountHolder[0], newAccountHolder[1], newAccountHolder[2], newAccountHolder[3]);

	}

	public String writeToString() {
		String newString = this.firstName + "," + this.middleName + "," + this.lastName + "," + this.ssn;
		return newString;
	}

	@Override
	public int compareTo(AccountHolder accountHolder) {
		if (this.getCombinedBalance() > accountHolder.getCombinedBalance()) {
			return 1;
		} else {
			return -1;
		}
	}

}
