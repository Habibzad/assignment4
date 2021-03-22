package com.meritamerica.assignment4;

public class WithdrawTransaction extends Transaction {
	WithdrawTransaction(BankAccount targetAccount, double amount) {
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.isProcessed = false;
	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		if (amount > 1000) {
			FraudQueue.addTransaction(this);
			throw new ExceedsFraudSuspicionLimitException("Amount greater than 1000!");
		}

		if (amount <= 0) {
			throw new NegativeAmountException("Negative amount cannot be transffered!");
		}

		if (targetAccount.getBalance() < amount) {
			throw new ExceedsAvailableBalanceException("Insufficient funds!");
		}
		this.targetAccount.withdraw(amount);
	}

}
