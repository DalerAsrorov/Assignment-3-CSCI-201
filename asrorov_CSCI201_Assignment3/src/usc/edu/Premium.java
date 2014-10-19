package usc.edu;

public class Premium extends SavingsAccount
{
	final static double INTEREST_RATE = 1.0;
	public Premium(double balance) 
	{
		super(balance);
	}
	public String getAccountType()
	{
		return "Premium Savings";
	}
	public static double getInterestRate()
	{
		return INTEREST_RATE;
	}
}
