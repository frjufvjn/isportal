import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchDir {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String HEADER = "LENGTH\tPATH\tNAME\tRESULT1\tRESULT2\tVALID";
		LogWrite.putLog(HEADER);
		subDirList("C:\\RECORD");
	}
	
	public static void subDirList(String source)
	{
		File dir = new File(source); 
		File[] fileList = dir.listFiles(); 
		
		try{
			for(int i = 0 ; i < fileList.length ; i++) {
				File file = fileList[i]; 
				if(file.isHidden()) continue;
				if(file.isFile())
				{
					float lengthMinus = file.length() - 7;
					float result = lengthMinus / 16;
					
					LogWrite.putLog(
							file.length() + "\t" + 
							source + "\t" +
							file.getName() + "\t" + 
							lengthMinus + "\t" + 
							result + "\t" + 
							isNumChk(result+"")
						);
					
				} else if(file.isDirectory()) {
					
					subDirList(file.getCanonicalPath().toString()); 
				}
			}
			
		} catch(IOException e) {

		}
	}
	
	private static boolean isNumChk(String arg) {
		String val = arg.substring(arg.indexOf("."));
		return ".0".equals(val) ? true : false;

//		try {
//			Integer.parseInt(arg);
//			return true;
//		} catch (NumberFormatException e) {
//			return false;
//		}
	}
	
}


class LogWrite {
	
    private static PrintWriter pw = null;
    private static String logDir = "";
    
    private static String getLogFileName()
    {
        String strLogFile = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        
        logDir = "C:\\temp";
        
        strLogFile = logDir + "\\" + sdf.format(new Date()) + ".log";
        return strLogFile;
    }
    
    public static void putLog(String logStr)
    {
        String strLogFile = getLogFileName();
        
        if( strLogFile != null )
        {
            try
            {
                pw = new PrintWriter(new FileWriter(strLogFile, true), true);
                //pw.print(strLogTime);
                pw.print(" ");
                pw.println(logStr);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if( pw != null )
                {
                    pw.close();
                    pw = null;
                }
            }
        }

        System.out.println(logStr);
    }    

}
