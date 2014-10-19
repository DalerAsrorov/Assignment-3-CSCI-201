package usc.edu;

import java.util.*;
import java.io.*;
public class Test 
{
	private static BaseAccount [] accounts = new BaseAccount[2];
	private static String username;
	private static String password;
	private static PrintWriter input;
	private static Scanner scanner;
	private static double totalBalance;
	private static String textFileName;
	private static String inputStr = "";
	public static double checkBal = 0;
	
	public static void main(String [] args)
	{
		Scanner scan = new Scanner(System.in);
		String choice = "";
		boolean check = true; 
		
		do
		{	
			try
			{
				System.out.print("Welcome to the bank. \n 1) Existing account holder "
						+ "\n 2) Open a New Account"
						+ "\nWhat would you like to do? ");
				choice = scan.nextLine();
				if (choice.equals("1"))
				{
					//check = true;
					loginForExistingUser();
					
					if(!inputStr.equals("q"))
						menu();
				}
				else if (choice.equals("2"))
				{
					loginForNewUser();
					check = false;
				}
				else
					throw new InputMismatchException();
			}
			catch(InputMismatchException e)
			{
				check = false;
				System.out.println("Incorrect input. Try again. ");
				//choice = scan.nextLine();
			}
		}while(!check || inputStr.equalsIgnoreCase("q"));	
	}
	
	public static void loginForExistingUser()
	{
		Scanner scan = new Scanner(System.in);
		boolean check = true;
		String fileName = "";
		while (check)
		{
			System.out.print("Username: ");
			inputStr = scan.nextLine();
			if (inputStr.equalsIgnoreCase("q"))
				break;
			username = inputStr;
			System.out.print("Password: ");
			password = scan.nextLine();
			
			fileName = username + ".txt";
	
			if (checkIfUserExists(fileName) == false)
			{
				check = false;
				boolean comb = checkCombination(fileName, username, password);
				if (comb == true)
				{
					textFileName = fileName;
					storeToArray();
					checkBal = accounts[0].getBalance();
					menu();
				}
				else
				{	
					check = true;
					System.out.println("I’m sorry, but that username and password does not "
							+ "match any at our bank. Please try again "
							+ "(or enter ‘q’ to return to the main menu). ");
				}	
			}
			else
			{
				check = true;
				System.out.println("I’m sorry, but that username and password does not "
						+ "match any at our bank. Please try again "
						+ "(or enter ‘q’ to return to the main menu). ");
			}
		}
	}
	public static boolean checkCombination(String file, String givenUsername, String givenPassword) 
	{
		boolean check = false;
		Scanner giveMe = null;
		try
		{
			giveMe = new Scanner(new File(file));
			
			String skip1 = giveMe.next();    //skip the "Username:"
			String realUsername = giveMe.next(); 
			String skip2 = giveMe.next();    //skip the "Password:"
			String realPassword = giveMe.next();
			
			if (givenUsername.equals(realUsername) && givenPassword.equals(realPassword))
				check = true;
		}
		catch(Exception e)
		{
			System.out.println("Error while opening the file. ");
		}	
		giveMe.close();
		
		return check;
	}
	public static void loginForNewUser()
	{
		Scanner scan = new Scanner(System.in);
		String fileName = "";
		boolean check = true;
		while (check)
		{
			System.out.print("Username: ");
			inputStr  = scan.nextLine();
			if (inputStr.equalsIgnoreCase("q"))
				break;
			fileName = inputStr + ".txt";
			if (checkIfUserExists(fileName) == true)
			{	
				check = false;
				username = inputStr;
				textFileName = username + ".txt";
				System.out.println("Great, that username is not in use! ");
				System.out.print("Password: ");
				password = scan.nextLine();
				if (checkPassword(password) == true)
				{
					System.out.println("The password should contain more than one character with no spaces. Please try again. ");
					password = scan.nextLine();
				}
				getInfo();
				createFile();
				checkBal = accounts[0].getBalance();
			}
			else if (checkIfUserExists(fileName) == false)
			{
				check = true;
				System.out.println("I am sorry, but the username '" + inputStr + "' is already associated "
						+ "with an account. Please try again (or enter 'q' to go back to the main menu). ");
			}
		}	
	}
	private static boolean checkPassword(String password)
	{
		int len = password.length();
		boolean check = false;
		
		if (len  < 2)
		{
			check = true;
		}
		else
		{
			for (int i = 0; i < len; i++)
			{
				if (password.charAt(0) == ' ')
				{
					check = true;
				}
			}
		}
		return check;
	}
	private static boolean checkIfUserExists(String fileName)
	{
		boolean c = false;
		File file = new File(fileName);
		if (!file.exists())
			c = true;
		else
			c = false;
		return c;
	}
	private static void getInfo()
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("How much would you like to deposit in checking? ");
		double checkingAmount = scan.nextDouble();
		System.out.print("How much would you like to deposit in savings? ");
		double savingsAmount = scan.nextDouble();
		
