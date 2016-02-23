/*
 * LEDWorks4View.java
 */

package ledworks4;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * The application's main frame.
 */
public class LEDWorks4View extends FrameView {
    String rawStr = "";
    Signtext mySign;
    //String clnText = "";
    static String[] signSymbol = {"\u0020",":","¯A","¡1","¡2","¡3","¡4","¡5","¡6","¡7","¡8","¡9","","\u0003","","#"};
    static String[] textSymbol = {"#"," ","@SOF","@PA1","@PA2","@PA3","@PA4","@PA5","@PA6","@PA7","@PA8","@PA9","@EOF","","",":"};
    static String[] signCode ={"¯A","¡1","¡2","¡3","¡4","¡5","¡6","¡7","¡8","¡9",""};
    static String[] tagCode = {"@SOF","@PA1","@PA2","@PA3","@PA4","@PA5","@PA6","@PA7","@PA8","@PA9","@EOF"};
    static String currentDirectory = System.getProperty("user.dir")+checkForDistFolder();
    static String showFilePath = currentDirectory.concat("Show files\\");
    static String resourceFilePath = currentDirectory.concat("Resources\\");
    eLogFrame log = new eLogFrame();
    ProgramMode pm = new ProgramMode("pref.lpr");

    public LEDWorks4View(SingleFrameApplication app) {
        super(app);
        log.pack();
        log.setVisible(true);
        log.bugout.append("curDir set to:"+ currentDirectory +"\n \n");
        log.bugout.append("showPath set to:"+ showFilePath +"\n \n");
        log.bugout.append("resourcePath set to:"+ resourceFilePath +"\n \n");
        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = LEDWorks4App.getApplication().getMainFrame();
            aboutBox = new LEDWorks4AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        LEDWorks4App.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        foutputtxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        loadNamesButton = new javax.swing.JButton();
        markedTextOut = new javax.swing.JButton();
        markedTextOut.setEnabled(false);
        createTextFile = new javax.swing.JButton();
        createTextFile.setEnabled(false);
        plainTextOut = new javax.swing.JButton();
        plainTextOut.setEnabled(false);
        createPRGButton = new javax.swing.JButton();
        decodeText = new javax.swing.JButton();
        decodeText.setEnabled(false);
        advancedButton = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        engagementButton = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel16.setVisible(false);
        jLabel17 = new javax.swing.JLabel();
        jLabel17.setVisible(false);
        emfEdit = new javax.swing.JTextField();
        emfEdit.setVisible(false);
        emlEdit = new javax.swing.JTextField();
        emlEdit.setVisible(false);
        ewfEdit = new javax.swing.JTextField();
        ewfEdit.setVisible(false);
        ewlEdit = new javax.swing.JTextField();
        ewlEdit.setVisible(false);
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        rightSignEdit = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        leftSignEdit = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        sendButtonLeft = new javax.swing.JButton();
        sendButtonRight = new javax.swing.JButton();
        clearTextButton = new javax.swing.JButton();
        clearTextButton1 = new javax.swing.JButton();
        importNamesButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        showTimeNite = new javax.swing.JRadioButton();
        shownumButton2 = new javax.swing.JRadioButton();
        showTimeNoon = new javax.swing.JRadioButton();
        showTimeMorn = new javax.swing.JRadioButton();
        shownumButton1 = new javax.swing.JRadioButton();
        shownumButton3 = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        b3lEdit = new javax.swing.JTextField();
        hglEdit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        b1fEdit = new javax.swing.JTextField();
        b2lEdit = new javax.swing.JTextField();
        gr2Edit = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        oglEdit = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pplEdit = new javax.swing.JTextField();
        ogfEdit = new javax.swing.JTextField();
        b3fEdit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ccfEdit = new javax.swing.JTextField();
        gr1Edit = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cclEdit = new javax.swing.JTextField();
        b1lEdit = new javax.swing.JTextField();
        hdfEdit = new javax.swing.JTextField();
        gr3Edit = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        hgfEdit = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        hdlEdit = new javax.swing.JTextField();
        b2fEdit = new javax.swing.JTextField();
        ppfEdit = new javax.swing.JTextField();
        gr4Edit = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        bugout = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        openPRGItem = new javax.swing.JMenuItem();
        newPRGItem = new javax.swing.JMenuItem();
        savePRGItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        toolMenu = new javax.swing.JMenu();
        importTabbedNames = new javax.swing.JMenuItem();
        oldShowModeMenuItem = new javax.swing.JCheckBoxMenuItem();
        consoleMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        showVersionPrefMenu = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        portsMenuItem = new javax.swing.JMenu();
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jLabel20 = new javax.swing.JLabel();

        mainPanel.setMinimumSize(new java.awt.Dimension(750, 700));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(900, 700));

