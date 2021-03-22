package com.meritamerica.assignment4;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class CDAccount extends BankAccount {
	private CDOffering offering;
	static private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	// Constructor
	CDAccount(CDOffering offering, double balance) {
		super(balance);
		this.offering = offering;
	}

	CDAccount(long accountNumber, double balance, double interestRate, Date startDate, int term) {
		super(accountNumber, balance, interestRate, startDate, term);
		offering = new CDOffering(term, interestRate);

	}

	// Class methods:
	public double getInterestRate() {
		return this.offering.getInterestRate();
	}

	int getTerm() {
		return this.offering.getTerm();
	}

	public static CDAccount readFromString(String accountData) throws ParseException {
		String[] newCDAccount = accountData.split(",");
		long accountNumber = Long.parseLong(newCDAccount[0]);
		double balance = Double.parseDouble(newCDAccount[1]);
		double interest = Double.parseDouble(newCDAccount[2]);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Date date = (Date) dateFormat.parse(newCDAccount[3]);
		int term = -1;
		try {
			term = Integer.parseInt(newCDAccount[4]);
		} catch (NumberFormatException e) {
			System.out.println("Number Format Exception");
		} catch (Exception e) {
			System.out.println("Exception occured");
			throw new ParseException("Exception Number Format", term);
		}
		CDAccount account = new CDAccount(accountNumber, balance, interest, date, term);
		System.out.println();
		return account;
	}

	@Override
	public boolean deposit(double amount) {
		return false;
	}

	@Override
	public boolean withdraw(double amount) {
		return false;
	}

	public double futureValue() {
		return this.getBalance() * (Math.pow(1 + this.getInterestRate(), this.getTerm()));
	}
}
