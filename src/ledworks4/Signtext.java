package ledworks4;

/*
 * Signtext.java
 *
 * Created on November 14, 2005, 8:46 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

/**
 * Class for holding creating and holding various text for output to Sunnywell
 * LED signs.
 * @author William Brinkert
 */
import java.io.*;

public class Signtext {
    static String[] signSymbol = {":","¯A","¡1","¡2","¡3","¡4","¡5","¡6","¡7","¡8","¡9","","\u0003",""};
    static String[] textSymbol = {" ","@SOF","@PA1","@PA2","@PA3","@PA4","@PA5","@PA6","@PA7","@PA8","@PA9","@EOF","",""};
    static String[] signCode ={"¯A","¡1","¡2","¡3","¡4","¡5","¡6","¡7","¡8","¡9",""};
    static String[] tagCode = {"@SOF","@PA1","@PA2","@PA3","@PA4","@PA5","@PA6","@PA7","@PA8","@PA9","@EOF"};
    String[] tagName = {"Gr1","Gr2","Gr3","Gr4","PrF","PrL","GeF",
        "GeL","OgF","OgL","B1F","B1L","B2F","B2L",
        "B3F","B3L","HdF","HdL","CcF","CcL","EmF",
        "EmL","EwF","EwL"};
    String sourceText;
    String rightHeader;
    String leftHeader;
    String withNames;
    String leftSign;
    String rightSign;
    String leftPRG;
    String rightPRG;
    String oldShowPRG;
    String[] nameArray;
    static String resourceFilePath = LEDWorks4View.resourceFilePath;
        
