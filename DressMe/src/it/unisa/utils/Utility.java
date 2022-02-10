package it.unisa.utils;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
public class Utility {
	
	public static void print(String... messages){
		String message = "";
		for(String s : messages) {
			message += s+"\n";
		}
		
		System.out.printf("%s",message);
	}

	public static void print(Exception exception) {
		Utility.print("EXCEPTION: " + exception.getMessage());
		exception.printStackTrace();
	}
	
	   public static Date formatStringToDate(String data) {
	         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	         try {
	             return sdf.parse(data);
	         } catch(ParseException e) {}
	         return null;
	    }
	   
	   public static java.sql.Date toSqlDate(Date data) {
	        Calendar calendar = new GregorianCalendar();
	        calendar.setTime(data);
	        calendar.set(Calendar.HOUR, 1); 
	        calendar.set(Calendar.MINUTE, 0); 
	        calendar.set(Calendar.SECOND, 0); 
	        calendar.set(Calendar.MILLISECOND, 0); 

	 

	        return new java.sql.Date(calendar.getTimeInMillis());
	    }
	
}

