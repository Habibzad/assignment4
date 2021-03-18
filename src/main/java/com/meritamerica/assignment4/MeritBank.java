package com.meritamerica.assignment4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.meritamerica.assignment3.CDOffering;

public class MeritBank {
	static AccountHolder[] accountHolders = new AccountHolder[0];
    static CDOffering[] cdOfferings = new CDOffering[0];
    static long nextAccountNumber = 0;

    //Create a new array of account holders, add the new account holder, and assign the new array to the accountHolders array
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
        return nextAccountNumber++;
    }

    static void setNextAccountNumber(long newAccountNumber) {
        nextAccountNumber = newAccountNumber;
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
    	
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileName))) {

            nextAccountNumber = Long.valueOf(bufferReader.readLine()); // Sets the masterAccountNumber to 11

            CDOffering[] newOfferings = new CDOffering[Integer.valueOf(bufferReader.readLine())];

            for (int i = 0; i < newOfferings.length; i++) {
                newOfferings[i] = CDOffering.readFromString(bufferReader.readLine());
            }
            cdOfferings = newOfferings;

            AccountHolder[] newAccountHolders = new AccountHolder[Integer.valueOf(bufferReader.readLine())];
            for (int i = 0; i < newAccountHolders.length; i++) {
                newAccountHolders[i] = AccountHolder.readFromString(bufferReader.readLine());

                CheckingAccount[] newCheckingAccounts = new CheckingAccount[Integer.valueOf(bufferReader.readLine())];
                for (int j = 0; j < newCheckingAccounts.length; j++) {
                    newAccountHolders[i].addCheckingAccount(CheckingAccount.readFromString(bufferReader.readLine()));

                }

                SavingsAccount[] newSavingsAccounts = new SavingsAccount[Integer.valueOf(bufferReader.readLine())];
                for (int j = 0; j < newSavingsAccounts.length; j++) {
                    newAccountHolders[i].addSavingsAccount(SavingsAccount.readFromString(bufferReader.readLine()));
                }

                CDAccount[] newCDAccounts = new CDAccount[Integer.valueOf(bufferReader.readLine())];
                for (int j = 0; j < newCDAccounts.length; j++) {
                    newAccountHolders[i].addCDAccount(CDAccount.readFromString(bufferReader.readLine()));
                }
            }
            accountHolders = newAccountHolders;

            return true;

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");

        } catch (IOException e) {
            System.out.println("Error: Input / output error!");

        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    static boolean writeToFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedReader = new BufferedWriter(fileWriter);

            bufferedReader.write(String.valueOf(nextAccountNumber));
            bufferedReader.newLine();
            bufferedReader.write(String.valueOf(cdOfferings.length));
            bufferedReader.newLine();
            for (int i = 0; i < cdOfferings.length; i++) {
            	bufferedReader.write(cdOfferings[i].writeToString());
            	bufferedReader.newLine();
            }
            bufferedReader.write(String.valueOf(accountHolders.length));
            bufferedReader.newLine();
            for (int i = 0; i < accountHolders.length; i++) {
            	bufferedReader.write(accountHolders[i].writeToString());
            	bufferedReader.newLine();
            	bufferedReader.write(accountHolders[i].getNumberOfCheckingAccounts());
                for (int j = 0; j < accountHolders[i].getNumberOfCheckingAccounts(); j++) {
                	bufferedReader.write(String.valueOf(accountHolders[i].getCheckingAccounts()[j].writeToString()));
                	bufferedReader.newLine();
                }
                for (int k = 0; k < accountHolders[i].getNumberOfSavingsAccounts(); k++) {
                	bufferedReader.write(String.valueOf(accountHolders[i].getSavingsAccounts()[k].writeToString()));
                	bufferedReader.newLine();
                }
                for (int g = 0; g < accountHolders[i].getNumberOfCDAccounts(); g++) {
                	bufferedReader.write(String.valueOf(accountHolders[i].getCDAccounts()[g].writeToString()));
                	bufferedReader.newLine();
                }
            }
            bufferedReader.close();
            return true;
        } catch (IOException e) {
            return false;
        }

    }

}
