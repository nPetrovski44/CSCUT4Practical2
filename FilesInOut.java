import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


import java.lang.Number;
import java.text.SimpleDateFormat;
/**
 * 
 * CSCU9T4 Java strings and files exercise.
 *
 */
public class FilesInOut {
	
	protected static ArrayList<Table> table = new ArrayList<Table>();

    public static void main(String[] args) throws IOException 
    {
        // Replace this with statements to set the file name (input) and file name (output).
        // Initially it will be easier to hardcode suitable file names.
    	File input = new File("C:\\Users\\Nikolay Petrovski\\git\\CSCUT4Practical2\\inputm.txt");
    	File output = new File("C:\\Users\\Nikolay Petrovski\\git\\CSCUT4Practical2\\formatted.txt");
    	SimpleDateFormat dateType = new SimpleDateFormat("ddmmyyyy");
    	SimpleDateFormat newDate = new SimpleDateFormat("dd/mm/yyyy");
    	Date date = null;
    	
        // Set up a new Scanner to read the input file.
    	
    	FileWriter writer = new FileWriter(output);
    	Scanner scan = new Scanner(input);
    	scan.useDelimiter("\n");
    	
// Initially, echo the text to System.out to check you are reading correctly.
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
            writer.write(lineName.trim() + " " + lineDate + "\n");
    		System.out.printf("%-25s %s\n", lineName.trim(), lineDate);
    	}
    	writer.close();
    	
    	//print();
        // Then add code to modify the text to the output format.

        // Set up a new PrintWriter to write the output file.
        // Add suitable code into the above processing (because you need to do this line by line also.
        // That is, read a line, write a line, loop.

    	
        // Finally, add code to read the filenames as arguments from the command line.


    } // main
    
    public static void print()
    {
    	ListIterator<Table> iter = table.listIterator();
    	while(iter.hasNext())
    	{
    		Table current = iter.next();
    		System.out.println(current.getName() + " " + current.getDate());
    	}
    }

} // FilesInOut
