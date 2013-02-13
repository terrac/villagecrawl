package gwt.server.build;

public class SUtil {
	public static String readFileAsString(String filePath)
	{
	    StringBuffer fileData = null;
		try {
			fileData = new StringBuffer(1000);
			java.io.BufferedReader reader = new java.io.BufferedReader(
			        new java.io.FileReader(filePath));
			char[] buf = new char[1024];
			int numRead=0;
			while((numRead=reader.read(buf)) != -1){
			    String readData = String.valueOf(buf, 0, numRead);
			    fileData.append(readData);
			    buf = new char[1024];
			}
			reader.close();
		} catch (Exception e) {
			
			throw new RuntimeException(new java.io.File(filePath).getAbsolutePath(),e);
		}
	    return fileData.toString();
	}
}
