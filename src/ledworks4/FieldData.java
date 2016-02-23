/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ledworks4;

/**
 *
 * @author William Brinkert
 */
public class FieldData {
    String name;
    boolean isEnabled;
    String tag1;
    String tag2;

    //     Class Constructor
    public FieldData(String n, boolean b, String t){
        name = n;
        isEnabled = b;
        tag1 = t;
    }
    public FieldData(String record){
        String[] temp = record.split("\u0009");
        //logArray(temp);
        name = temp[0];
        tag1 = temp[2];
        if (temp.length > 3){ tag2 = temp[3];}
        if (temp[1].trim().equalsIgnoreCase("true")){
            isEnabled = true;
        }else isEnabled = false;
    }
    public void logFieldData(FieldData fd){
        eLogFrame.bugout.append("name:"+ fd.name +"\n");
        eLogFrame.bugout.append("isEnabled:"+ fd.isEnabled +"\n");
        eLogFrame.bugout.append("tag1:"+ fd.tag1 +"\n");
        if (!tag2.isEmpty()){eLogFrame.bugout.append("tag2:"+ fd.tag2 +"\n");}
    }
    public void logMultiFieldData(FieldData[] afd){
        for (int i = 0; i < afd.length; i++){
            eLogFrame.bugout.append("Field Record #"+ i +"\n");
            logFieldData(afd[i]);
        }
    }
    public void logArray(String[] array){
        for (int i = 0; i < array.length; i++){
            //System.out.println("array["+i+"] is "+ array[i]);
        }
    }
}