        foutputtxt.setName("foutputtxt"); // NOI18N
        foutputtxt.setText("show1.prg");

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ledworks4.LEDWorks4App.class).getContext().getResourceMap(LEDWorks4View.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(272, 97));

        loadNamesButton.setText(resourceMap.getString("loadNamesButton.text")); // NOI18N
        loadNamesButton.setToolTipText(resourceMap.getString("loadNamesButton.toolTipText")); // NOI18N
        loadNamesButton.setName("loadNamesButton"); // NOI18N
        loadNamesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadNamesButtonMouseClicked(evt);
            }
        });

        markedTextOut.setText(resourceMap.getString("markedTextOut.text")); // NOI18N
        markedTextOut.setName("markedTextOut"); // NOI18N
        markedTextOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                markedTextOutMouseClicked(evt);
            }
        });
        markedTextOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markedTextOutActionPerformed(evt);
            }
        });

        createTextFile.setText(resourceMap.getString("createTextFile.text")); // NOI18N
        createTextFile.setToolTipText(resourceMap.getString("createTextFile.toolTipText")); // NOI18N
        createTextFile.setName("createTextFile"); // NOI18N
        createTextFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createTextFileMouseClicked(evt);
            }
        });

        plainTextOut.setText(resourceMap.getString("plainTextOut.text")); // NOI18N
        plainTextOut.setName("plainTextOut"); // NOI18N
        plainTextOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                plainTextOutMouseClicked(evt);
            }
        });

        createPRGButton.setText(resourceMap.getString("createPRGButton.text")); // NOI18N
        createPRGButton.setMaximumSize(new java.awt.Dimension(129, 23));
        createPRGButton.setMinimumSize(new java.awt.Dimension(129, 23));
        createPRGButton.setName("createPRGButton"); // NOI18N
        createPRGButton.setPreferredSize(new java.awt.Dimension(129, 23));
        createPRGButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createPRGButtonMouseClicked(evt);
            }
        });

        decodeText.setText(resourceMap.getString("decodeText.text")); // NOI18N
        decodeText.setToolTipText(resourceMap.getString("decodeText.toolTipText")); // NOI18N
        decodeText.setName("decodeText"); // NOI18N
        decodeText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                decodeTextMouseClicked(evt);
            }
        });

        advancedButton.setText(resourceMap.getString("advancedButton.text")); // NOI18N
        advancedButton.setName("advancedButton"); // NOI18N
        advancedButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                advancedButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(advancedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(decodeText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(createTextFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loadNamesButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(createPRGButton, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(markedTextOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(plainTextOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(211, 211, 211))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadNamesButton)
                    .addComponent(createPRGButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(decodeText)
                    .addComponent(markedTextOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createTextFile)
                    .addComponent(plainTextOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(advancedButton)
                .addContainerGap())
        );

        jPanel2.setName("jPanel2"); // NOI18N

        engagementButton.setText(resourceMap.getString("engagementButton.text")); // NOI18N
        engagementButton.setName("engagementButton"); // NOI18N
        engagementButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                engagementButtonMouseClicked(evt);
            }
        });

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        emfEdit.setName("emfEdit"); // NOI18N
        emfEdit.setNextFocusableComponent(emlEdit);

        emlEdit.setName("emlEdit"); // NOI18N
        emlEdit.setNextFocusableComponent(ewfEdit);

        ewfEdit.setName("ewfEdit"); // NOI18N
        ewfEdit.setNextFocusableComponent(ewlEdit);

        ewlEdit.setName("ewlEdit"); // NOI18N
        ewlEdit.setNextFocusableComponent(loadNamesButton);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(engagementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(emfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(emlEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ewfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(ewlEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(engagementButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(emfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(emlEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ewfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17))
                    .addComponent(ewlEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        rightSignEdit.setFont(resourceMap.getFont("rightSignEdit.font")); // NOI18N
        rightSignEdit.setLineWrap(true);
        rightSignEdit.setWrapStyleWord(true);
        rightSignEdit.setDragEnabled(true);
        rightSignEdit.setName("rightSignEdit"); // NOI18N
        jScrollPane2.setViewportView(rightSignEdit);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        leftSignEdit.setFont(resourceMap.getFont("leftSignEdit.font")); // NOI18N
        leftSignEdit.setLineWrap(true);
        leftSignEdit.setWrapStyleWord(true);
        leftSignEdit.setDragEnabled(true);
        leftSignEdit.setName("leftSignEdit"); // NOI18N
        jScrollPane1.setViewportView(leftSignEdit);

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        sendButtonLeft.setText(resourceMap.getString("sendButtonLeft.text")); // NOI18N
        sendButtonLeft.setName("sendButtonLeft"); // NOI18N
        sendButtonLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendButtonLeftMouseClicked(evt);
            }
        });

        sendButtonRight.setText(resourceMap.getString("sendButtonRight.text")); // NOI18N
        sendButtonRight.setName("sendButtonRight"); // NOI18N
        sendButtonRight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendButtonRightMouseClicked(evt);
            }
        });

        clearTextButton.setText(resourceMap.getString("clearTextButton.text")); // NOI18N
        clearTextButton.setName("clearTextButton"); // NOI18N
        clearTextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearTextButtonMouseClicked(evt);
            }
        });

        clearTextButton1.setText(resourceMap.getString("clearTextButton1.text")); // NOI18N
        clearTextButton1.setName("clearTextButton1"); // NOI18N
        clearTextButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearTextButton1MouseClicked(evt);
            }
        });

        importNamesButton.setText(resourceMap.getString("importNamesButton.text")); // NOI18N
        importNamesButton.setToolTipText(resourceMap.getString("importNamesButton.toolTipText")); // NOI18N
        importNamesButton.setName("importNamesButton"); // NOI18N
        importNamesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importNamesButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearTextButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                        .addComponent(sendButtonLeft))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearTextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(importNamesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(sendButtonRight))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(sendButtonRight)
                    .addComponent(clearTextButton)
                    .addComponent(importNamesButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(sendButtonLeft)
                    .addComponent(clearTextButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );

        jPanel4.setName("jPanel4"); // NOI18N

        showTimeNite.setSelected(true);
        showTimeNite.setText(resourceMap.getString("showTimeNite.text")); // NOI18N
        showTimeNite.setName("showTimeNite"); // NOI18N
        showTimeNite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showTimeNiteMouseClicked(evt);
            }
        });

        shownumButton2.setText(resourceMap.getString("shownumButton2.text")); // NOI18N
        shownumButton2.setName("shownumButton2"); // NOI18N
        shownumButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shownumButton2MouseClicked(evt);
            }
        });

        showTimeNoon.setText(resourceMap.getString("showTimeNoon.text")); // NOI18N
        showTimeNoon.setName("showTimeNoon"); // NOI18N
        showTimeNoon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showTimeNoonMouseClicked(evt);
            }
        });

        showTimeMorn.setText(resourceMap.getString("showTimeMorn.text")); // NOI18N
        showTimeMorn.setName("showTimeMorn"); // NOI18N
        showTimeMorn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showTimeMornMouseClicked(evt);
            }
        });

        shownumButton1.setSelected(true);
        shownumButton1.setText(resourceMap.getString("shownumButton1.text")); // NOI18N
        shownumButton1.setName("shownumButton1"); // NOI18N
        shownumButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shownumButton1MouseClicked(evt);
            }
        });

        shownumButton3.setText(resourceMap.getString("shownumButton3.text")); // NOI18N
        shownumButton3.setName("shownumButton3"); // NOI18N
        shownumButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shownumButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showTimeMorn, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shownumButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shownumButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showTimeNoon, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showTimeNite, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shownumButton3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showTimeNite)
                    .addComponent(showTimeMorn)
                    .addComponent(showTimeNoon))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shownumButton1)
                    .addComponent(shownumButton3)
                    .addComponent(shownumButton2))
                .addContainerGap())
        );

        jPanel5.setName("jPanel5"); // NOI18N

        b3lEdit.setName("b3lEdit"); // NOI18N
        b3lEdit.setNextFocusableComponent(hdfEdit);

        hglEdit.setName("hglEdit"); // NOI18N
        hglEdit.setNextFocusableComponent(ogfEdit);

        jLabel5.setText(pm.Field[3].name);
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(pm.Field[4].name);
        jLabel6.setName("jLabel6"); // NOI18N

        b1fEdit.setName("b1fEdit"); // NOI18N
        b1fEdit.setNextFocusableComponent(b1lEdit);

        b2lEdit.setName("b2lEdit"); // NOI18N
        b2lEdit.setNextFocusableComponent(b3fEdit);

        gr2Edit.setName("gr2Edit"); // NOI18N
        gr2Edit.setNextFocusableComponent(gr3Edit);

        jLabel2.setText(pm.Field[0].name);
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel8.setText(pm.Field[6].name);
        jLabel8.setName("jLabel8"); // NOI18N

        oglEdit.setName("oglEdit"); // NOI18N
        oglEdit.setNextFocusableComponent(b1fEdit);

        jLabel9.setText(pm.Field[7].name);
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel3.setText(pm.Field[1].name);
        jLabel3.setName("jLabel3"); // NOI18N

        pplEdit.setName("pplEdit"); // NOI18N
        pplEdit.setNextFocusableComponent(hgfEdit);

        ogfEdit.setName("ogfEdit"); // NOI18N
        ogfEdit.setNextFocusableComponent(oglEdit);

        b3fEdit.setName("b3fEdit"); // NOI18N
        b3fEdit.setNextFocusableComponent(b3lEdit);

        jLabel7.setText(pm.Field[5].name);
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel10.setText(pm.Field[8].name);
        jLabel10.setName("jLabel10"); // NOI18N

        ccfEdit.setName("ccfEdit"); // NOI18N

        gr1Edit.setName("gr1Edit"); // NOI18N
        gr1Edit.setNextFocusableComponent(gr2Edit);

        jLabel13.setText(pm.Field[11].name);
        jLabel13.setName("jLabel13"); // NOI18N

        cclEdit.setName("cclEdit"); // NOI18N

        b1lEdit.setName("b1lEdit"); // NOI18N
        b1lEdit.setNextFocusableComponent(b2fEdit);

        hdfEdit.setName("hdfEdit"); // NOI18N
        hdfEdit.setNextFocusableComponent(hdlEdit);

        gr3Edit.setName("gr3Edit"); // NOI18N
        gr3Edit.setNextFocusableComponent(gr4Edit);

        jLabel4.setText(pm.Field[2].name);
        jLabel4.setName("jLabel4"); // NOI18N

        hgfEdit.setName("hgfEdit"); // NOI18N
        hgfEdit.setNextFocusableComponent(hglEdit);

        jLabel11.setText(pm.Field[9].name);
        jLabel11.setName("jLabel11"); // NOI18N

        hdlEdit.setName("hdlEdit"); // NOI18N
        hdlEdit.setNextFocusableComponent(loadNamesButton);

        b2fEdit.setName("b2fEdit"); // NOI18N
        b2fEdit.setNextFocusableComponent(b2lEdit);

        ppfEdit.setName("ppfEdit"); // NOI18N
        ppfEdit.setNextFocusableComponent(pplEdit);

        gr4Edit.setName("gr4Edit"); // NOI18N
        gr4Edit.setNextFocusableComponent(ppfEdit);

        jLabel12.setText(pm.Field[10].name);
        jLabel12.setName("jLabel12"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)
                        .addComponent(jLabel12)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gr1Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gr2Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gr3Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gr4Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(ppfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(pplEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(hgfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(hglEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(ogfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(oglEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(b1fEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(b1lEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(b2fEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(b2lEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(b3fEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(b3lEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(hdfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(hdlEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(ccfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(cclEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gr1Edit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gr2Edit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gr3Edit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gr4Edit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ppfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(pplEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hgfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(hglEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ogfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(oglEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(b1fEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addComponent(b1lEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(b2fEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addComponent(b2lEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(b3fEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addComponent(b3lEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hdfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addComponent(hdlEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ccfEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13))
                    .addComponent(cclEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6.setName("jPanel6"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        bugout.setText(resourceMap.getString("bugout.text")); // NOI18N
        bugout.setName("bugout"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bugout, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(bugout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(foutputtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16))))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(foutputtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 404, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(215, 215, 215))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(235, 235, 235)
                        .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        openPRGItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openPRGItem.setIcon(new javax.swing.JLabel() {
            public javax.swing.Icon getIcon() {
                try {
                    return new javax.swing.ImageIcon(
                        new java.net.URL("file:/C:/Program Files/LEDWorks1.4.0/dist/open.PNG")
                    );
                } catch (java.net.MalformedURLException e) {
                }
                return null;
            }
        }.getIcon());
        openPRGItem.setText(resourceMap.getString("openPRGItem.text")); // NOI18N
        openPRGItem.setName("openPRGItem"); // NOI18N
        openPRGItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openPRGItemActionPerformed(evt);
            }
        });
        fileMenu.add(openPRGItem);

        newPRGItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newPRGItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ledworks4/resources/new.PNG"))); // NOI18N
        newPRGItem.setText(resourceMap.getString("newPRGItem.text")); // NOI18N
        newPRGItem.setName("newPRGItem"); // NOI18N
        fileMenu.add(newPRGItem);

        savePRGItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        savePRGItem.setIcon(resourceMap.getIcon("savePRGItem.icon")); // NOI18N
        savePRGItem.setText(resourceMap.getString("savePRGItem.text")); // NOI18N
        savePRGItem.setName("savePRGItem"); // NOI18N
        savePRGItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePRGItemActionPerformed(evt);
            }
        });
        fileMenu.add(savePRGItem);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(ledworks4.LEDWorks4App.class).getContext().getActionMap(LEDWorks4View.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        toolMenu.setText(resourceMap.getString("toolMenu.text")); // NOI18N
        toolMenu.setName("toolMenu"); // NOI18N

        importTabbedNames.setText(resourceMap.getString("importTabbedNames.text")); // NOI18N
        importTabbedNames.setName("importTabbedNames"); // NOI18N
        importTabbedNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importTabbedNamesActionPerformed(evt);
            }
        });
        toolMenu.add(importTabbedNames);

        boolean isOld;
        if (pm.version.equalsIgnoreCase("OLD")){ isOld = true;}else {isOld = false;}
        oldShowModeMenuItem.setSelected(isOld);
        oldShowModeMenuItem.setText(resourceMap.getString("oldShowModeMenuItem.text")); // NOI18N
        oldShowModeMenuItem.setToolTipText(resourceMap.getString("oldShowModeMenuItem.toolTipText")); // NOI18N
        oldShowModeMenuItem.setName("oldShowModeMenuItem"); // NOI18N
        oldShowModeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oldShowModeMenuItemActionPerformed(evt);
            }
        });
        toolMenu.add(oldShowModeMenuItem);

        consoleMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        consoleMenuItem1.setSelected(true);
        consoleMenuItem1.setText(resourceMap.getString("consoleMenuItem1.text")); // NOI18N
        consoleMenuItem1.setName("consoleMenuItem1"); // NOI18N
        consoleMenuItem1.setName("consoleMenuItem1"); // NOI18N
        consoleMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                consoleMenuItem1MouseClicked(evt);
            }
        });
        consoleMenuItem1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                consoleMenuItem1ItemStateChanged(evt);
            }
        });
        toolMenu.add(consoleMenuItem1);

        jSeparator1.setName("jSeparator1"); // NOI18N
        toolMenu.add(jSeparator1);

        showVersionPrefMenu.setText(resourceMap.getString("showVersionPrefMenu.text")); // NOI18N
        showVersionPrefMenu.setName("showVersionPrefMenu"); // NOI18N
        showVersionPrefMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showVersionPrefMenuActionPerformed(evt);
            }
        });
        toolMenu.add(showVersionPrefMenu);

        menuBar.add(toolMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        portsMenuItem.setText(resourceMap.getString("portsMenuItem.text")); // NOI18N
        portsMenuItem.setName("portsMenuItem"); // NOI18N
        menuBar.add(portsMenuItem);

        statusPanel.setName("statusPanel"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(statusMessageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 691, Short.MAX_VALUE)
                        .addComponent(statusAnimationLabel))
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel))
                .addGap(33, 33, 33))
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents
    /** If TRUE, will show labels and input fields for
     *  entering an engagemant into the sign text
     * @param evt
     */
    private void engagementButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_engagementButtonMouseClicked
        if (engagementButton.isSelected()) {
            jLabel16.setVisible(true);
            jLabel17.setVisible(true);
            emfEdit.setVisible(true);
            emlEdit.setVisible(true);
            ewfEdit.setVisible(true);
            ewlEdit.setVisible(true);
        } else{
            jLabel16.setVisible(false);
            jLabel17.setVisible(false);
            emfEdit.setVisible(false);
            emlEdit.setVisible(false);
            ewfEdit.setVisible(false);
            ewlEdit.setVisible(false);
        }
}//GEN-LAST:event_engagementButtonMouseClicked

    private void shownumButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shownumButton1MouseClicked
        foutputtxt.setText("show1.prg");
}//GEN-LAST:event_shownumButton1MouseClicked

    private void shownumButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shownumButton2MouseClicked
        foutputtxt.setText("show2.prg");
}//GEN-LAST:event_shownumButton2MouseClicked

    private void shownumButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shownumButton3MouseClicked
        foutputtxt.setText("show3.prg");
}//GEN-LAST:event_shownumButton3MouseClicked

    private void showTimeMornMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showTimeMornMouseClicked
        if (showTimeNoon.isSelected()) showTimeNoon.doClick();
        else showTimeNite.doClick();
        timeChanged();
}//GEN-LAST:event_showTimeMornMouseClicked

    private void showTimeNoonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showTimeNoonMouseClicked
        if (showTimeMorn.isSelected()) showTimeMorn.doClick();
        else showTimeNite.doClick();
        timeChanged();
}//GEN-LAST:event_showTimeNoonMouseClicked

    private void showTimeNiteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showTimeNiteMouseClicked
        if (showTimeNoon.isSelected()) showTimeNoon.doClick();
        else showTimeMorn.doClick();
        timeChanged();
}//GEN-LAST:event_showTimeNiteMouseClicked

    private void advancedButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_advancedButtonMouseClicked
        if (advancedButton.isSelected()) {
            plainTextOut.setEnabled(true);
            markedTextOut.setEnabled(true);
            clearTextButton.setEnabled(true);
            decodeText.setEnabled(true);
            createTextFile.setEnabled(true);
        } else {
            plainTextOut.setEnabled(false);
            markedTextOut.setEnabled(false);
            clearTextButton.setEnabled(false);
            decodeText.setEnabled(false);
            createTextFile.setEnabled(false);
        }
}//GEN-LAST:event_advancedButtonMouseClicked

    private void clearTextButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearTextButtonMouseClicked
        rightSignEdit.setText("");
        bugout.setText("Text Field is Empty");
}//GEN-LAST:event_clearTextButtonMouseClicked
    /** This method loads local nameArray and tagName and
     *  uses these to insert names into sign text
     */
    private void loadNamesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadNamesButtonMouseClicked
        loadNamesMethod();
}//GEN-LAST:event_loadNamesButtonMouseClicked

    private void decodeTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_decodeTextMouseClicked
        leftSignEdit.setText(swapListText(leftSignEdit.getText(),textSymbol, signSymbol));
        rightSignEdit.setText(swapListText(rightSignEdit.getText(),textSymbol, signSymbol));
        bugout.setText("Text has been filtered.");
}//GEN-LAST:event_decodeTextMouseClicked

    private void markedTextOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_markedTextOutMouseClicked
        processForPRG();
        bugout.setText("Marked Text has been converted to PRG format.");
}//GEN-LAST:event_markedTextOutMouseClicked

    private void markedTextOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markedTextOutActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_markedTextOutActionPerformed

    private void createTextFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createTextFileMouseClicked
        String outFileName = new String(foutputtxt.getText());
        String tempstr = leftSignEdit.getText() +"%"+ rightSignEdit.getText();
        if (outFileName.length() == 0) {
            bugout.setText("No destination file was selected. Text saved as Untitled1.txt ");
            outFileName = showFilePath.concat("untitled1.PRG");
        }
        savePRGFile(outFileName);
}//GEN-LAST:event_createTextFileMouseClicked

    private void plainTextOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plainTextOutMouseClicked
        String outFileName = new String(foutputtxt.getText());
        if (outFileName.length() == 0) {
            bugout.setText("No destination file was selected. Text saved as Untitled1.PRG ");
            outFileName = showFilePath.concat("untitled1.PRG");
        }
        String tempstr = prepOut("¯A" + leftSignEdit.getText()+ "");
        eLogFrame.bugout.append("\n...returned from prepOut call \n");
        leftSignEdit.setText(tempstr);
        String tempstr2 = prepOut("¯A" + rightSignEdit.getText()+ "");
        eLogFrame.bugout.append("\n...returned from prepOut call \n");
        rightSignEdit.setText(tempstr2);
        saveToFile(outFileName);
}//GEN-LAST:event_plainTextOutMouseClicked

    private void savePRGItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePRGItemActionPerformed
        String actionCommand = evt.getActionCommand();
        if ("Save Show PRG File".equalsIgnoreCase(actionCommand)){
            JFileChooser fC = new JFileChooser();
            fC.setCurrentDirectory(new File(showFilePath));
            if (fC.showSaveDialog(getFrame())==fC.APPROVE_OPTION){
                fileIOHandler fios = new fileIOHandler(fC.getSelectedFile() + "");
                String oS = leftSignEdit.getText()+"%"+ rightSignEdit.getText();
                // must get the string processed and sent here
                fios.saveFile(oS);
                bugout.setText("File " + fC.getSelectedFile().getName() + " successfully saved.");
            }
        }
    }//GEN-LAST:event_savePRGItemActionPerformed

    private void openPRGItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openPRGItemActionPerformed
        fileIOHandler fios = new fileIOHandler();
        String actionCommand = evt.getActionCommand();
        if ("Open Show PRG File".equalsIgnoreCase(actionCommand)){
            JFileChooser fC = new JFileChooser();
            fC.setCurrentDirectory(new File(showFilePath));
            if (fC.showOpenDialog(getFrame())== fC.APPROVE_OPTION){
                rawStr = fios.loadFile(fC.getSelectedFile());
                foutputtxt.setText(fC.getName(fC.getSelectedFile()));
                if (rawStr.contains("%")){
                    String[] splitText = rawStr.split("%");
                    if (splitText[0].startsWith("<names>")){
                        importNamesToInputFields(splitText[0]);
                        rightSignEdit.setText(splitText[1]);
                        if (splitText.length > 2){
                            leftSignEdit.setText(splitText[2]);
                        }
                    }else {
                        rightSignEdit.setText(splitText[0]);
                        leftSignEdit.setText(splitText[1]);
                    }
                } else{
                    rightSignEdit.setText(rawStr);
                }
                
                bugout.setText("File " + fC.getSelectedFile().getName() + " successfully opened.");
            }
        }

    }//GEN-LAST:event_openPRGItemActionPerformed

    private void importTabbedNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importTabbedNamesActionPerformed
        String actionCommand = evt.getActionCommand();
        if ("Import tab delimited names".equalsIgnoreCase(actionCommand)){
            bugout.setText("Getting Names from below...");
            importNames();
        }
    }//GEN-LAST:event_importTabbedNamesActionPerformed

    private void createPRGButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createPRGButtonMouseClicked
        createPRGMethod();
    }//GEN-LAST:event_createPRGButtonMouseClicked

    private void sendButtonLeftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendButtonLeftMouseClicked
        // TODO add your handling code here:
        SerialWrite sw = new SerialWrite();
        sw.sendToSign(leftSignEdit.getText());
}//GEN-LAST:event_sendButtonLeftMouseClicked

    private void sendButtonRightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendButtonRightMouseClicked
        SerialWrite sw = new SerialWrite();
        sw.sendToSign(rightSignEdit.getText());
}//GEN-LAST:event_sendButtonRightMouseClicked

    private void clearTextButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearTextButton1MouseClicked
        // TODO add your handling code here:
        leftSignEdit.setText("");
        bugout.setText("Text Field is Empty");
    }//GEN-LAST:event_clearTextButton1MouseClicked

    private void importNamesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_importNamesButtonMouseClicked
        importNames();
}//GEN-LAST:event_importNamesButtonMouseClicked

    private void consoleMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_consoleMenuItem1MouseClicked
        

    }//GEN-LAST:event_consoleMenuItem1MouseClicked

    private void consoleMenuItem1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_consoleMenuItem1ItemStateChanged
        if (consoleMenuItem1.isSelected()){log.setVisible(true);}
        else{ log.setVisible(false);}
        log.bugout.append("console.isVisible:"+ log.isVisible()+ "\n");
    }//GEN-LAST:event_consoleMenuItem1ItemStateChanged

    private void oldShowModeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oldShowModeMenuItemActionPerformed
        String actionCommand = evt.getActionCommand();
        eLogFrame.bugout.append("actionComm: "+ actionCommand);
        if ("Program Old Show".equalsIgnoreCase(actionCommand)){
            if ((oldShowModeMenuItem.isSelected())&&(pm.version.equalsIgnoreCase("new"))){
                updateFields("oldfieldsource.lrf");
                pm.version = "OLD";
            }else if ((!oldShowModeMenuItem.isSelected())&&(pm.version.equalsIgnoreCase("old"))){
                updateFields("newfieldsource.lrf");
                pm.version = "NEW";
            }
        }
        if (oldShowModeMenuItem.isSelected()){
            showVersionPrefMenu.setText("Save as Old Show Version");
        }else {
            showVersionPrefMenu.setText("Save as New Show Version");
        }
    }//GEN-LAST:event_oldShowModeMenuItemActionPerformed

    private void showVersionPrefMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showVersionPrefMenuActionPerformed
        String showVersion ="";
        String actionCommand = evt.getActionCommand();
        if ("Save as Old Show Version".equalsIgnoreCase(actionCommand)){
            showVersion = "OLD";
            pm.updatePrefToFile("NEW", showVersion);
        } else if ("Save as New Show Version".equalsIgnoreCase(actionCommand)){
            showVersion = "NEW";
            pm.updatePrefToFile("OLD", showVersion);
        }

    }//GEN-LAST:event_showVersionPrefMenuActionPerformed
    private void timeChanged(){
        if (showTimeNite.isSelected()){
            changeTime("EVENING");
        }
        else if (showTimeMorn.isSelected()){
                 changeTime("MORNING");
             }
                 else if (showTimeNoon.isSelected()){
                          changeTime("AFTERNOON");
                      }
    }
    private void changeTime(String tod){
        String message = leftSignEdit.getText();
        message = message.replace("EVENING", tod);
        message = message.replace("AFTERNOON", tod);
        message = message.replace("MORNING", tod);
        leftSignEdit.setText(message);
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox advancedButton;
    private javax.swing.JTextField b1fEdit;
    private javax.swing.JTextField b1lEdit;
    private javax.swing.JTextField b2fEdit;
    private javax.swing.JTextField b2lEdit;
    private javax.swing.JTextField b3fEdit;
    private javax.swing.JTextField b3lEdit;
    private javax.swing.JTextField bugout;
    private javax.swing.JTextField ccfEdit;
    private javax.swing.JTextField cclEdit;
    private javax.swing.JButton clearTextButton;
    private javax.swing.JButton clearTextButton1;
    private javax.swing.JCheckBoxMenuItem consoleMenuItem1;
    private javax.swing.JButton createPRGButton;
    private javax.swing.JButton createTextFile;
    private javax.swing.JButton decodeText;
    private javax.swing.JTextField emfEdit;
    private javax.swing.JTextField emlEdit;
    private javax.swing.JCheckBox engagementButton;
    private javax.swing.JTextField ewfEdit;
    private javax.swing.JTextField ewlEdit;
    private javax.swing.JTextField foutputtxt;
    private javax.swing.JTextField gr1Edit;
    private javax.swing.JTextField gr2Edit;
    private javax.swing.JTextField gr3Edit;
    private javax.swing.JTextField gr4Edit;
    private javax.swing.JTextField hdfEdit;
    private javax.swing.JTextField hdlEdit;
    private javax.swing.JTextField hgfEdit;
    private javax.swing.JTextField hglEdit;
    private javax.swing.JButton importNamesButton;
    private javax.swing.JMenuItem importTabbedNames;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextArea leftSignEdit;
    private javax.swing.JButton loadNamesButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton markedTextOut;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newPRGItem;
    private javax.swing.JTextField ogfEdit;
    private javax.swing.JTextField oglEdit;
    public javax.swing.JCheckBoxMenuItem oldShowModeMenuItem;
    private javax.swing.JMenuItem openPRGItem;
    private javax.swing.JButton plainTextOut;
    private javax.swing.JMenu portsMenuItem;
    private javax.swing.JTextField ppfEdit;
    private javax.swing.JTextField pplEdit;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTextArea rightSignEdit;
    private javax.swing.JMenuItem savePRGItem;
    private javax.swing.JButton sendButtonLeft;
    private javax.swing.JButton sendButtonRight;
    private javax.swing.JRadioButton showTimeMorn;
    private javax.swing.JRadioButton showTimeNite;
    private javax.swing.JRadioButton showTimeNoon;
    private javax.swing.JMenuItem showVersionPrefMenu;
    private javax.swing.JRadioButton shownumButton1;
    private javax.swing.JRadioButton shownumButton2;
    private javax.swing.JRadioButton shownumButton3;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenu toolMenu;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    /** Clears all inputs fields
     */
    public void clearFields(){
         // Reset all of the input fields
        gr1Edit.setText("");
        gr2Edit.setText("");
        gr3Edit.setText("");
        gr4Edit.setText("");
        ppfEdit.setText("");
        pplEdit.setText("");
        hgfEdit.setText("");
        hglEdit.setText("");
        ogfEdit.setText("");
        oglEdit.setText("");
        b1fEdit.setText("");
        b1lEdit.setText("");
        b2fEdit.setText("");
        b2lEdit.setText("");
        b3fEdit.setText("");
        b3lEdit.setText("");
        hdfEdit.setText("");
        hdlEdit.setText("");
        ccfEdit.setText("");
        cclEdit.setText("");
        emfEdit.setText("");
        emlEdit.setText("");
        ewfEdit.setText("");
        ewlEdit.setText("");
    }
    /*---------------------------------------------------
     * Non-Frame related methods down here              *
     * -------------------------------------------------*/
    
    /** Returns a 24 index array filled with the values
     *  found in the frame input (edit) fields; all values
     *  are forced into upper case strings.
     */
    public String[] loadArray(){
        String[] tempArray = new String[24];
        /*-------------------------------------------------
        | put the names into the correct slots of the     |
        | nameArray                                       |
        --------------------------------------------------*/
        tempArray[0] = gr1Edit.getText().toUpperCase();
        tempArray[1] = gr2Edit.getText().toUpperCase();
        tempArray[2] = gr3Edit.getText().toUpperCase();
        tempArray[3] = gr4Edit.getText().toUpperCase();
        tempArray[4] = ppfEdit.getText().toUpperCase();
        tempArray[5] = pplEdit.getText().toUpperCase();
        tempArray[6] = hgfEdit.getText().toUpperCase();
        tempArray[7] = hglEdit.getText().toUpperCase();
        tempArray[8] = ogfEdit.getText().toUpperCase();
        tempArray[9] = oglEdit.getText().toUpperCase();
        tempArray[10] = b1fEdit.getText().toUpperCase();
        tempArray[11] = b1lEdit.getText().toUpperCase();
        tempArray[12] = b2fEdit.getText().toUpperCase();
        tempArray[13] = b2lEdit.getText().toUpperCase();
        tempArray[14] = b3fEdit.getText().toUpperCase();
        tempArray[15] = b3lEdit.getText().toUpperCase();
        tempArray[16] = hdfEdit.getText().toUpperCase();
        tempArray[17] = hdlEdit.getText().toUpperCase();
        tempArray[18] = ccfEdit.getText().toUpperCase();
        tempArray[19] = cclEdit.getText().toUpperCase();
        tempArray[20] = emfEdit.getText().toUpperCase();
        tempArray[21] = emlEdit.getText().toUpperCase();
        tempArray[22] = ewfEdit.getText().toUpperCase();
        tempArray[23] = ewlEdit.getText().toUpperCase();
        return tempArray;
    }
    /** Grabs text from the main edtResult field and
     *  then calls swapListText and prepOut before
     *  rewriting edtResult field with sign ready text
     */
    public void processForPRG(){
        eLogFrame.bugout.append("getting text from frames...\n");
        String rightText = processForPRG(rightSignEdit.getText());
        rightSignEdit.setText(rightText);
        if (leftSignEdit.getText().length() > 0 ){
            String leftText = processForPRG(leftSignEdit.getText());
            leftSignEdit.setText(leftText);
        }
    }

    public static String processForPRG(String tempText){
        eLogFrame.bugout.append("got text from method call...\n");
        tempText = swapListText(tempText,signCode,tagCode);
        tempText = prepOut(tempText);
        eLogFrame.bugout.append("prepOut completed \n");
        return tempText;
    }
    /** Given thisText, this method replaces all strings of
     *  value swapOut with values in swapIn, index for index.
     *  If swapIn array is smaller the swapOut, method will
     *  call a warning "null swap ins" to eLogFrame.  If swapIn
     *  is longer, thisText will be missing those values.
     */
    static String swapListText(String thisText, String[] swapIn, String[] swapOut){
        for (int h = 0; h < swapOut.length; h++){
            if (h < swapIn.length ){
                thisText = thisText.replaceAll(swapOut[h],swapIn[h]);
                eLogFrame.bugout.append("swap " + swapOut[h]+ " for " + swapIn[h]+ "\n");
            }
            else{
                thisText = thisText.replaceAll(swapOut[h],"");
                eLogFrame.bugout.append("null swap ins"+ "\n");
            }
        }
        eLogFrame.bugout.append("swapListText call complete \n");
        return thisText;
    }
    /**
     * Takes a string and replaces all blank spaces with a colon ":"
     * @param readyTxt is the String passed into the method
     * @return returns the passed string after swapping out blank spaces
     */
    static String blankSpacers(String readyTxt){
        readyTxt = readyTxt.replaceAll(" ", ":");
        return readyTxt;
    }
    static String simplePrep(String s){
        eLogFrame.bugout.append("simplePrep called...\n");
        String codedText = s;
        eLogFrame.bugout.append(codedText +"\n");
        //Temporarily 'hide' dual byte signcode
        codedText = hideColons(codedText);
        codedText = codedText.replace("\u00AF\u0041", "\u00AB");
        codedText = codedText.replaceAll("¡", "\u00AC");
        codedText = codedText.replace("\u0008", "\u00AE");
        codedText = codedText.replace("\u0020", "\u003A");
        eLogFrame.bugout.append("sign codes hidden \n");
        eLogFrame.bugout.append(codedText +"\n");
        int textlength = (2*codedText.length())-1;
        char[] ca = new char[textlength];
        //Insert color code for red text
        int j = 0;
        for (int i = 0; i <codedText.length();i++){
            ca[j] = codedText.charAt(i);
            eLogFrame.bugout.append(ca[j]+"");
            ca[j+1] = '\u0001';
            eLogFrame.bugout.append(ca[j+1]+"");
            j+=2;
        }
        eLogFrame.bugout.append("\n color code inserted \n");
        codedText.copyValueOf(ca);
        codedText = codedText.replace("\u00AB","\u00AF\u0041");
        codedText = codedText.replaceAll("\u00AC","¡*");
        codedText = codedText.replace("\u00AE","\u0008");
        codedText = codedText.replace("\u003A","\u0020");
        codedText = restoreColons(codedText);
        return codedText;
    }
    /** This method returns a string into which sign
     *  specific code has been added.  String should
     *  otherwise be complete before calling this
     *  method as further manipulation will not be
     *  possible if the text is to load to the sign correctly
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
                    if ((readyTxt.charAt(i) == '¯') || (readyTxt.charAt(i) == '¡') ||   //AFh or A1h
                            (readyTxt.charAt(i) == '') || (readyTxt.charAt(i) == '')) //08h or 01h
                    {
                            eLogFrame.bugout.append(".");
                            holdTxt = holdTxt + readyTxt.charAt(i);
                            if ((readyTxt.charAt(i) == '')|| (readyTxt.charAt(i) == ''))//08h or 01h
                                    break; // OEF marker: in this special case stop processing String
                            i++;       // move forward to tag after marker
                            holdTxt = holdTxt + readyTxt.charAt(i);	// concat tag into string
                            eLogFrame.bugout.append("-");
                            continue;
                    }
                    if ( i != readyTxt.length() ){
                            holdTxt = holdTxt + '';	// if we haven't reached the end, insert spacer 01h
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
     * Since Sunnywell signs use the unicode character ":" to mark a blank space
     * character, we must hide any colons we want to actually appear on the
     * before processing.
     * @param s The String which contains the colons we wish to "hide" using
     * the "#" character.
     * @return A string where any instance of ":" has been replaced with "#".
     */
    private static String hideColons(String s){
        s = s.replace(":", "#");
        return s;
    }
    /**
     * Replaces any ":" that were swapped out with the unicode character that
     * Sunnywell signs use to display colons.
     * @param s
     * @return
     */
    private static String restoreColons(String s){
        s = s.replace("#", "\u003A");
        return s;
    }
    /** Calls the processForPRG method and then saves the result
     *  in the directory "C:\\Program Files\\LEDWORKS1.4.0\\dist\\"
     *  with file specified in fileName
     */
    public void savePRGFile(String fileName){
        fileIOHandler fios = new fileIOHandler(showFilePath.concat(fileName));
        processForPRG();
        String oS= leftSignEdit.getText() +"%"+ rightSignEdit.getText();
        fios.saveFile(oS);
        bugout.setText("File " + fileName + " successfully saved.");
    }
    public void saveToFile(String fileName){
        fileIOHandler fios = new fileIOHandler(showFilePath.concat(fileName));
        String oS = leftSignEdit.getText() +"%"+ rightSignEdit.getText();
        fios.saveFile(oS);
        bugout.setText("File " + fileName + " successfully saved.");
    }
    public void populateFields(String[][] showRecord){
        eLogFrame.bugout.append("Total records for processing: "+ showRecord.length +"\n");
        String[] tempArray = new String[24];
        String fileName ="";
        tempArray = initArray(tempArray);
        String[] multishow = new String[showRecord.length];
        int index = 4;
        int first = 0;
        int last = 1;
        for (int q = 0; q < showRecord.length; q++){
            if (showRecord[q][0].startsWith("<names>")){
                showRecord[q][0] = showRecord[q][0].replace("<names>", "");
                fileName = showRecord[q][1];
            }else {
                fileName = showRecord[q][0].replace('/', '_');
                fileName = fileName.concat("_"+ showRecord[q][1]);
                if (oldShowModeMenuItem.isSelected()){
                    fileName = fileName.concat("_old");
                }
            }
            
            foutputtxt.setText(fileName);
            if (showRecord[q][2].contains(";")){
                String[] groups = showRecord[q][2].split(";");
                groups = trimArray(groups);
                for (int count = 0;count < groups.length; count++){
                    tempArray[count] = groups[count].toUpperCase();
                }
            } else if (showRecord[q][2].length() > 0){
                tempArray[0]= showRecord[q][2].toUpperCase();
            }
            for (int j = 3; j < showRecord[q].length; j++){
                if ((showRecord[q][j].contains(";")) && ( j == 6)){
                    String[] bNames = showRecord[q][6].split(";");
                    bNames = trimArray(bNames);
                    for (int bcount = 0 ; bcount < bNames.length ; bcount++){
                        String[] birthN = bNames[bcount].split(" ");
                        tempArray[index]= birthN[first].toUpperCase();
                        index++;
                        tempArray[index]= birthN[last].toUpperCase();
                        index++;
                    }
                    index = 16;
                    j = 7;
                }
                if (!showRecord[q][j].isEmpty()){
                    String name[] = showRecord[q][j].split(" ");
                    eLogFrame.bugout.append("name length ="+ name.length + "\n");
                    if (name.length > 0){
                        eLogFrame.bugout.append("index = "+ index +"\n");
                        tempArray[index] = name[first].toUpperCase();
                        eLogFrame.bugout.append("first: "+ tempArray[index] +"  ");
                        index++;
                        tempArray[index] = name[last].toUpperCase();
                        eLogFrame.bugout.append("last: "+ tempArray[index]+ "\n");
                        index++;
                        if (j==6){ index = 16; }
                    }
                    
                }
                
            }
            if (showRecord.length == 1){
                pokeEdits(tempArray);
            }else if(showRecord.length > 1){
                pokeEdits(tempArray);
                loadNamesMethod();
                createPRGMethod();
                multishow[q] = fileName;
            }

            tempArray = initArray(tempArray);
            index = 4;

        }
        if (showRecord.length > 1){showFilesCreated(multishow);}
        
    }
    private String[] initArray(String[] array){
        for (int i = 0; i < array.length; i++){
            array[i] = "";
        }
        return array;
    }
    private String[] trimArray(String[] array){
        for (int i = 0; i < array.length; i++){
            array[i] = array[i].trim();
        }
        return array;
    }
    private void pokeEdits(String[] nameArray){
        gr1Edit.setText(nameArray[0]);
        gr2Edit.setText(nameArray[1]);
        gr3Edit.setText(nameArray[2]);
        gr4Edit.setText(nameArray[3]);
        ppfEdit.setText(nameArray[4]);
        pplEdit.setText(nameArray[5]);
        hgfEdit.setText(nameArray[6]);
        hglEdit.setText(nameArray[7]);
        ogfEdit.setText(nameArray[8]);
        oglEdit.setText(nameArray[9]);
        b1fEdit.setText(nameArray[10]);
        b1lEdit.setText(nameArray[11]);
        b2fEdit.setText(nameArray[12]);
        b2lEdit.setText(nameArray[13]);
        b3fEdit.setText(nameArray[14]);
        b3lEdit.setText(nameArray[15]);
        hdfEdit.setText(nameArray[16]);
        hdlEdit.setText(nameArray[17]);
        ccfEdit.setText(nameArray[18]);
        cclEdit.setText(nameArray[19]);
        emfEdit.setText(nameArray[20]);
        emlEdit.setText(nameArray[21]);
        ewfEdit.setText(nameArray[22]);
        ewlEdit.setText(nameArray[23]);

    }
    private void pokeEdits(String[] nameArray, boolean moreRecords){
        gr1Edit.setText(nameArray[0]);
        gr2Edit.setText(nameArray[1]);
        gr3Edit.setText(nameArray[2]);
        gr4Edit.setText(nameArray[3]);
        ppfEdit.setText(nameArray[4]);
        pplEdit.setText(nameArray[5]);
        hgfEdit.setText(nameArray[6]);
        hglEdit.setText(nameArray[7]);
        ogfEdit.setText(nameArray[8]);
        oglEdit.setText(nameArray[9]);
        b1fEdit.setText(nameArray[10]);
        b1lEdit.setText(nameArray[11]);
        b2fEdit.setText(nameArray[12]);
        b2lEdit.setText(nameArray[13]);
        b3fEdit.setText(nameArray[14]);
        b3lEdit.setText(nameArray[15]);
        hdfEdit.setText(nameArray[16]);
        hdlEdit.setText(nameArray[17]);
        ccfEdit.setText(nameArray[18]);
        cclEdit.setText(nameArray[19]);
        emfEdit.setText(nameArray[20]);
        emlEdit.setText(nameArray[21]);
        ewfEdit.setText(nameArray[22]);
        ewlEdit.setText(nameArray[23]);
    }
    private void importNames(){
        String[] dataLines = rightSignEdit.getText().split("\n");
        String[][] dataRecord = new String[dataLines.length][];
        for (int i = 0; i < dataLines.length; i++){
            dataRecord[i] = dataLines[i].split("\u0009");
        }
        rightSignEdit.setText("Clear...processing, please wait... \n");
        for (int q = 0; q < dataRecord.length; q++){
            eLogFrame.bugout.append("Record #"+ (q+1) +"\n");
            for (int w = 0; w < dataRecord[q].length; w++){
                eLogFrame.bugout.append(dataRecord[q][w] + "\u0009" );
            }
            eLogFrame.bugout.append("\n");
        }
        populateFields(dataRecord);
    }
    private void importNamesToInputFields(String names){
        String[] dataLines = names.split("\n");
        String[][] dataRecord = new String[dataLines.length][];
        for (int i = 0; i < dataLines.length; i++){
            dataRecord[i] = dataLines[i].split("\u0009");
        }
        bugout.setText("...processing, please wait... \n");
        for (int q = 0; q < dataRecord.length; q++){
            for (int w = 0; w < dataRecord[q].length; w++){
                eLogFrame.bugout.append(dataRecord[q][w] + "\u0009" );
            }
            eLogFrame.bugout.append("\n");
        }
        populateFields(dataRecord);
    }
    private void showFilesCreated(String[] n){
        rightSignEdit.setText("Multiple show records were found.  Each record has been saved as a show:\n");
        for (int i = 0; i < n.length; i++){
            rightSignEdit.append("Record #"+(i+1) +":"+ n[i] +"\n");
        }
        rightSignEdit.append("\n Use the File Menu to open each show for inspection prior to sending to sign.");
    }
    private String recordToString(String[][] dRecord){
        String rts = "";
        for (int q = 0; q < dRecord.length; q++){
            for (int w = 0; w < dRecord[q].length; w++){
                rts = rts.concat(dRecord[q][w] + "\u0009" );
            }
            rts= rts.concat("\n");
        }
        eLogFrame.bugout.append("recordToString...\n");
        for (int q = 0; q < dRecord.length; q++){
            for (int w = 0; w < dRecord[q].length; w++){
                eLogFrame.bugout.append(dRecord[q][w] + "\u0009" );
            }
            eLogFrame.bugout.append("\n");
        }
        return rts;
    }
    private String[][] arrayToSingleRecord(String[] a){
        String[][] dR = new String[1][9];
        dR[0][0] = getField(a,0,3);         //groups
        dR[0][1] = getFullName(a,4);        //prize or medal
        dR[0][2] = getFullName(a,6);        //genome or space tourist
        dR[0][3] = getFullName(a,8);        //ordinary guy
        dR[0][4] = getField2(a,10,15);      //birthdays
        dR[0][5] = getFullName(a,16);       //headache
        dR[0][6] = getFullName(a,18);       //cc or facebook
        dR[0][7] = getFullName(a,20);       //engagement male
        dR[0][8] = getFullName(a,22);       //engagement female
        // debug debug
        eLogFrame.bugout.append("arrayToSingleRecord...\n");
        for (int q = 0; q < dR.length; q++){
            for (int w = 0; w < dR[q].length; w++){
                eLogFrame.bugout.append(dR[q][w] + "\u0009" );
            }
            eLogFrame.bugout.append("\n");
        }
        return dR;
    }
    private String getFullName(String[] small, int index){
        String fN = "";
        fN = small[index];
        fN = fN.concat(" ");
        fN = fN.concat(small[index+1]);
        return fN;
    }
    private String getField(String[] small, int b, int e){
        String gr="";
        boolean allempty = true;
        for (int i = b; i < (e); i++ ){
            if (!small[i].isEmpty()) {
                allempty = false;
                gr = gr.concat(small[i]);
                if ((!small[i+1].isEmpty())&&(i+1 <= e)){
                    gr = gr.concat(";");
                }
            }

        }
        if (allempty){gr = "";}
        return gr;
    }
    private String getField2(String[] small, int b, int e){
        String gf="";
        boolean allempty = true;
        for (int i = b; i < (e); i=i+2 ){
            if (!small[i].isEmpty()) {
                allempty = false;
                gf = gf.concat(getFullName(small,i));
                if ((!small[i+2].isEmpty())&&(i+2 <= e)){
                    gf = gf.concat(";");
                }
            }

        }
        if (allempty){gf = "";}
        return gf;
    }
    private void loadNamesMethod(){
        eLogFrame.bugout.append("loadNames called...\n");
        String test = "";
        String[] nameArray = loadArray();
        clearTextButton.setVisible(false);
        mySign = new Signtext(nameArray, oldShowModeMenuItem.isSelected());
        if (engagementButton.isSelected()){
            mySign.engagement();
        }
        if (oldShowModeMenuItem.isSelected()){
            rightSignEdit.setText(mySign.withNames);
        } else {
            leftSignEdit.setText(mySign.leftSign);
            rightSignEdit.setText(mySign.rightSign);
        }
        if (!showTimeNite.isSelected()){
            timeChanged();
        }
        test = "There are " + mySign.rightSign.length() + " characters in the Right sign text.";
        bugout.setText(test);
        createPRGButton.setVisible(true);
    }
    private void createPRGMethod(){
        eLogFrame.bugout.append("createPRG called...\n");
        String outFileName = new String(foutputtxt.getText());
        String namesString = "<names>"+"\u0009"+outFileName+"\u0009";
        namesString = namesString.concat(recordToString(arrayToSingleRecord(mySign.nameArray)));
        fileIOHandler fios = new fileIOHandler(showFilePath.concat(outFileName));
        if (oldShowModeMenuItem.isSelected()){
            try {
                fios.saveFile(namesString + "%" + mySign.oldShowPRG);
                rightSignEdit.setText(mySign.oldShowPRG);
            } catch (NullPointerException e){
                processForPRG();
                fios.saveFile(rightSignEdit.getText());
                bugout.setText("Marked Text has been converted to PRG format.");
            }
        } else {
            try {
                fios.saveFile(namesString + "%" + mySign.rightPRG +"%"+ mySign.leftPRG);
                leftSignEdit.setText(mySign.leftPRG);
                rightSignEdit.setText(mySign.rightPRG);
            } catch (NullPointerException e){
                processForPRG();
                fios.saveFile(rightSignEdit.getText() +"%"+ leftSignEdit.getText());
                bugout.setText("Marked Text has been converted to PRG format.");
            }
        }
        bugout.setText("File " + outFileName + " successfully saved.");

        // Reset all of the input fields
        clearFields();

        if (shownumButton1.isSelected()){
            shownumButton2.doClick();
            foutputtxt.setText("show2.prg");
        } else if (shownumButton2.isSelected()){
            shownumButton3.doClick();
            foutputtxt.setText("show3.prg");
        } else if (shownumButton3.isSelected()){
            shownumButton1.doClick();
            foutputtxt.setText("show1.prg");
        }
        clearTextButton.setVisible(true);
    }
    private String importFile(String fileName){
        String temp = "";
        fileIOHandler fios = new fileIOHandler(resourceFilePath+ fileName);
        temp = fios.loadFile();
        eLogFrame.bugout.append(resourceFilePath + fileName +"\n");
        return temp;
    }
    private void updateFields(String fileName){
        eLogFrame.bugout.append("getFieldData("+ fileName+") called. \n");
        FieldData[] myNewFields = pm.getFieldData(fileName);
        jLabel2.setText(myNewFields[0].name);
        jLabel3.setText(myNewFields[1].name);
        jLabel4.setText(myNewFields[2].name);
        jLabel5.setText(myNewFields[3].name);
        jLabel6.setText(myNewFields[4].name);
        jLabel7.setText(myNewFields[5].name);
        jLabel8.setText(myNewFields[6].name);
        jLabel9.setText(myNewFields[7].name);
        jLabel10.setText(myNewFields[8].name);
        jLabel11.setText(myNewFields[9].name);
        jLabel12.setText(myNewFields[10].name);
        jLabel13.setText(myNewFields[11].name);
    }
    public static String checkForDistFolder(){
        String dir="\\";
        File f = new File("dist");
        if (f.isDirectory()){
            dir = "\\dist\\";
        }
        return dir;
    }
}
