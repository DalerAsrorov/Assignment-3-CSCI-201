package usc.edu;

public class Deluxe extends SavingsAccount 
{
	static final double INTEREST_RATE = 5.0;
	public Deluxe(double balance) 
	{
		super(balance);
	}
	public String getAccountType()
	{
		return "Deluxe Savings";
	}
	public static double getInterestRate()
	{
		return INTEREST_RATE;
	}
}
