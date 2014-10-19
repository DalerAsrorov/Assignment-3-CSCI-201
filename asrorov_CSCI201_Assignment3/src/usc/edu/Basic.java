package usc.edu;

public class Basic extends SavingsAccount
{
	final static double INTEREST_RATE = 0.1;
	public Basic(double balance) 
	{
		super(balance);
	}
	public String getAccountType()
	{
		return "Basic Savings";
	}
	public static double getInterestRate()
	{
		return INTEREST_RATE;
	}
}
