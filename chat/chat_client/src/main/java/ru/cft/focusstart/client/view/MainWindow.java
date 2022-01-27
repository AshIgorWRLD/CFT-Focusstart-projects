package ru.cft.focusstart.client.view;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.client.controller.ClientChangedInfo;
import ru.cft.focusstart.client.controller.ClientChanges;
import ru.cft.focusstart.client.controller.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@Slf4j
public class MainWindow extends JFrame implements Observer {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;

    private final Container contentPane;

    private ActionListener sendMessageButtonListener;
    private JLabel numberOfClientsLabel;
    private JTextField nameTextField;
    private JTextField serverTextField;
    private JTextArea jTextAreaForMessage;
    private JTextArea jTextAreaForMembers;
    private JTextField messageTextField;
    private JButton confirmButton;

    private Font textFont = new Font("TimesNewRoman", Font.BOLD, 14);

    public MainWindow() {
        super("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        contentPane = getContentPane();
        createPreConnectionDisplay();
    }

    public void updateChatMembersAmount(int amount){
        StringBuilder stringBuilder = new StringBuilder();
        log.info("AMOUNT: " + amount);
        stringBuilder.append("Users online: ").append(String.valueOf(amount));
        numberOfClientsLabel.setText(stringBuilder.toString());
        repaint();
    }

    public void addMessage(boolean isNotify, String date, String addressee,String message){
        jTextAreaForMessage.append("[");
        jTextAreaForMessage.append(date);
        jTextAreaForMessage.append("] ");
        jTextAreaForMessage.append(addressee);
        if(!isNotify) {
            jTextAreaForMessage.append(": ");
        }
        jTextAreaForMessage.append(message);
        jTextAreaForMessage.append("\n");
        repaint();
    }

    public void refreshMembersList(ArrayList<String> members){
        jTextAreaForMembers.setText("");
        members.forEach(member->{
            jTextAreaForMembers.append(member);
            jTextAreaForMembers.append("\n");
        });
    }

    public String takeNameText(){
        String name = nameTextField.getText();
        if (name.equals("")){
            return null;
        }
        return name;
    }

    public String takeServerIpText(){
        String serverIp = serverTextField.getText();
        if (serverIp.equals("")){
            return null;
        }
        return serverIp;
    }

    public String takeMessageText(){
        String messageText = messageTextField.getText();
        messageTextField.setText("");
        return messageText;
    }

    public void setSendMessageButtonListener(ActionListener actionListener){
        sendMessageButtonListener = actionListener;
    }

    public void setLogInInfoButtonListener(ActionListener actionListener){
        confirmButton.addActionListener(actionListener);
    }

    public void createPreConnectionDisplay(){
        contentPane.removeAll();
        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        contentPane.add(createInsertPanel());
        pack();
        setLocationRelativeTo(null);
    }

    public void createChatDisplay(String name){
        contentPane.removeAll();
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        numberOfClientsLabel = new JLabel("Users online: 0");
        contentPane.add(numberOfClientsLabel, BorderLayout.NORTH);
        contentPane.add(createTextTypingPanel(name), BorderLayout.SOUTH);
        contentPane.add(createChatPanel(), BorderLayout.CENTER);
        contentPane.add(createChatMembersPanel(), BorderLayout.WEST);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createInsertPanel() {
        JPanel insertPanel = new JPanel();
        insertPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        insertPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JLabel nameLabel = new JLabel("Input your name");
        insertPanel.add(nameLabel, gbc);
        gbc.gridy = 1;
        JLabel serverIpLabel = new JLabel("Input server ip");
        insertPanel.add(serverIpLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 200;
        nameTextField = new JTextField();
        insertPanel.add(nameTextField, gbc);
        gbc.gridy = 1;
        serverTextField = new JTextField();
        insertPanel.add(serverTextField, gbc);
        confirmButton = new JButton("Connect");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        insertPanel.add(confirmButton, gbc);
        return insertPanel;
    }

    private JScrollPane createChatPanel(){
        jTextAreaForMessage = new JTextArea();
        jTextAreaForMessage.setEditable(false);
        jTextAreaForMessage.setLineWrap(true);
        jTextAreaForMessage.setFont(textFont);
        JScrollPane jScrollPane = new JScrollPane(jTextAreaForMessage);
        return jScrollPane;
    }

    private JScrollPane createChatMembersPanel(){
        jTextAreaForMembers = new JTextArea();
        jTextAreaForMembers.setEditable(false);
        jTextAreaForMembers.setLineWrap(true);
        jTextAreaForMembers.setFont(textFont);
        JScrollPane jScrollPane = new JScrollPane(jTextAreaForMembers);
        return jScrollPane;
    }

    private JPanel createTextTypingPanel(String name){
        JPanel textTypingPanel = new JPanel();
        textTypingPanel.setLayout(new BorderLayout());
        JLabel nameLabel = new JLabel(name);
        textTypingPanel.add(nameLabel, BorderLayout.WEST);
        messageTextField = new JTextField();
        textTypingPanel.add(messageTextField, BorderLayout.CENTER);
        JButton sendMessageButton = createSendMessageButton();
        textTypingPanel.add(sendMessageButton, BorderLayout.EAST);
        return textTypingPanel;
    }

    private JButton createSendMessageButton(){
        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.addActionListener(sendMessageButtonListener);
        return sendMessageButton;
    }


    @Override
    public void takeChanges(ClientChanges clientChanges, ClientChangedInfo clientChangedInfo) {
        log.info("TAKE CHANGES");
        switch (clientChanges){
            case NEW_MESSAGE -> {
                addMessage(clientChangedInfo.isNotify(), clientChangedInfo.getDate(),
                        clientChangedInfo.getAddressee(), clientChangedInfo.getMessage());
            }
            case MEMBERS_AMOUNT_UPDATED -> {
                updateChatMembersAmount(clientChangedInfo.getNames().size());
                refreshMembersList(clientChangedInfo.getNames());
            }
            case UNAVAILABLE_NAME -> {
                JOptionPane.showMessageDialog(this,
                        "Sorry, that name is already taken, choose another");
            }
            case UNKNOWN_HOST -> {
                JOptionPane.showMessageDialog(this,
                        "Sorry, that host ip is unknown");
            }
            case USER_CONNECTED -> {
               createChatDisplay(clientChangedInfo.getUserName());
            }
        }
    }
}
