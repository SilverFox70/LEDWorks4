/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ledworks4;

/**
 *
 * @author Emily LeMenager
 */
public class ProgramMode {
    String resourceFilePath = LEDWorks4View.resourceFilePath;
    String mode = null;
    String[][] modes;
    String version;
    String showFilePath = LEDWorks4View.showFilePath;
    String pathType;
    Boolean console = true;
    String nativeTags;
    String nativeFields;
    String location;
    boolean userWantsCustomFields;
    String[] getNewFields;
    boolean userWantsCustomTags;
    String[] getNewTags;
    FieldData[] Field;
    
    //Constructor
    public ProgramMode(String resourceFile){
        mode = importFile(resourceFile);
        modes = parseData(mode);
        setValues(modes);
        //logRecord();
        logValues();

    }
    //Local Class Methods
    private String importFile(String fileName){
        String temp = "";
        System.out.println("rFP ="+ resourceFilePath+ fileName);
        fileIOHandler fios = new fileIOHandler(resourceFilePath+ fileName);
        temp = fios.loadFile();
        eLogFrame.bugout.append(resourceFilePath + fileName +"\n");
        return temp;
    }
    private String[][] parseData(String data){
        data = mode.replaceAll("<", "");
        eLogFrame.bugout.append(data+"\n");
        String[] lines = data.split("\n");
        String[][] eM = new String[lines.length][];
        for (int i = 0; i < eM.length; i++){
            eM[i] = lines[i].split(">");
        }
        return eM;
    }
    private void setValues(String[][] ms){
        eLogFrame.bugout.append("setValues called...\n");
        logRecord();
        for (int i = 0; i < ms.length; i++){
            if (ms[i][0].equalsIgnoreCase("show version")){version = ms[i][1].trim();}
            if (ms[i][0].equalsIgnoreCase("show file path")){/*showFilePath = ms[i][1].trim();*/}
            if (ms[i][0].equalsIgnoreCase("path type")){pathType = ms[i][1].trim();}
            if (ms[i][0].equalsIgnoreCase("console")){
                if (ms[i][1].trim().equalsIgnoreCase("ON")){
                    console = true;
                }else {console = false;}
            }
            if (ms[i][0].equalsIgnoreCase("native tags")){nativeTags = ms[i][1].trim();}
            if (ms[i][0].equalsIgnoreCase("native fields")){nativeFields = ms[i][1].trim();}
            if (ms[i][0].equalsIgnoreCase("show location")){location = ms[i][1].trim();}
        }
        userWantsCustomFields = userWantsCustom(nativeFields);
        userWantsCustomTags = userWantsCustom(nativeTags);
        if ((userWantsCustomFields)||(userWantsCustomTags)){
            Field = getFieldData(nativeFields);
        }else if(version.equalsIgnoreCase("old")){
                 Field = getFieldData("oldfieldsource.lrf");
              }else if(version.equalsIgnoreCase("new")){
                       Field = getFieldData("newfieldsource.lrf");
                    }
        
    }
    private boolean userWantsCustom(String s){
        boolean query = false;
        if (!s.equalsIgnoreCase("yes")){
            query = true;
        }
        return query;
    }
    public FieldData[] getFieldData(String filename){
        fileIOHandler fios = new fileIOHandler(resourceFilePath+filename);
        String[] record = fios.loadStringArrayFromFile();
        logArray(record);
        FieldData[] myFields = new FieldData[record.length];
        for (int i = 0; i < record.length; i++){
            myFields[i] = new FieldData(record[i]);
        }
        return myFields;
    }
    /**
     * Brute force method for updating the Show Version parameter in the
     * preference file.  It assumes that no other parameter will use the same
     * string value as is being passed in previous
     * @param previous The old value of the parameter you wish to change
     * @param newValue The new value of the parameter being changed.
     */
    public void updatePrefToFile(String previous, String newValue){
        fileIOHandler fios = new fileIOHandler(resourceFilePath + "pref.lpr");
        String temp = fios.loadFile();
        temp = temp.replaceAll(previous, newValue);
        fios.saveFile(temp);
    }
    //-------------------------------------------------------------------------
    //                        Debugging Methods
    //-------------------------------------------------------------------------
    private void logValues(){
        eLogFrame.bugout.append("logValues called...\n");
        eLogFrame.bugout.append("<show version>"+version+"\n");
        eLogFrame.bugout.append("<show file path>"+showFilePath+"\n");
        eLogFrame.bugout.append("<path type>"+pathType+"\n");
        eLogFrame.bugout.append("<console>"+console+"\n");
        eLogFrame.bugout.append("<native tags>"+nativeTags+"\n");
        eLogFrame.bugout.append("<native fields>"+nativeFields+"\n");
        eLogFrame.bugout.append("<location>"+location+"\n");
    }
    private void logRecord(){
        eLogFrame.bugout.append("logRecord called...\n");
        for (int i=0; i < modes.length; i++){
            eLogFrame.bugout.append("line #"+ i +" :");
            for (int j =0 ; j < modes[i].length; j++){
                eLogFrame.bugout.append(modes[i][j]+ " ");
            }
            eLogFrame.bugout.append("\n");
        }
    }
    private void logArray(String[] array){
        for (int i = 0; i< array.length; i++){
            eLogFrame.bugout.append("index "+ i +"="+ array[i] +"\u0009");
            //System.out.println("array["+i+"] is "+ array[i]);
        }
        eLogFrame.bugout.append("\n");
    }

}
