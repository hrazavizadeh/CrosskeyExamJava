
public class customerLoan {

	private String customerName = "";
    private double totalLoan = 0;
    private double interest = 0;
    private int years = 0;
    private double monthlyPay=0;
    
    public customerLoan(String Name, double TotalLoan, double Interest, int Years)
    {
    	try
    	{
    		this.customerName = Name;
    		this.totalLoan = TotalLoan;
    		this.interest = Interest;
    		this.years = Years;
    		
    		//Calculating customer monthly payment using power method of class
    		monthlyPay = (TotalLoan * (Interest /100)* HPow(1 + (Interest/100), Years*12)) / (HPow(1 + (Interest/100), (Years*12)) - 1);
    	}
    	catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    }

    //Create Power Method with loop
    private double HPow(double number1, int number2)
    {
        double powerResult = number1;
        for (int counter = 1; counter < number2; counter++)
            powerResult *= number1;
        return powerResult;
    }


    public double getMonthlyPay()
    {        
            return monthlyPay;              
    }
    
    public String getName()
    {
    	return customerName;
    }
    
    public double getTotalLoan()
    {
    	return totalLoan;
    }
    
    public double getInterest()
    {
    	return interest;
    }
    
    public int getYears()
    {
    	return years;
    }
    
}
