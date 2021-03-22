package com.meritamerica.assignment4;

public class DepositTransaction extends Transaction {
	// Instance variables
	BankAccount targetAccount;
	double amount;
	boolean isProcessed;

	// Constructor
	DepositTransaction(BankAccount targetAccount, double amount) {
		super();
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.isProcessed = false;
	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		if (amount > 1000) {
			FraudQueue.addTransaction(this);
			throw new ExceedsFraudSuspicionLimitException("Transaction greater than or equal to 1000: needs review");
		}

		if (amount <= 0) {
			throw new NegativeAmountException("Negative deposit not allowed");
		}

		this.targetAccount.deposit(amount);
	}

}
