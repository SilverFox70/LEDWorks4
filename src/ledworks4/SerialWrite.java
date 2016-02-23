package ledworks4;

/*
 * @(#)SimpleWrite.java	1.12 98/06/25 SMI
 * 
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */
import java.io.*;
import java.util.*;
import gnu.io.*; 

/**
 * Class declaration
 *
 *
 * @author Sun MicroSystems
 * @version 1.10, 08/04/00
 */
public class SerialWrite {
    static Enumeration	      portList;
    static CommPortIdentifier portId;
    //static String rxIDStart = "ªªªªª»";
    static String rxIDStart = "\u00AA\u00AA\u00AA\u00AA\u00AA\u00BB";
    static String rxIDStop = "¿¿±";
    static String outputString = "¯AYOUR:ATTENTION:PEOPLE:::::::::::::::YOUR:ATTENTION:PLEASE:::::::::::PLEASE:YELL:IF:YOU:ARE:PAYING:ATTENTION:¡1";
    static String messageString = rxIDStart + outputString + rxIDStop;
    static SerialPort	      serialPort;
    static OutputStream       outputStream;
    static boolean	      outputBufferEmptyFlag = false;

    public void testListofPorts(){
        eLogFrame.bugout.append("SerialWrite.testListofPorts called...\n");
        int numberofPorts = 0;
        boolean portFound = false;
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            numberofPorts++;
            eLogFrame.bugout.append("port found...\n");
            portId = (CommPortIdentifier) portList.nextElement();
            eLogFrame.bugout.append("portId="+ portId.getName()+" ");
            eLogFrame.bugout.append(" is a ");
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
                eLogFrame.bugout.append("Serial Port"+ "\n");
            }
            if (portId.getPortType() == CommPortIdentifier.PORT_I2C){
                eLogFrame.bugout.append("I2C Port"+ "\n");
            }
            if (portId.getPortType() == CommPortIdentifier.PORT_PARALLEL){
                eLogFrame.bugout.append("Parallel Port"+ "\n");
            }
            if (portId.getPortType() == CommPortIdentifier.PORT_RAW){
                eLogFrame.bugout.append("Raw Port"+ "\n");
            }
            if (portId.getPortType() == CommPortIdentifier.PORT_RS485){
                eLogFrame.bugout.append("RS485 Port"+ "\n");
            }
        }

        eLogFrame.bugout.append("Total ports found: "+ numberofPorts +"\n");
        
    }

    /**
     * Sends a given string out via the default serial port, COM 1
     *
     *
     * @param signtext The string to be sent out
     *
     * @see
     */
    public static void sendToSign(String signtext) {
        eLogFrame.bugout.append("sendToSign called...\n");
        boolean portFound = false;
        String  defaultPort = "COM1";
        messageString = rxIDStart + signtext + rxIDStop;
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            eLogFrame.bugout.append("portId="+ portId.getName()+"\n");

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

                if (portId.getName().equals(defaultPort)) {
                        eLogFrame.bugout.append("Found port " + defaultPort + "\n");

                        portFound = true;

                        try {
                        serialPort =
                            (SerialPort) portId.open("SerialWrite", 2000);
                        } catch (PortInUseException e) {
                        eLogFrame.bugout.append("Port in use.\n");

                        continue;
                        }

                        try {
                        outputStream = serialPort.getOutputStream();
                        } catch (IOException e) {}

                        try {
                        serialPort.setSerialPortParams(2400,
                                           SerialPort.DATABITS_8,
                                           SerialPort.STOPBITS_1,
                                           SerialPort.PARITY_NONE);
                        } catch (UnsupportedCommOperationException e) {}


                        try {
                            serialPort.notifyOnOutputEmpty(true);
                        } catch (Exception e) {
                        eLogFrame.bugout.append("Error setting event notification \n");
                        eLogFrame.bugout.append(e.toString());
                        //System.exit(-1);
                        }

                        eLogFrame.bugout.append(
                            "Writing \""+ messageString+"\" to "
                        +serialPort.getName()+ "\n");

                        try {
                            outputStream.write(messageString.getBytes());
                            eLogFrame.bugout.append("bytes="+ messageString.getBytes());
                        } catch (IOException e) {
                            eLogFrame.bugout.append("Exception occurred while writing out...\n");
                        }
                        eLogFrame.bugout.append("Writing complete...");

                        try {
                           Thread.sleep(2000);  // Be sure data is xferred before closing
                        } catch (Exception e) {
                            eLogFrame.bugout.append("\nException occurred while sleeping thread\n");
                        }
                        serialPort.close();
                        eLogFrame.bugout.append("Serial port closed.\n");
                        //System.exit(1);
                }
            }
        }

        if (!portFound) {
            eLogFrame.bugout.append("port " + defaultPort + " not found.\n");
        }
    }
    /**
     * A simple method to test output via serial port at COM1
     */
    public static void testSendToSign() {
        boolean portFound = false;
        String  defaultPort = "COM1";;
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

            if (portId.getName().equals(defaultPort)) {
                    eLogFrame.bugout.append("Found port " + defaultPort + "\n");

                    portFound = true;

                    try {
                    serialPort =
                        (SerialPort) portId.open("SerialWrite", 2000);
                    } catch (PortInUseException e) {
                    eLogFrame.bugout.append("Port in use.\n");

                    continue;
                    }

                    try {
                    outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {}

                    try {
                    serialPort.setSerialPortParams(2400,
                                       SerialPort.DATABITS_8,
                                       SerialPort.STOPBITS_1,
                                       SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {}


                    try {
                        serialPort.notifyOnOutputEmpty(true);
                    } catch (Exception e) {
                    eLogFrame.bugout.append("Error setting event notification \n");
                    eLogFrame.bugout.append(e.toString());
                    //System.exit(-1);
                    }


                    eLogFrame.bugout.append(
                        "Writing \""+ messageString+"\" to "
                    +serialPort.getName()+ "\n");

                    try {
                    outputStream.write(messageString.getBytes());
                    } catch (IOException e) {}

                    try {
                       Thread.sleep(2000);  // Be sure data is xferred before closing
                    } catch (Exception e) {}
                    serialPort.close();
                    //System.exit(1);
            }
            }
        }

	if (!portFound) {
	    eLogFrame.bugout.append("port " + defaultPort + " not found.\n");
	}
    }


}




