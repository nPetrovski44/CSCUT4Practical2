import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.lang.Number;
import java.text.SimpleDateFormat;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
/**
 * 
 * CSCU9T4 Java strings and files exercise.
 *
 */
public class FilesInOut {
	
	static ArrayList<Table> table = new ArrayList<Table>();
	static SimpleDateFormat dateType = new SimpleDateFormat("ddmmyyyy");
	static SimpleDateFormat newDate = new SimpleDateFormat("dd/mm/yyyy");
	static Date date = null;
	static File inputFile;
	static File outputFile;
	static PrintWriter writer = null; 
	static String inputFileName;
	static String outputFileName;
	static Scanner in = new Scanner(System.in);
	static Scanner scan = null;
	static boolean validInput = false;
	static String flag = "";
	
	/**
	 * This is the main method where we check for the flag, inputFileName and outputFileName
	 * @param args
	 */
    public static void main(String[] args)
    {
    	/*
    	 * We check for the validity of the flag entered
    	 * -d for default
    	 * -u for all uperCase letters
    	 * -h for default html
    	*/
    	
    	do 
    	{
    		System.out.println("Sypply flag: -u or -h");
    		flag = in.next();
    		if(flag.equals("-d") || flag.equals("-u")|| flag.equals("-h"))validInput = true;
    		else validInput = false;
    	}while(validInput == false);
    	
    	/*
    	 * We check for the name of a inputFileName, if no such file exists
    	 * we prompt the user to enter a new name.
    	 */
    	do
    	{
	    	System.out.println("Supply filename for input:");
	    	try {
	    		inputFileName = in.next();
	    		inputFile = new File(inputFileName) ;
	    		scan = new Scanner(inputFile);
	    		scan.useDelimiter("\n");
	    		validInput = true;
	    		} catch (IOException e) {
	    			System.err.println("IOException: " + e.getMessage() + "not found");
	    			validInput = false;
	    			}
    	}while(validInput == false);
    
    	/*
    	 * We check for the name of a outputFileName and its type.
    	 * If there isn't such a file but the type is correct we create the file,
    	 * otherwise we prompt the user to enter a new name.
    	 */
    	
    	System.out.println("Supply filename for output:");
    	do
    	{
	    	try {
	    		outputFileName = in.next();
	    		outputFile = new File(outputFileName) ;
	    		writer = new PrintWriter(outputFile);
	    		
	    		//We check the type of the file
	    		String getName = outputFile.getName();
	    		int index = getName.lastIndexOf(".");
	    		
	    		//Check if the type matches with the flag choosen
	    		if(flag.equals("-h"))
	    			{
	    					if (((index == -1) ? "" : getName.substring(index + 1)).equals("html"))validInput = true;
	    					else 
	    		    		{
	    		    			validInput = false;
	    		    			System.out.println("Enter a valid output type.");
	    		    		}
	    			}
	    		if(flag.equals("-d") || flag.equals("-u"))
    			{
	    				if (((index == -1) ? "" : getName.substring(index + 1)).equals("txt"))validInput = true;
    					else 
    		    		{
    		    			validInput = false;
    		    			System.out.println("Enter a valid output type.");
    		    		}
    			}
	    		} catch (Exception e) {
	    			validInput = false;
	    			}

    	}while(validInput == false);
    	
    	//We then format the text from the inputFile depending on the flag
    	if(flag.equals("-d"))
    		{
	    		titleCase();
	    		print();
    		}
    	if(flag.equals("-u"))
    		{
    			allUpperCase();
    			print();
    		}
    	if(flag.equals("-h"))
    		{
    			titleCase();
    			printHTML();
    		}
    }//main
    
