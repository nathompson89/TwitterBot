import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.logging.*;


public class WritingToFile {
	
	static String logSeparator = "/////////////////////////////////////////////////////";
	
	static Logger logger = Logger.getLogger("TwitterBotLog");  
    static FileHandler fh;
	
	public static void LogInfo (String sor, String msg, String UserName)
	{
		try
	    {
	    	fh = new FileHandler("TwitterBotInfo.log", true);
	    	logger.addHandler(fh);
	    	SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        
	        logger.info(sor + System.lineSeparator() + msg + System.lineSeparator() + UserName + System.lineSeparator() + logSeparator);
	    	
	    }
	    
	    catch (Exception ex)
	    {
	    	LogError(ex.toString(), exceptionStacktraceToString(ex));
	    }
	}

	public static void LogError(String msg, String printStackTrace) 
	{
		try
	    {
	    	fh = new FileHandler("TwitterBotError.log", true);
	    	logger.addHandler(fh);
	    	SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        
	        logger.severe(msg + System.lineSeparator() + printStackTrace + System.lineSeparator() + logSeparator);
	    	
	    }
	    
	    catch (Exception ex)
	    {
	    	LogError(ex.toString(), exceptionStacktraceToString(ex));
	    }
		
	}
	
	public static String exceptionStacktraceToString(Exception e)
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    e.printStackTrace(ps);
	    ps.close();
	    return baos.toString();
	}
	
	public static void CSVFile (String fileName, String msg, String userName, String sor)
	{
		//Get timestamp to put into file
		String timeStamp = new SimpleDateFormat("M/dd/yyyy hh:mm:ss a").format(new java.util.Date());
		
		try
		{
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(fileName), true));
			StringBuilder sb = new StringBuilder();
			// Remove commas from the message so it doesn't mess with the CSV file.
			// Original located in TwitterBotInfo.log
			msg = msg.replace(",", "");
			msg = msg.replace("\n", "").replace("\r", "");
			
			sb.append(timeStamp);
			sb.append(',');
			sb.append(sor);
			sb.append(',');
			sb.append(userName);
			sb.append(',');
			sb.append(msg);
			sb.append('\n');
						
			pw.write(sb.toString());
			pw.close();
		
		}
		catch (Exception ex)
		{
			LogError(ex.toString(), exceptionStacktraceToString(ex));
		}
		
	}

}
