package com.meritamerica.assignment4;

public class FraudQueue {

	private static Transaction fraudTransaction;

	public static void addTransaction(Transaction transaction) {
		fraudTransaction = transaction;
	}

	public Transaction getTransaction() {
		return fraudTransaction;
	}

}
