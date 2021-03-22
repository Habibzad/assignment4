package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Transaction {

	BankAccount sourceAccount;
	BankAccount targetAccount;
	double amount;
	java.util.Date transactionDate;
	String rejectionReason;
	boolean isProcessed;
	static private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	BankAccount getSourceAccount() {
		return this.sourceAccount;
	}

	public void setSourceAccount(BankAccount sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public BankAccount getTargetAccount() {
		return this.targetAccount;
	}

	public void setTargetAccount(BankAccount targetAccount) {
		this.targetAccount = targetAccount;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public java.util.Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(java.util.Date date) {
		this.transactionDate = date;
	}

	public String writeToString() {
		return this.sourceAccount + "," + this.targetAccount + "," + this.amount + "," + this.transactionDate;
	}

	public static Transaction readFromString(String transactionDataString) throws ParseException {
		Transaction transaction;
		ArrayList<String> transactionsList = new ArrayList<>(Arrays.asList(transactionDataString.split(",")));
		if (transactionsList.get(0).equals("-1")) {
			if (Double.parseDouble(transactionsList.get(2)) > 0) {
				transaction = new DepositTransaction(MeritBank.getBankAccount(Long.parseLong(transactionsList.get(1))),
						Double.parseDouble(transactionsList.get(2)));
				transaction.setTransactionDate(formatter.parse(transactionsList.get(3)));
				return transaction;
			} else {
				transaction = new WithdrawTransaction(MeritBank.getBankAccount(Long.parseLong(transactionsList.get(1))),
						Math.abs(Double.parseDouble(transactionsList.get(2))));
				transaction.setTransactionDate(formatter.parse(transactionsList.get(3)));
				return transaction;
			}
		} else {
			transaction = new TransferTransaction(MeritBank.getBankAccount(Long.parseLong(transactionsList.get(0))),
					MeritBank.getBankAccount(Long.parseLong(transactionsList.get(1))),
					Math.abs(Double.parseDouble(transactionsList.get(2))));
			transaction.setTransactionDate(formatter.parse(transactionsList.get(3)));
			return transaction;
		}
	}

	public abstract void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;

	public boolean isProcessedByFraudTeam() {
		if (this.isProcessed == true) {
			return true;
		} else {
			return false;
		}
	}

	public void setProcessedByFraudTeam(boolean isProcessed) {
		this.isProcessed = true;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String reason) {
		this.rejectionReason = reason;
	}

}
