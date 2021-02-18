import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.lang.Number;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
/**
 * 
 * CSCU9T4 Java strings and files exercise.
 *
 */
public class Table {
	private String name;
	private Date date;
    public Table(String n, Date d)
    {
    	this.name = n;
    	this.date = d;	
    }

    public String getName()
    {
    	return this.name;
    }
    
    public Date getDate()
    {
    	return this.date;
    }
} // FilesInOut
