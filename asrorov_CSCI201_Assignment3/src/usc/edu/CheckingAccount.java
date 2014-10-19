package usc.edu;

public class CheckingAccount extends BaseAccount
{
	public CheckingAccount(double balance) 
	{
		super(balance);
	}
	protected double getBalanceAfterNumYears(int numYears) 
	{
		return 0;
	}
	public String getAccountType() 
	{
		return "Checking";
	}
	
}
