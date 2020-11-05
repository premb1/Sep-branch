package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ProReader {

	
	
	public static String getLocator(String key)
	{
		String loc="";
		File pfile= new File("E:\\Sep-batch\\SeleniumMaven\\src\\test\\resources\\object.properties");
		try {
			FileInputStream fis = new FileInputStream(pfile);
			Properties pro = new Properties();
			
			pro.load(fis);
			
		loc=	pro.getProperty(key);
		
		
		
		return loc;
		
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loc;
	}
}
