package usc.edu;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Scanner;

public class SavingsAccount extends BaseAccount
{
	private double checkingBalance = 0;
	public SavingsAccount(double balance) 
	{
		super(balance);
	}
	@Override
	public double getBalanceAfterNumYears(int numYears)
	{
		DecimalFormat df = new DecimalFormat("#.00");
		  
		double balance = this.getBalance();
		double checkBal = getCheckBal();
		double interestRate = 0;
		double interest = 0;
		int counter = 1;
		
		if (checkBal + balance <= 1000)
			interest = 0.001;
		else if (checkBal + balance > 1000 && checkBal < 10000)
			interest = 0.01;
		else
			interest = 0.05;
		interestRate = balance * interest;
		
		System.out.println("Year     Amount     Interest ");
		System.out.println("----     ------     -------- ");
		System.out.printf("%3d  %10s  %9s", 0, "$" + df.format(balance), "$" + df.format(interestRate));
		System.out.println();
		
		while(counter <= numYears)
		{
			if (checkBal + balance <= 1000)
				interest = 0.001;
			else if (checkBal + balance > 1000 && checkBal < 10000)
				interest = 0.01;
			else
				interest = 0.05;
				
			balance += interestRate;
			interestRate = balance * interest;
			
			if (counter == numYears)
				System.out.printf("%3d  %10s", counter, "$" + df.format(balance));
			else
				System.out.printf("%3d  %10s  %9s", counter, "$" + df.format(balance), "$" + df.format(interestRate));
			System.out.println();
			counter++;
		}
		System.out.println();
		//System.out.printf("%3d  %10.2f  %9.2f", numYears, balance, interestRate);
		return balance;
	}
	public String getAccountType()
	{
		return "Savings Account";
	}
	public double getCheckBal()
	{
		Test n = new Test();
		double checkingBalance = n.checkBal;
		
		return checkingBalance;
	}
	
}