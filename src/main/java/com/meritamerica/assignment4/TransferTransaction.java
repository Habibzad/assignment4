package com.meritamerica.assignment4;

public class TransferTransaction extends Transaction {
	TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount,
			double amount){
		
	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		
	}
}