    /**
     * Creates an instance of Signtext formatted for the 5G show
     * @param names An array of names to be inserted into the text
     */
    public Signtext(String[] names) {
        nameArray = names;
        int[] nums = getBirthdayandGroupCountFrom(names);
        String[] bdaytxt = loadBdayTxt();
        sourceText = importText();
        importHeaderText();
        insertGroups(nums[0]);
        if (nums[1] > 1){
            insertBdays(bdaytxt, nums[1]);
        }
        altBuild(names);
        leftSign = leftHeader.concat(withNames);
        rightSign = rightHeader.concat(withNames);
        processForPRG();
    }
    /**
     * Creates a new instance of Signtext which can be formatted for either
     * the old or new show sign versions.
     * @param names An array of names to be inserted into the text
     * @param Old If true, constructs the text for the old version of the signs
     */
    public Signtext(String[] names, boolean Old) {
        nameArray = names;
        int[] nums = getBirthdayandGroupCountFrom(names);
        String[] bdaytxt = loadBdayTxt();
        if (Old){
            sourceText = importOldSourceText();
        }else {
            sourceText = importText();
        }
        importHeaderText();
        insertGroups(nums[0]);
        if (nums[1] > 1){
            insertBdays(bdaytxt, nums[1]);
        }
        altBuild(names);
        leftSign = leftHeader.concat(withNames);
        rightSign = rightHeader.concat(withNames);
        processForPRG();
    }
    /** Returns a string that has been loaded from SOURCE.TXT file in
     *  dist/Resources folder of LEDWorks directory
     */
    private String importText(){
        String temp = "";
        fileIOHandler fios = new fileIOHandler(resourceFilePath+"SOURCE.TXT");
        temp = fios.loadFile();
        eLogFrame.bugout.append("source.txt loaded...\n");
        return temp;
    }
    /**
     * Returns a string that has been loaded from "oldSOURCE.TXT" file in
     * dist/Resources folder of the program's directory.
     * @return
     */
    private String importOldSourceText(){
        String temp = "";
        fileIOHandler fios = new fileIOHandler(resourceFilePath+"oldSOURCE.TXT");
        temp = fios.loadFile();
        eLogFrame.bugout.append("oldsource.txt loaded...\n");
        return temp;
    }
    /**
     * Works on this object's header Strings which are loaded from HEADER.TXT
     * The string in HEADER.TXT is split by '%'.  Left header is index=0
     * right header is index=1
     */
    private void importHeaderText(){
        fileIOHandler fios = new fileIOHandler(resourceFilePath+"HEADER.TXT");
        String rawText = fios.loadFile();
        String[] splitText = rawText.split("%");
        eLogFrame.bugout.append("header.txt loaded...\n");
        rightHeader = splitText[1];
        leftHeader = splitText[0];
    }
    /** Returns a string array where each index has been
     *  split by a ':' in BIRTHVAR.TXT file located in
     *  the local directory
     */
    private String[] loadBdayTxt(){
        String holder = "";
        eLogFrame.bugout.append("loadBdayTxt() called...\n");
         try
        {
                String fileName = resourceFilePath+"BIRTHVAR.TXT";
                File inputFile = new File(fileName);
                if (!inputFile.exists()){
                   holder = "Cannot find the file " + fileName + "!!";
                   eLogFrame.bugout.append("File not found:"+fileName+"\n");
                }
                FileReader file = new FileReader(inputFile);
                BufferedReader in = new BufferedReader(file);
                int i;

                while ((i = in.read()) != -1)
                {
                        holder = holder + ((char)i);
                }

                in.close();

        }
        catch (IOException ex)
        {
                holder = "Error opening file:!:!";
                eLogFrame.bugout.append("Error opening File @ loadBdayTxt() \n");
        }
        return holder.split(":");
    }
    /** Returns a string that has been loaded from ENGAGEMENT.TXT
     *  file in the local directory
     */
   private String loadEngagement(){
        String temp = "";
        eLogFrame.bugout.append("loadEngagement() called...\n");
        try
        {
                String fileName = resourceFilePath.concat("ENGAGEMENT.TXT");
                File inputFile = new File(fileName);
                if (!inputFile.exists())
                    eLogFrame.bugout.append("Cannot find the file " + fileName);
                FileReader file = new FileReader(inputFile);
                BufferedReader in = new BufferedReader(file);
                int i;
                

                while ((i = in.read()) != -1)
                {
                        temp = temp + ((char)i);
                }

                in.close();

        }
        catch (IOException ex)
        {
                eLogFrame.bugout.append("Error opening file");
        }
         return temp;
    }
   /** Replaces the normal end of sourceText with text from
    *  the loadEngagement method
    */
    public void engagement(){
        sourceText = sourceText.replace("OK IT IS TIME TO BEGIN     EVERYONE START YELLING NOW      READY GO    @PA2            @PA9                .@PA1.@PA1.@PA1.@PA1.@PA1.@PA1",
                                    loadEngagement());
        altBuild(nameArray);
    }
    /** Searches the text for Group tags "@Gr1" and inserts them
     *  into sourceText.  If sourceText does not have at least @Gr1, then
     *  an invalid source file error will be generated
     */
    private void insertGroups(int nG){
        eLogFrame.bugout.append("insertGroups(int nG) called...\n");
        boolean tagfound = false;
        if (nG == 1 ) return;
        if (nG == 0){
            sourceText = sourceText.replace("@Gr1", "");
            sourceText = sourceText.replace("WE WOULD LIKE TO WELCOME THE GROUP FROM","");
            return;
        } 
            
        for (int index = 0; index < sourceText.length()-4 ; index++){
            if (sourceText.substring(index, index + 4).equals("@Gr1") ){
                index += 4;
                tagfound = true;
                String txtB4 = sourceText.substring(0, index);
                String txtAf = sourceText.substring(index + 1, sourceText.length()-1);
                for (int add = 2; add <= nG; add++){
                    if (add == nG) txtB4 = txtB4 + " AND @Gr" + add;
                    else txtB4 = txtB4 + ", @Gr" + add;
                }
                sourceText = txtB4 + txtAf;
            }                        
        }
        //if (!tagfound) sourceText = "@Gr1 tag not found.  Invalid source file.";
        sourceText = sourceText.replace("WE WOULD LIKE TO WELCOME THE GROUP FROM","WE WOULD LIKE TO WELCOME THE GROUPS FROM");
    
    }
    /** Searches the text for Bday tags and inserts birthdays
     *  into sourceText.  If sourceText does not have at least an instance of Bday, then
     *  an invalid source file error will be generated
     */
    private void insertBdays(String[] bdaytxt, int nB){
        eLogFrame.bugout.append("insertBdays(*,#) called.  # ="+ nB +"\n");
        if (nB ==1 ) return;
        if (nB == 0){
            sourceText = "Birthday Name tag not found.  Insufficient data.";
            return;
        }
        sourceText = sourceText.replace(bdaytxt[0],  bdaytxt[nB-1]);
        
    }
    /**
     * Takes the names array and replaces each mark-up tag array with a name
     * from the String array "names".  Will cause a NullPointer Exception if
     * there are more names than tags.
     * @param names String array of names to be inserted
     * @param tag String array of tags that correspond 1-1 with names array for
     * swapping out.
     */
    public void altBuild(String[] names, String[] tag){
        withNames = sourceText;
        for (int i = 0; i < tag.length; i++){
            withNames = sourceText.replace("@" + tag[i],names[i]);
        }
    }
    /**
     * Takes the names array and replaces each mark-up tag for a name with a name
     * from the String array "names".  Will cause a NullPointer Exception if
     * there are more names than tags.
     * @param names String array of names to be inserted into text.
     */
    private void altBuild(String[] names){
        withNames = sourceText;
        eLogFrame.bugout.append("internal altBuild called...\n");
        for (int i = 0; i < tagName.length; i++){
            withNames = withNames.replace("@" + tagName[i],names[i]);
            eLogFrame.bugout.append("@" + tagName[i] + " is now " + names[i] + "\n");
        }
        eLogFrame.bugout.append(withNames + "......is the file \n");
    }
    /**
     * Uses swapCode() and prepOut() to process leftPRG, rightPRG and oldShow.PRG
     * into true sign ready text.
     */
    public void processForPRG(){
        eLogFrame.bugout.append("internal class build of PRG...\n");
        swapCode();
        leftPRG = prepOut(leftPRG);
        rightPRG = prepOut(rightPRG);
        oldShowPRG = prepOut(oldShowPRG);
        eLogFrame.bugout.append("prepOut completed \n");
    }
    /**
     * Replaces all mark-up tags found in text with their equivalent sign
     * encoded values. Works on left, right and Old sign text versions
     * simultaneously.  Is automatically called by processForPRG()
     */
    public void swapCode(){
        leftPRG = leftSign;
        for (int h = 0; h < tagCode.length; h++){
            if (h < signCode.length ){
                leftPRG = leftPRG.replaceAll(tagCode[h],signCode[h]);
                eLogFrame.bugout.append("swap " + tagCode[h]+ " for " + signCode[h]+ "\n");
            }
            else{
                leftPRG = leftPRG.replaceAll(tagCode[h],"");
                eLogFrame.bugout.append("null swap ins"+ "\n");
            }
        }
        eLogFrame.bugout.append("swapCode leftSign call complete \n");
        rightPRG = rightSign;
        for (int h = 0; h < tagCode.length; h++){
            if (h < signCode.length ){
                rightPRG = rightPRG.replaceAll(tagCode[h],signCode[h]);
                eLogFrame.bugout.append("swap " + tagCode[h]+ " for " + signCode[h]+ "\n");
            }
            else{
                rightPRG = rightPRG.replaceAll(tagCode[h],"");
                eLogFrame.bugout.append("null swap ins"+ "\n");
            }
        }
        eLogFrame.bugout.append("swapCode rightSign call complete \n");
        oldShowPRG = withNames;
        for (int h = 0; h < tagCode.length; h++){
            if (h < signCode.length ){
                oldShowPRG = oldShowPRG.replaceAll(tagCode[h],signCode[h]);
                eLogFrame.bugout.append("swap " + tagCode[h]+ " for " + signCode[h]+ "\n");
            }
            else{
                oldShowPRG = oldShowPRG.replaceAll(tagCode[h],"");
                eLogFrame.bugout.append("null swap ins"+ "\n");
            }
        }
        eLogFrame.bugout.append("swapCode OldShowSign call complete \n");
    }
    /**
     * This method works character by character to insert the appropriate byte
     * code into the text for encoding in Sunnywell's prg format.  Characters
     * to be inserted: 01h for "red" color encoding and 3Ah for a blankspace.
     * Other code: AFh and 41h  SOF =(actually PageID and PageNO)
     * A1h is wait command (followed by number 1-9)
     * @param readyTxt
     * @return
     */
    static String prepOut(String readyTxt){
            String holdTxt = "";
            readyTxt = hideColons(readyTxt);
            eLogFrame.bugout.append("\n processing text of " + readyTxt.length() + " characters \n");
            for (int i = 0; i < readyTxt.length(); i++)
            {
                    /*--------------------------------------------------------------
                     * Check for sign's own code markers and concat into temp string
                     *--------------------------------------------------------------*/
                    if ((readyTxt.charAt(i) == '¯') || (readyTxt.charAt(i) == '¡') ||
                            (readyTxt.charAt(i) == '') || (readyTxt.charAt(i) == ''))
                    {
                            eLogFrame.bugout.append(".");
                            holdTxt = holdTxt + readyTxt.charAt(i);
                            if ((readyTxt.charAt(i) == '')|| (readyTxt.charAt(i) == ''))
                                    break; // OEF marker: in this special case stop processing String
                            i++;       // move forward to tag after marker
                            holdTxt = holdTxt + readyTxt.charAt(i);	// concat tag into string
                            eLogFrame.bugout.append("-");
                            continue;
                    }
                    if ( i != readyTxt.length() ){
                            holdTxt = holdTxt + '';	// if we haven't reached the end, insert color byte
                            eLogFrame.bugout.append(",");
                    }
                    if (readyTxt.charAt(i) == ' '){
                            holdTxt = holdTxt + ':';		// replace blankspace with ':'
                            eLogFrame.bugout.append(">");
                    }
                    else{
                            holdTxt = holdTxt + readyTxt.charAt(i);  // otherwise just copy contents
                            eLogFrame.bugout.append("_");
                    }
            }
            holdTxt = restoreColons(holdTxt);
            return holdTxt;
    }
    /**
     * Returns the number of birthday and group names found in the names
     * array by checking the length of each String.  Each non-zero length
     * String is assumed to have valid content and is counted.  Names array
     * index values 0-3 are check to count the number of groups.  Index values
     * 10-15 are checked to count the number of birthdays; these indices cover
     * first and last names, so errors and unexpected behavior could arise if
     * one of the birthday names is missing a first or last name.
     * @param names An array of names to be inserted into the sign text
     * @return An int array: [0] = number of groups and [1] = number of birthday
     * names (first and last).
     */
    public int[] getBirthdayandGroupCountFrom(String[] names){
        int[] count = new int[2];
        eLogFrame.bugout.append("counting birthdays and groups...\n");
        for ( int i = 0; i < 4 ; i++){
            if (names[i].length() != 0)
                count[0]++;
        }
        for ( int j = 10 ; j < 16; j+=2){
            if (names[j].length() != 0)
                count[1]++;
        }
        eLogFrame.bugout.append(count[0] + " groups and "+ count[1] +" birthdays \n");
        return count;
    }
    /**
     * Since Sunnywell signs use the unicode character ":" to mark a blank space
     * character, we must hide any colons we want to actually appear on the
     * before processing.
     * @param s The String which contains the colons we wish to "hide" using
     * the "|" character.
     * @return A string where any instance of ":" has been replaced with "|".
     */
    private static String hideColons(String s){
        s = s.replace(":", "|");
        return s;
    }
    /**
     * Replaces any ":" that were swapped out with the unicode character that
     * Sunnywell signs use to display colons.
     * @param s
     * @return
     */
    private static String restoreColons(String s){
        s = s.replace("|", "\u0020");
        return s;
    }
}
