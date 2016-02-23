package ledworks4;

/*
 * fileIOHandler.java
 *
 * Created on December 22, 2007, 8:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



/**
 *
 * @author William Brinkert
 */
import java.io.*;

public class fileIOHandler {
    String fName = "untitled.txt";
    String eor = "\n";      //End Of Record marker
    String del = "\u0009";  //field delimiter
    /**
     * Creates a new instance of fileIOHandler
     */
    public fileIOHandler() {
    }

    public fileIOHandler(String recordMarker, String fieldDelimiter) {
        eor = recordMarker;
        del = fieldDelimiter;
    }
    
    public fileIOHandler(String fileName){
       fName = fileName;
    }

    public fileIOHandler(String fileName, String recordMarker, String fieldDelimiter) {
        fName = fileName;
        eor = recordMarker;
        del = fieldDelimiter;
    }
    
     public void saveFile(String thisText) {                                              
       
       try
       {
           File outputFile = new File(fName);
           FileWriter out = new FileWriter(outputFile);

           for (int x = 0; x < thisText.length(); x++)
              {
                  out.write(thisText.charAt(x));
              }
              out.close();
        }
        catch (IOException ex)
        {
              eLogFrame.bugout.append("Error opening file" + "\n");
        }

        eLogFrame.bugout.append("File was written successfully to " + fName + "\n");
     	
    }   
    public void saveFile(String outFileName, String thisText) {                                              
       
       try
       {
           File outputFile = new File(outFileName);
           FileWriter out = new FileWriter(outputFile);

           for (int x = 0; x < thisText.length(); x++)
              {
                  out.write(thisText.charAt(x));
              }
              out.close();
        }
        catch (IOException ex)
        {
              eLogFrame.bugout.append("Error opening file" + "\n");
        }

        eLogFrame.bugout.append("File was written successfully to " + outFileName + "\n");
     	
    }                                             
                        
     public String loadFile(String inputFileName) {                                            
        String strText = "";
        try{
            File inputFile = new File(inputFileName);
            if (!inputFile.exists())
                eLogFrame.bugout.append("Cannot find the file " + inputFileName + "\n");
	    FileReader file = new FileReader(inputFile);
            BufferedReader in = new BufferedReader(file);
	    int i;
            
            while ((i = in.read()) != -1){
	        strText = strText + ((char)i);
	    }
			
	    in.close();
            eLogFrame.bugout.append("File successfully opened." + "\n");
        }
	catch (IOException ex){
            eLogFrame.bugout.append("Error opening file" + "\n");
        }
        return strText;
    }
     public String loadFile(File inputFile) {                                            
        String strText = "";
        try{
           
            if (!inputFile.exists())
                eLogFrame.bugout.append("Cannot find the file " + inputFile.getName() + "\n");
	    FileReader file = new FileReader(inputFile);
            BufferedReader in = new BufferedReader(file);
	    int i;
            
            while ((i = in.read()) != -1){
	        strText = strText + ((char)i);
	    }
			
	    in.close();
            eLogFrame.bugout.append("File successfully opened." + "\n");
        }
	catch (IOException ex){
            eLogFrame.bugout.append("Error opening file" + "\n");
        }
        return strText;
    }
     public String loadFile() {
        String strText = "";
        try{
            File inputFile = new File(fName);
            if (!inputFile.exists())
                eLogFrame.bugout.append("Cannot find the file " + fName + "\n");
	    FileReader file = new FileReader(inputFile);
            BufferedReader in = new BufferedReader(file);
	    int i;

            while ((i = in.read()) != -1){
	        strText = strText + ((char)i);
	    }

	    in.close();
            eLogFrame.bugout.append("File successfully opened." + "\n");
        }
	catch (IOException ex){
            eLogFrame.bugout.append("Error opening file" + "\n");
        }
        return strText;
    }
    public String[][] loadRecordsFromFile(){
        String[] dataLines = loadFile().split(eor);
        String[][] dataRecord = new String[dataLines.length][];
        for (int i = 0; i < dataLines.length; i++){
            dataRecord[i] = dataLines[i].split(del);
        }
        return dataRecord;

    }
    /**
     * Reads records from text file of name fileName.  Records are read
     * separated by "\n" and fields
     * within each record are delimited by "\u0009"
     * @param fileName
     * @return
     */
    public String[][] loadRecordsFromFile(String fileName){
        String[] dataLines = loadFile(fileName).split(eor);
        String[][] dataRecord = new String[dataLines.length][];
        for (int i = 0; i < dataLines.length; i++){
            dataRecord[i] = dataLines[i].split(del);
        }
        return dataRecord;

    }
    public String[][] loadRecordsFromFile(String fileName, String eorm, String delim){

        String[] dataLines = loadFile(fileName).split(eorm);
        String[][] dataRecord = new String[dataLines.length][];
        for (int i = 0; i < dataLines.length; i++){
            dataRecord[i] = dataLines[i].split(delim);
        }
        return dataRecord;

    }
    public String [] loadStringArrayFromFile(){
        String[] array = loadFile().split(eor);
        return array;
    }
    public String [] loadStringArrayFromFile(String fileName){
        String[] array = loadFile(fileName).split(eor);
        return array;
    }
    public String [] loadStringArrayFromFile(String fileName, String marker){
        String[] array = loadFile(fileName).split(marker);
        return array;
    }

    public void logLoadedRecords(String[][] dRecords){
         eLogFrame.bugout.append("log of records: \n");
        for (int q = 0; q < dRecords.length; q++){
            for (int w = 0; w < dRecords[q].length; w++){
                eLogFrame.bugout.append(dRecords[q][w] + "\u0009" );
            }
            eLogFrame.bugout.append("\n");
        }
     }
                                               
    
}
