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
	
    public static void main(String[] args)
    {
    	do 
    	{
    		System.out.println("Sypply flag: -d, -u or -h");
    		flag = in.next();
    		if(flag.equals("-d") || flag.equals("-u")|| flag.equals("-h"))validInput = true;
    		else validInput = false;
    	}while(validInput == false);
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
    	
    	validInput = false;
    	System.out.println("Supply filename for output:");
    	do
    	{
	    	try {
	    		outputFileName = in.next();
	    		outputFile = new File(outputFileName) ;
	    		writer = new PrintWriter(outputFile);
	    		String getName = outputFile.getName();
	    		int index = getName.lastIndexOf(".");
	    		if (((index == -1) ? "" : getName.substring(index + 1)).equals("html"))
	    			{
	    					if(flag.equals("-h"))validInput = true;
	    					else 
	    		    		{
	    		    			validInput = false;
	    		    			System.out.println("Enter a valid output type.");
	    		    		}
	    			}
	    		if (((index == -1) ? "" : getName.substring(index + 1)).equals("txt"))
    			{
    					if(flag.equals("-d") || flag.equals("-u"))validInput = true;
    					else 
    		    		{
    		    			validInput = false;
    		    			System.out.println("Enter a valid output type.");
    		    		}
    			}
	    		} catch (FileNotFoundException e) {
	    			
	    			System.err.println("FileNotFoundException: " + e.getMessage() + "not openable");
	    			validInput = false;
	    			}

    	}while(validInput == false);
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
    }
    private static void titleCase()
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
                	lineName += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";
                }
            }
            try
            {
            	Table t = new Table(lineName.trim(), newDate.parse(lineDate));
                table.add(t);
            }
            catch(Exception e) {}
    	}
    }
    
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
    }
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
    }
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
    }
} // FilesInOut