		accounts[0] = new CheckingAccount(checkingAmount);
		accounts[1] = new SavingsAccount(savingsAmount); 
	}
	public static void createFile() 
	{
		try{
			input = new PrintWriter(textFileName);
		}
		catch(IOException ex){
			System.out.println("Could not create the file. " + ex.getMessage());
		}
		//Writing the data into the file
		input.println("Username: " + username);
		input.println("Password: " + password);
		input.println();
		input.println(accounts[0].getBalance() + " ---- Checking");
		input.println(accounts[1].getBalance() + " ---- Savings");
		input.flush();
		input.close();
	}
	public static void storeToArray()
	{
		double checkingBalance = 0;
		double savingsBalance = 0;
		try
		{
			scanner = new Scanner(new File(textFileName));
			String skip1 = scanner.nextLine(); //skip
			String skip2 = scanner.nextLine(); // skip
			String skip3 = scanner.nextLine(); // skip
			checkingBalance = scanner.nextDouble(); // ****checking*****
			String skip4 = scanner.nextLine();  //skip
			savingsBalance = scanner.nextDouble(); // *****savings*****
			accounts[0] = new CheckingAccount(checkingBalance);
			accounts[1] = new SavingsAccount(savingsBalance);
			setSavingsAccount();
		}
		catch(Exception ex)
		{
			System.out.println("Can't open the file. " + ex.getMessage());
		}
		scanner.close();
	}
	/**
	 *  |||||*********************** Option 1 ***********************|||||
	 */
	public static void showInfo()
	{
			System.out.println();
			System.out.println("You have a " + accounts[0].getAccountType() + 
					" with a balance of $" + accounts[0].getBalance());
			System.out.println("You have a " + accounts[1].getAccountType() + 
					" with a balance of $" + accounts[1].getBalance());
			
	}
	/**
	 *  |||||*********************** Option 2 ***********************|||||
	 */
	public static void deposit()
	{
		Scanner scan = new Scanner(System.in);
		String choice = "";
		boolean check = true;
		boolean subCheck = true;
		double valueDouble = 0.0;
		String value = "";
		System.out.println("\nHere are the accounts you have. ");
		System.out.println("1) " + accounts[0].getAccountType());
		System.out.println("2) " + accounts[1].getAccountType());
		do
		{	
			try
			{
				System.out.print("Into which account would you like to make a deposit? ");
				choice = scan.nextLine();
				choice = choice.trim();
				if (choice.equals("1"))
				{
					while(subCheck)
					{	
						try
						{
							System.out.print("How much to deposit into your " + accounts[0].getAccountType() + "? ");
							value = scan.nextLine();
							valueDouble = Double.parseDouble(value);
							
							if(accounts[0].deposit(valueDouble) == true)
							{	
								System.out.println("$" + value + " deposited into your "
										+ "" + accounts[0].getAccountType() + " account");
								System.out.println(accounts[0].getBalance());
								subCheck = false;
							}
							setSavingsAccount();
							update();
							checkBal = accounts[0].getBalance();
							//
							//
							//********************
						}
						catch(NumberFormatException ex)
						{
							subCheck = true;
							System.out.println("\"" + value + "\"" + "is not a valid amount. ");
						}
					}
				}
				else if (choice.equals("2"))
				{
					while(subCheck)
					{	
						try
						{
							System.out.print("How much to deposit into your " + accounts[1].getAccountType() + "? ");
							value = scan.nextLine();
							valueDouble = Double.parseDouble(value);
							
							if(accounts[1].deposit(valueDouble) == true)
							{	
								System.out.println("$" + value + " deposited into your "
										+ "" + accounts[1].getAccountType() + " account");
								System.out.println(accounts[1].getBalance());
								subCheck = false;
							}
							setSavingsAccount();
							update();
						}
						catch(NumberFormatException ex)
						{
							subCheck = true;
							System.out.println("\"" + value + "\"" + "is not a valid amount. ");
						}
					}
				}
				else
					throw new InputMismatchException();
			}
			catch(InputMismatchException ex)
			{
				check = false;
				System.out.println("The input is incorrect. Try again. ");
			}
		}while(!check);
	}
	/**
	 *  |||||*********************** Option 3 ***********************|||||
	 */
	public static void withdraw()
	{
		Scanner scan = new Scanner(System.in);
		String choice = "";
		boolean check = true;
		boolean subCheck = true;
		double valueDouble = 0.0;
		String value = "";
		System.out.println();
		System.out.println("\nHere are the accounts you have. ");
		System.out.println("1) " + accounts[0].getAccountType());
		System.out.println("2) " + accounts[1].getAccountType());
		do
		{	
			try
			{
				System.out.print("From which account would you like to withdraw? ");
				choice = scan.nextLine();
				choice = choice.trim();
				if (choice.equals("1"))
				{
					while(subCheck)
					{	
						try
						{
							System.out.print("How much to withdraw? ");
							value = scan.nextLine();
							valueDouble = Double.parseDouble(value);
							
							if (valueDouble > accounts[0].getBalance())
							{
								subCheck = true;
								System.out.println("You do not have " + valueDouble + " in your account");
							}
							else if(accounts[0].withdraw(valueDouble) == true)
							{	
								System.out.println("$" + value + " withdrawn from your "
										+ "" + accounts[0].getAccountType() + " account");
								//System.out.println(accounts[0].getBalance());
								subCheck = false;
								checkBal = accounts[0].getBalance();
							}
							setSavingsAccount();
							update();
						}
						catch(NumberFormatException ex)
						{
							subCheck = true;
							System.out.println("\"" + value + "\"" + "is not a valid amount. ");
						}
					}
				}
				else if (choice.equals("2"))
				{
					while(subCheck)
					{	
						try
						{
							System.out.print("How much to withdraw? ");
							value = scan.nextLine();
							valueDouble = Double.parseDouble(value);
							
							if (valueDouble > accounts[1].getBalance())
							{
								subCheck = true;
								System.out.println("You do not have $" + valueDouble + "in your account. ");
							}
							else if(accounts[1].withdraw(valueDouble) == true)
							{	
								System.out.println("$" + value + " withdrawn from your "
										+ "" + accounts[1].getAccountType() + " account");
								//System.out.println(accounts[1].getBalance());
								subCheck = false;
							}
							setSavingsAccount();
							update();
						}
						catch(NumberFormatException ex)
						{
							subCheck = true;
							System.out.println("\"" + value + "\"" + "is not a valid amount. ");
						}
					}
				}
				else
					throw new InputMismatchException();
			}
			catch(InputMismatchException ex)
			{
				check = false;
				System.out.println("The input is incorrect. Try again. ");
			}
		}while(!check);
	}
	/**
	 *  |||||*********************** Option 4 ***********************|||||
	 */
	public static void determineBalanceInXYears()
	{
		Scanner scan = new Scanner(System.in);
		String getInput = "";
		int numOfYears = 0;
		setSavingsAccount();
		
		boolean check = true;
		do
		{
			try
			{
				System.out.print("\nIn how many years? ");
				getInput = scan.nextLine();
				numOfYears = Integer.parseInt(getInput);
				
				if (numOfYears < 0)
				{
					check = true;
					System.out.println("The number of years cannot be a negative number. "
							+ "Please try again...");
					
				}
				else
				{
					check = false;
					System.out.println("Calculation here... ");
					SavingsAccount s = new SavingsAccount(accounts[1].getBalance());
					s.getBalanceAfterNumYears(numOfYears);
				}
				
			}
			catch (NumberFormatException ex)
			{
				check = true;
				System.out.println("Wrong input. Please try again... ");
			}
		}while(check);
		
	}
	public static void setSavingsAccount()
	{
		totalBalance = accounts[0].getBalance() + accounts[1].getBalance();
		double savingsAmount = accounts[1].getBalance();
		if (totalBalance < 1000)
			accounts[1] = new Basic(savingsAmount);
		else if(totalBalance > 1000 && totalBalance < 10000)
			accounts[1] = new Premium(savingsAmount);
		else if (totalBalance > 10000)
			accounts[1] = new Deluxe(savingsAmount);
	}
	public static void update()
	{
		try{
			input = new PrintWriter(textFileName);
		}
		catch(IOException ex){
			System.out.println("Could not create the file. " + ex.getMessage());
		}
		//Writing the data into the file
		input.println("Username: " + username);
		input.println("Password: " + password);
		input.println();
		input.println(accounts[0].getBalance() + " $ ---- Checking");
		input.println(accounts[1].getBalance() + " $ ---- Savings");
		checkBal = accounts[0].getBalance();
		input.flush();
		input.close();
	}
	public static void menu()
	{
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		boolean check = true;
		
		do
		{
			try
			{	
				System.out.println("\nWelcome to your accounts, " + username + ". " 
								+	"\n1) View Account Information "
								+	"\n2) Make a Deposit "
								+	"\n3) Make a Withdrawal "
								+	"\n4) Determine Balance in x Years "
								+	"\n5) Logout");
				choice = scan.nextInt();
				if(choice == 1)
				{
					showInfo();
					check = false;
				}
				else if (choice == 2)
				{
					deposit();
					check = false;
				}
				else if (choice == 3)
				{
					withdraw();
					check = false;
				}
				else if (choice == 4)
				{
					determineBalanceInXYears();
					check = false;
				}
				else if (choice == 5)
				{
					System.out.println("\nThank you for coming into the bank!");
					System.exit(0);
				}
				else 
					throw new InputMismatchException();
			}
			catch(InputMismatchException ex)
			{
				check = false;
				System.out.println("Wrong input. Please enter the correct number of the operation you want to perform. ");
				String c = scan.nextLine();
			}
		}while(!check);
	}
}		