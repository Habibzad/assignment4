package com.meritamerica.assignment4;

public class CDOffering {

	private double interestRate;
	private int term;

	CDOffering(int term, double interestRate) {
		this.interestRate = interestRate;
		this.term = term;
	}

	int getTerm() {
		return this.term;
	}

	double getInterestRate() {
		return this.interestRate;
	}

	public static CDOffering readFromString(String cdOfferingDataString) {
		String[] str = cdOfferingDataString.split(",");
		return new CDOffering(Integer.parseInt(str[0]), Double.parseDouble(str[1]));
	}

	public String writeToString() {
		String newString = this.interestRate + "," + this.term;
		return newString;
	}
}