    /**
     * This method stores the names and birth dates of the people in the inputFile
     * into an arrayList of Objects Table
     */
    private static void titleCase()
    {
    	 //We go through each line in the text
    	while(scan.hasNext())
    	{
            String line = scan.next();
            String lineDate = "";
            String lineName = "";
            Scanner lineScan = new Scanner(line);
            
            //We go through each String value in that line
            while(lineScan.hasNext())
            {
                String word = lineScan.next();
                
                //If the string value could be transformed into a date correctly,
                //we do that and store the date in a new format
                try
                {
                	date = dateType.parse(word);
                	String formatedDate = newDate.format(date);
                	lineDate = formatedDate;
                	
                }catch(Exception e)
                {
                	//If the string we are currently on is of length - 1, we assume it is a middle name, we capitalize it and we add a '.'
                	if(word.length() == 1)
                	{
                		word += ".";
                	}
                	
                	//We separate the first letter of the word, capitalize it and add it to the rest of the word
                	lineName += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";
                }
            }
            //We add the names and the dates to the arrayList
            try
            {
            	Table t = new Table(lineName.trim(), newDate.parse(lineDate));
                table.add(t);
            }
            catch(Exception e) {
            	System.out.println("Error.");
            }
    	}
    }//titleCase
    
    /**
     * This method stores the formated names and birth dates of the people in the inputFile
     * into an arrayList of Objects Table. The difference is that this method changes all the letters
     * into upper case ones
     */
    private static void allUpperCase()
    {
    	while(scan.hasNext())
    	{
            String line = scan.next();
            String lineDate = "";
            String lineName = "";
            Scanner lineScan = new Scanner(line);
            while(lineScan.hasNext())
            {
                String word = lineScan.next();
                try
                {
                	date = dateType.parse(word);
                	String formatedDate = newDate.format(date);
                	lineDate = formatedDate;
                	
                }catch(Exception e)
                {
                	if(word.length() == 1)
                	{
                		word += ".";
                	}
                	//We change the entire string into upper cases.
                	lineName += word.toUpperCase() + " ";
                }
            }
            try
            {
            	Table t = new Table(lineName.trim(), newDate.parse(lineDate));
                table.add(t);
            }
            catch(Exception e) {}
    	}
    }//allUpperCase
    
    /**
     * This method goes through the arrayList, printing all the entries
     * and writing them onto the outputFile
     */
    private static void print()
    {
    	ListIterator<Table> iter = table.listIterator();
    	while(iter.hasNext())
    	{
    		Table current = iter.next();
    		String date2 = newDate.format(current.getDate());
    		System.out.printf("%-25s %s\n",current.getName(), date2);
    		writer.printf("%-25s %s\n",current.getName(), date2);
    	}
    	writer.close();
    }//print
    
    /**
     * This method goes through the arrayList, printing all the entries
     * in a table for HTML type files and writes that onto the file.
     */
    private static void printHTML()
    {
    	String fillingHTML;
    	
    	fillingHTML = "<!DOCTYPE html>\r\n" + 
    			"<html>\r\n" + 
    			"<head>\r\n" + 
    			"<style>\r\n" +
    			"table{width: 25%;}\r\n" +
    			"table, tr, th{border:1px solid;}\r\n" +
    			"</style>\r\n" +
    			"<title>Writing in an HTML file</title>\r\n" + 
    			"</head>\r\n" + 
    			"<body>\r\n" + 
    			"<table>\r\n" +
    			"<tr>\r\n" + "<th><i>Name</i></th>\r\n" + "<th><i>BirthDate</i></th>\r\n" + "</tr>\r\n";
    	
    	ListIterator<Table> iter = table.listIterator();
    	while(iter.hasNext())
    	{
    		Table current = iter.next();
    		String date2 = newDate.format(current.getDate());
    		System.out.printf("%-25s %s\n",current.getName(), date2);
    		fillingHTML += "<tr>\r\n" +
    				"<th>" + current.getName() + "</th>\r\n" +
    				"<th>" + date2 + "</th>\r\n" + "</tr>\r\n";
    	}
    	fillingHTML += "</table>\r\n" + "</body>\r\n" + "</html>\r\n";
    	writer.print(fillingHTML);
    	writer.close();
    }//printHTML
    
} // FilesInOut
