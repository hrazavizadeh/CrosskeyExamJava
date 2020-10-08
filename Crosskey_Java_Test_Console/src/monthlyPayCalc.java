import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class monthlyPayCalc {
	public static void main(String[] args)throws Exception

	{
		BufferedReader reader =null;
		try
		{
		Scanner userInput=new Scanner(System.in);		
		String filePath="";
		String record="";
		ArrayList<customerLoan> customerList=new ArrayList<customerLoan>();
		
		
		do{
			customerList.clear();
			System.out.println("Please Enter the file path and name:\r\n(Exit = Q)");
			filePath=userInput.nextLine();
			if(filePath.equalsIgnoreCase("Q"))
				break;
			if(!new File(filePath).exists())
			{
				System.out.print("File not found!!!!!!\r\n\r\n");				
				continue;
			}
			//Open file from entered path
			reader = new BufferedReader(new FileReader(filePath));
			record = reader.readLine();
		
			//Send every line which has data and call creatCustomer method, result store in an array list 
			while ((record = reader.readLine()) != null)
			{
				if(record.length()>0 && !record.equals("."))
					customerList.add(creatCustomer(record));
			}
			reader.close();
	    
			//Display Result
			showResult(customerList);
			
			System.out.println();
			System.out.println("Save the result? (Y/N)");
			if(userInput.nextLine().equalsIgnoreCase("Y"))
				//Save Report in TXT file
				exportReport(customerList, filePath);
			
			System.out.println();
			System.out.println("Exit? (Y/N)");
		}while(!userInput.nextLine().equalsIgnoreCase("y"));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(reader!=null)
				reader.close();
		}
	}
	
	
	
	//Extract files from a record and return an customerLoan objec
	private static customerLoan creatCustomer(String record)
	{
		try
		{
		String customerName="";
		int years=0;
		double totalLoan=0;
		double interest=0;
		
		
		int startIndex = 0;
		int fieldIndex = 0;
        String[] recordFields = new String[4];
        Boolean isquotation = false;
        int pointer = 0;
        
        //search till end of record
        while (pointer < record.length())
        {
            /*check every character in record until finding ','
            if before finding ',' it was a '"' in record, ignore the ',' until you find another '"'*/
            while (pointer < record.length() && (isquotation || record.charAt(pointer) != ','))
            {
                //if current character is '"', change value of isquotation
                if (record.charAt(pointer) == '"') isquotation = !isquotation;
                pointer++;

            }
            //extract the found field by substring from record
            recordFields[fieldIndex] = record.substring(startIndex, pointer);
            pointer++;
            fieldIndex++;
            //Set start Index to position of first character of next field
            startIndex = pointer;
        }
				        
		return new customerLoan(recordFields[0].replace('"',' ').trim(),Double.parseDouble(recordFields[1]) , Double.parseDouble(recordFields[2]), Integer.parseInt(recordFields[3]));
		
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	
	
	//Display each record in array list
	private static void showResult(ArrayList<customerLoan> customerlist)
	{
		try
		{
			System.out.println(String.format("%-30s %-12s %-10s %-10s %-10s\n", "Customer Name","Total loan","Interest","Years","Monthly Pay"));
			for (customerLoan mycustomerloan : customerlist) 	  
				System.out.println(String.format("%-30s %,-12.2f %,-10.2f %,-10d %,-10.2f", mycustomerloan.getName(),mycustomerloan.getTotalLoan(),mycustomerloan.getInterest(),mycustomerloan.getYears(),mycustomerloan.getMonthlyPay()));
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	//Save the result in text file
	private static void exportReport(ArrayList<customerLoan> customerlist, String path) throws IOException
	{
		FileWriter exportWriter =null;
		 try 
		 {			
			 //Create Export file in the same path of selected import file
			 String exportPath="";
			 Path expPath=Paths.get(path);
			 exportPath=expPath.getParent() + "Report_"+expPath.getFileName().toString().substring(0,expPath.getFileName().toString().length()-4)+".txt";
		     exportWriter = new FileWriter(exportPath);
		      		    
		     exportWriter.write(String.format("%-30s|%-12s|%-10s|%-10s|%-10s\r\n\r\n", "Customer Name","Total loan","Interest","Years","Monthly Pay"));
			 for (customerLoan mycustomerloan : customerlist) 	  
				 exportWriter.write(String.format("%-30s|%,-12.2f|%,-10.2f|%,-10d|%,-10.2f\r\n", mycustomerloan.getName(),mycustomerloan.getTotalLoan(),mycustomerloan.getInterest(),mycustomerloan.getYears(),mycustomerloan.getMonthlyPay()));
				
				
		      exportWriter.close();
		      System.out.println("The Report Successfully Saved. \r\nPath = " + exportPath);
		      
		 } 
		 catch (IOException ex) 
		 {
		    	System.out.println(ex.getMessage());
		 }
		 finally {
			 if(exportWriter!=null )
				 exportWriter.close();
		}
		
	}
}
