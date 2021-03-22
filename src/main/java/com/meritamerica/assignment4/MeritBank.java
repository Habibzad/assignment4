package com.meritamerica.assignment4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeritBank {

	private static AccountHolder[] accountHolders = new AccountHolder[0];
	private static CDOffering[] cdOfferings = new CDOffering[0];
	private static List<FraudQueue> frauds = new ArrayList<FraudQueue>();
	private static long masterAccountNumber = 0;

	static void addAccountHolder(AccountHolder accountHolder) {

		AccountHolder[] newAccountHolders = new AccountHolder[accountHolders.length + 1];
		int i = 0;
		for (i = 0; i < accountHolders.length; i++) {
			newAccountHolders[i] = accountHolders[i];
		}
		newAccountHolders[i] = accountHolder;
		accountHolders = newAccountHolders;
	}

	static AccountHolder[] getAccountHolders() {
		return accountHolders;
	}

	static AccountHolder[] sortAccountHolders() {
		Arrays.sort(accountHolders);
		return accountHolders;
	}

	static long getNextAccountNumber() {
		return masterAccountNumber++;
	}

	static void setNextAccountNumber(long newAccountNumber) {
		masterAccountNumber = newAccountNumber;
	}

	static CDOffering[] getCDOfferings() {
		return cdOfferings;
	}

	static void setCDOfferings(CDOffering[] offerings) {
		cdOfferings = offerings;
	}

	static CDOffering getBestCDOffering(double depositAmount) {
		return null;
	}

	static CDOffering getSecondBestCDOffering(double depositAmount) {

		return null;
	}

	static void clearCDOfferings() {
		cdOfferings = new CDOffering[0];
	}

	static double totalBalances() {
		double total = 0;
		for (int i = 0; i < accountHolders.length; i++) {
			total += accountHolders[i].getCombinedBalance();
		}
		return total;
	}

	public static boolean readFromFile(String fileName) {
		File input = new File(fileName);

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(input))) {

			masterAccountNumber = Long.valueOf(bufferedReader.readLine());
			CDOffering[] newOfferings = new CDOffering[Integer.valueOf(bufferedReader.readLine())];

			for (int i = 0; i < newOfferings.length; i++) {
				newOfferings[i] = CDOffering.readFromString(bufferedReader.readLine());
			}
			cdOfferings = newOfferings;

			AccountHolder[] newAccountHolders = new AccountHolder[Integer.valueOf(bufferedReader.readLine())];
			for (int i = 0; i < newAccountHolders.length; i++) {
				newAccountHolders[i] = AccountHolder.readFromString(bufferedReader.readLine());

				CheckingAccount[] newCheckingAccounts = new CheckingAccount[Integer.valueOf(bufferedReader.readLine())];
				for (int j = 0; j < newCheckingAccounts.length; j++) {
					newAccountHolders[i].addCheckingAccount(CheckingAccount.readFromString(bufferedReader.readLine()));

					// Set up transactions for checking accounts:
					ArrayList<Transaction> transactions = new ArrayList<Transaction>();
					int counter = Integer.valueOf(bufferedReader.readLine());

					for (int k = 0; k < counter; k++) {
						transactions.add(Transaction.readFromString(bufferedReader.readLine()));
					}
				}

				SavingsAccount[] newSavingsAccounts = new SavingsAccount[Integer.valueOf(bufferedReader.readLine())];
				for (int j = 0; j < newSavingsAccounts.length; j++) {
					newAccountHolders[i].addSavingsAccount(SavingsAccount.readFromString(bufferedReader.readLine()));

					ArrayList<Transaction> transactions = new ArrayList<Transaction>();
					int counter = Integer.valueOf(bufferedReader.readLine());

					for (int k = 0; k < counter; k++) {
						transactions.add(Transaction.readFromString(bufferedReader.readLine()));
					}
				}

				CDAccount[] newCDAccounts = new CDAccount[Integer.valueOf(bufferedReader.readLine())];
				for (int j = 0; j < newCDAccounts.length; j++) {
					newAccountHolders[i].addCDAccount(CDAccount.readFromString(bufferedReader.readLine()));

					ArrayList<Transaction> transactions = new ArrayList<Transaction>();
					int counter = Integer.valueOf(bufferedReader.readLine());

					for (int k = 0; k < counter; k++) {
						transactions.add(Transaction.readFromString(bufferedReader.readLine()));
					}
				}
			}

			List<Transaction> fraudTransactions = new ArrayList<Transaction>();
			int counter = Integer.valueOf(bufferedReader.readLine());

			for (int i = 0; i < counter; i++) {
				fraudTransactions.add(Transaction.readFromString(bufferedReader.readLine()));
			}
			accountHolders = newAccountHolders;
			return true;

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		} catch (IOException e) {
			System.out.println("Input / output error!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	static boolean writeToFile(String fileName) {
		try {
			FileWriter fieWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fieWriter);

			bufferedWriter.write(String.valueOf(masterAccountNumber));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(cdOfferings.length));
			bufferedWriter.newLine();
			for (int i = 0; i < cdOfferings.length; i++) {
				bufferedWriter.write(cdOfferings[i].writeToString());
				bufferedWriter.newLine();
			}
			bufferedWriter.write(String.valueOf(accountHolders.length));
			bufferedWriter.newLine();
			for (int i = 0; i < accountHolders.length; i++) {
				bufferedWriter.write(accountHolders[i].writeToString());
				bufferedWriter.newLine();
				bufferedWriter.write(accountHolders[i].getNumberOfCheckingAccounts());
				for (int j = 0; j < accountHolders[i].getNumberOfCheckingAccounts(); j++) {
					bufferedWriter.write(String.valueOf(accountHolders[i].getCheckingAccounts()[j].writeToString()));
					bufferedWriter.newLine();
				}
				for (int k = 0; k < accountHolders[i].getNumberOfSavingsAccounts(); k++) {
					bufferedWriter.write(String.valueOf(accountHolders[i].getSavingsAccounts()[k].writeToString()));
					bufferedWriter.newLine();
				}
				for (int g = 0; g < accountHolders[i].getNumberOfCDAccounts(); g++) {
					bufferedWriter.write(String.valueOf(accountHolders[i].getCdAccounts()[g].writeToString()));
					bufferedWriter.newLine();
				}
			}
			bufferedWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		double total = amount;
		for (int i = years; i > 0; i--) {
			total *= (1 + interestRate);
		}
		return total;
	}

	public static boolean processTransaction(Transaction transaction)
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		try {
			transaction.process();

		} catch (NegativeAmountException e) {
			throw e;
		} catch (ExceedsAvailableBalanceException e) {
			throw e;
		} catch (ExceedsFraudSuspicionLimitException e) {
			throw e;
		}
		return true;
	}

	public static FraudQueue getFraudQueue() {
		return frauds.get(0);
	}

	public static BankAccount getBankAccount(long accountId) {

		for (int i = 0; i < accountHolders.length; i++) {
			for (int j = 0; j < accountHolders[i].getNumberOfCheckingAccounts(); j++) {
				if (accountHolders[i].getCheckingAccounts()[j].getAccountNumber() == accountId) {
					return accountHolders[i].getCheckingAccounts()[j];
				}
			}

			for (int j = 0; j < accountHolders[i].getNumberOfSavingsAccounts(); j++) {
				if (accountHolders[i].getSavingsAccounts()[j].getAccountNumber() == accountId) {
					return accountHolders[i].getSavingsAccounts()[j];
				}
			}

			for (int j = 0; j < accountHolders[i].getNumberOfCDAccounts(); j++) {
				if (accountHolders[i].getCdAccounts()[j].getAccountNumber() == accountId) {
					return accountHolders[i].getCdAccounts()[j];
				}
			}
		}
		return null;
	}
}
