//This panel is used for displaying member information on the admin panel.  It
//	has a drop down for selecting a member, and various fields
//  for each different piece of member information
//

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MemberInfoPanel extends JPanel {
	private static final long serialVersionUID = 023;
	
	//Private variables
	//	memberListDropDown = drop down list of the member names
	//	memberStatusList = drop down list showing what the status is
	//  JTextFields = each piece of member information
	//	panelName = the name of the window (create/delete/modify member)
	//	defaultFont = the font to be used for on-screen data
	//	memberArray = a string array of each active member for the list
	//	memberListModel = the combobox model used for the drop down
	//
	private JComboBox<String> memberListDropDown, memberStatusList;
	private JTextField txtFirstName, txtLastName, txtSpouseName, txtAddress, txtCity, txtState,
				txtZip, txtEmail, txtPhone, txtMemberSince, txtGasPrice;
	private JLabel panelName;
	private Font defaultFont;
	private String[] memberArray;
	private DefaultComboBoxModel<String> memberListModel;
	
	
	//Constructor takes in the default font, the array of members and the 
	//	name to be used for the panel
	//
	public MemberInfoPanel(Font specifiedFont, String[] arrayInput, String panelNameInput) {
		super();
		
		//Initialize the values that are passed to the constructor
		//
		defaultFont = specifiedFont;
		memberArray = arrayInput;
		panelName = new JLabel(panelNameInput);
		panelName.setFont(defaultFont);

		
		//Set up the combobox model for the drop down
		//
		memberListModel = new DefaultComboBoxModel<String>(memberArray);

		
		//Create each of the text fields
		//
		txtFirstName = new JTextField();
		txtLastName = new JTextField();
		txtSpouseName = new JTextField();
		txtAddress = new JTextField();
		txtCity = new JTextField();
		txtState = new JTextField();
		txtZip = new JTextField();
		txtEmail = new JTextField();
		txtPhone = new JTextField();
		txtMemberSince = new JTextField();			
		txtGasPrice = new JTextField();
		
		
		//userInfoPanel is a panel that holds all of the member info
		//
		JPanel userInfoPanel = new JPanel();
		userInfoPanel.setLayout(new GridLayout(0, 2));
			
			
		//Create/add all of the labels and fields to the userInfoPanel
		//
		JLabel lblSelectExistingMember = new JLabel("Select Existing Member from the drop down:");
		lblSelectExistingMember.setFont(defaultFont);
		userInfoPanel.add(lblSelectExistingMember);
			
		//Member drop down
		memberListDropDown = new JComboBox<String>();
		memberListDropDown.setModel(memberListModel);
		memberListDropDown.setFont(defaultFont);
		userInfoPanel.add(memberListDropDown);	
			
		
		JLabel lblEnterFirstName = new JLabel("First Name:");
		lblEnterFirstName.setFont(defaultFont);
		userInfoPanel.add(lblEnterFirstName);
			
		//First name field
		txtFirstName.setFont(defaultFont);
		userInfoPanel.add(txtFirstName);
		txtFirstName.setText("First Name");
		txtFirstName.setColumns(10);
			
		JLabel lblEnterLastName = new JLabel("Last Name:");
		lblEnterLastName.setFont(defaultFont);
		userInfoPanel.add(lblEnterLastName);
			
		//Last name field
		txtLastName.setFont(defaultFont);
		userInfoPanel.add(txtLastName);
		txtLastName.setText("Last Name");
		txtLastName.setColumns(15);
		
		
		JLabel lblSpouseName = new JLabel("Spouse:");
		lblSpouseName.setFont(defaultFont);
		userInfoPanel.add(lblSpouseName);
		
		//Spouse name field
		txtSpouseName.setFont(defaultFont);
		userInfoPanel.add(txtSpouseName);
		txtSpouseName.setText("Spouse");
		txtSpouseName.setColumns(15);
		
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(defaultFont);
		userInfoPanel.add(lblAddress);
		
		//Address field
		txtAddress.setFont(defaultFont);
		userInfoPanel.add(txtAddress);
		txtAddress.setText("Address");
		txtAddress.setColumns(15);
		
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setFont(defaultFont);
		userInfoPanel.add(lblCity);
		
		//City name field
		txtCity.setFont(defaultFont);
		userInfoPanel.add(txtCity);
		txtCity.setText("City");
		txtCity.setColumns(15);
		
		
		JLabel lblState = new JLabel("State:");
		lblState.setFont(defaultFont);
		userInfoPanel.add(lblState);
		
		//State name field
		txtState.setFont(defaultFont);
		userInfoPanel.add(txtState);
		txtState.setText("State");
		txtState.setColumns(15);
		
		
		JLabel lblZip = new JLabel("Zip:");
		lblZip.setFont(defaultFont);
		userInfoPanel.add(lblZip);
		
		//Zip code field
		txtZip.setFont(defaultFont);
		userInfoPanel.add(txtZip);
		txtZip.setText("Zip");
		txtZip.setColumns(15);
		
		
		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setFont(defaultFont);;
		userInfoPanel.add(lblPhone);
		
		//Phone number field
		txtPhone.setFont(defaultFont);
		userInfoPanel.add(txtPhone);
		txtPhone.setText("Phone");
		txtPhone.setColumns(15);
		
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(defaultFont);
		userInfoPanel.add(lblEmail);
		
		//Email address field
		txtEmail.setFont(defaultFont);
		userInfoPanel.add(txtEmail);
		txtEmail.setText("Email");
		txtEmail.setColumns(15);
		
		
		JLabel lblMemberSince = new JLabel("Member Since:");
		lblMemberSince.setFont(defaultFont);
		userInfoPanel.add(lblMemberSince);
		
		//Member since field (populate with todays date, and disable editing)
		txtMemberSince.setFont(defaultFont);
		userInfoPanel.add(txtMemberSince);
		txtMemberSince.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		txtMemberSince.setEnabled(false);

		//Gas price field
		txtGasPrice.setFont(defaultFont);
		txtGasPrice.setEnabled(false);
				
		
		JLabel lblEnterMembershipCode = new JLabel("Membership Type:");
		lblEnterMembershipCode.setFont(defaultFont);
		userInfoPanel.add(lblEnterMembershipCode);
			
		//Create the drop down list for the member status
		memberStatusList = new JComboBox<String>();
		memberStatusList.setFont(defaultFont);
		memberStatusList.setModel(new DefaultComboBoxModel<String>(new String[] {"Member Type", "Mohawk Council Member", "Member", "Probation", "Life Member"}));
		memberStatusList.setMaximumRowCount(3);
		userInfoPanel.add(memberStatusList);
					
		
		//Add the userInfoPanel to the middle of the window, and
		//	add the name of the panel to the southern border
		//
		add(userInfoPanel, BorderLayout.CENTER);
		add(panelName, BorderLayout.SOUTH);
		
		
		//Disable all entry fields and drop downs by default, and set the 
		//	panels to visible
		//
		enableEntryFields(false);
		enableDropList(false);
		setVisible(true);
	}
	

	//ActionListener for the member list drop down.  This is what causes
	//	changes when a member's name is selected
	//
	public void memberListActionListener(ActionListener mlal){
		memberListDropDown.addActionListener(mlal);
	}
	
	
	//Public setter methods
	//--------------------------------------------------------------------------------------
	public void setFirstNameFieldText(String input){
		txtFirstName.setText(input);
	}
	
	public void setLastNameFieldText(String input){
		txtLastName.setText(input);
	}
	
	public void setSpouseNameField(String input){
		txtSpouseName.setText(input);
	}
	
	public void setAddressField(String input){
		txtAddress.setText(input);
	}
	
	public void setCityField(String input){
		txtCity.setText(input);
	}
	
	public void setStateField(String input){
		txtState.setText(input);
	}
	
	public void setZipField(String input){
		txtZip.setText(input);
	}
	
	public void setPhoneField(String input){
		txtPhone.setText(input);
	}
	
	public void setEmailField(String input){
		txtEmail.setText(input);
	}
	
	public void setMemberSinceField(String input){
		txtMemberSince.setText(input);
	}
	
	public void setMemberStatus(String input){
		memberStatusList.setSelectedItem(input);
	}
	
	
	public void setDropList(String[] input){
		memberArray = input;
		
		while(memberListDropDown.getItemCount() > 1)
			memberListModel.removeElementAt(1);
		
		for(int i = 1; i < input.length; i++){
			memberListModel.addElement(input[i]);
		}
	}
		
	
	//Public getter methods
	//--------------------------------------------------------------------------------------
	public String getSelectedName(){
		return (String)memberListDropDown.getSelectedItem();
	}
	
	public int getSelectedIndex(){
		return memberListDropDown.getSelectedIndex();
	}
	
	public String getMemberStatus(){
		return memberStatusList.getSelectedItem().toString();
	}
	
	public String getFirstNameField(){
		return txtFirstName.getText();
	}
	
	public String getLastNameField(){
		return txtLastName.getText();
	}
	
	public String getAddressField(){
		return txtAddress.getText();
	}
	
	public String getCityField(){
		return txtCity.getText();
	}
	
	public String getEmailField(){
		return txtEmail.getText();
	}
	
	public String getPhoneField(){
		return txtPhone.getText();
	}
	
	public String getSpouseNameField(){
		return txtSpouseName.getText();
	}
	
	public String getStateField(){
		return txtState.getText();
	}
	
	public String getZipField(){
		return txtZip.getText();
	}
	
	//Misc methods
	//--------------------------------------------------------------------------------------
	
	//Reset the currently selected member, and disable all the entry fields
	//
	public void resetMember(){
		memberListDropDown.setSelectedIndex(0);
		enableEntryFields(false);
	}
	
	//Enable the drop down list
	//
	public void enableDropList(boolean enable){
		memberListDropDown.setEnabled(enable);
	}
	
	//Reset the currently selected member
	//
	public void setDropListDefault(){
		memberListDropDown.setSelectedIndex(0);
	}
	
	//Set the fields to either enabled or disabled
	//	based on the value passed
	//
	public void enableEntryFields(boolean enable){
		txtFirstName.setEnabled(enable);
		txtLastName.setEnabled(enable);
		txtSpouseName.setEnabled(enable);
		txtAddress.setEnabled(enable);
		txtCity.setEnabled(enable);
		txtState.setEnabled(enable);
		txtZip.setEnabled(enable);
		txtEmail.setEnabled(enable);
		txtPhone.setEnabled(enable);
		memberStatusList.setEnabled(enable);
	}

}