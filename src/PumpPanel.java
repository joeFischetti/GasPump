//------------------------------------------------------------------------------
//This is the panel (card) called when after a user is selected
//	The user information is displayed, and the program takes
//	the gas pump values as input
//
//------------------------------------------------------------------------------


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PumpPanel extends JPanel {
	private static final long serialVersionUID = 034;
	
	private JComboBox<String> memberList;
	private ClubMember currentUser;

	private Font defaultFont;
	
	private JLabel memberNumberLabel, nameLabel, addressLabel, 
			cityLabel, stateLabel, zipLabel, phoneLabel,
			emailLabel, memberSinceLabel, gasPumpedLabel, 
			perGalLabel, totalPriceLabel;
	
	private JTextField txtMemberNumber, txtFullName, txtAddress, 
			txtCity, txtState, txtZip, txtPhone, 
			txtEmail, txtMemberSince, txtGasPumped, 
			txtPerGal, txtTotalPrice, txtMemberStatusField;
			
	private JButton btnStartPump, btnStopPump, btnReturn;	

	
	public PumpPanel(Font specifiedFont) {
		
		defaultFont = specifiedFont;
		
		//Init all JLabels
		//
		memberNumberLabel = new JLabel("Member Number:");
		memberNumberLabel.setFont(defaultFont);
		
		nameLabel = new JLabel("Name");
		nameLabel.setFont(defaultFont);
		
		addressLabel = new JLabel("Address");
		addressLabel.setFont(defaultFont);
		
		cityLabel = new JLabel("City");
		cityLabel.setFont(defaultFont);
		
		stateLabel = new JLabel("State");
		stateLabel.setFont(defaultFont);
		
		zipLabel = new JLabel("Zip");
		zipLabel.setFont(defaultFont);
		
		phoneLabel = new JLabel("Phone");
		phoneLabel.setFont(defaultFont);
		
		emailLabel = new JLabel("Email");
		emailLabel.setFont(defaultFont);
		
		memberSinceLabel = new JLabel("Member Since");
		memberSinceLabel.setFont(defaultFont);
		
		gasPumpedLabel = new JLabel("Amount of gas pumped");
		gasPumpedLabel.setFont(defaultFont);
		
		perGalLabel = new JLabel("Price per gallon");
		perGalLabel.setFont(defaultFont);
		
		totalPriceLabel = new JLabel("Amount Due");
		totalPriceLabel.setFont(defaultFont);
		
		
		
		//Init all JTextFields
		//
		txtMemberNumber = new JTextField(20);
		txtMemberNumber.setFont(defaultFont);
		txtMemberNumber.setEnabled(false);
		
		txtFullName = new JTextField(20);
		txtFullName.setFont(defaultFont);
		txtFullName.setEnabled(false);
		
		txtAddress = new JTextField(20);
		txtAddress.setFont(defaultFont);
		txtAddress.setEnabled(false);
		
		txtCity = new JTextField(20);
		txtCity.setFont(defaultFont);
		txtCity.setEnabled(false);
		
		txtState = new JTextField(20);
		txtState.setFont(defaultFont);
		txtState.setEnabled(false);
		
		txtZip = new JTextField(20);
		txtZip.setFont(defaultFont);
		txtZip.setEnabled(false);
		
		txtPhone = new JTextField(20);
		txtPhone.setFont(defaultFont);
		txtPhone.setEnabled(false);
		
		txtEmail = new JTextField(20);
		txtEmail.setFont(defaultFont);
		txtEmail.setEnabled(false);
		
		txtMemberSince = new JTextField(20);
		txtMemberSince.setFont(defaultFont);
		txtMemberSince.setEnabled(false);
		
		txtGasPumped = new JTextField(20);
		txtGasPumped.setFont(defaultFont);
		txtGasPumped.setEnabled(false);
		
		txtPerGal = new JTextField(20);
		txtPerGal.setFont(defaultFont);
		txtPerGal.setEnabled(false);
		
		txtTotalPrice = new JTextField(20);
		txtTotalPrice.setFont(defaultFont);
		txtTotalPrice.setEnabled(false);
		
		txtMemberStatusField = new JTextField(20);
		
		
		//Init main panel buttons
		//
		btnStartPump = new JButton("Start Pump");
		btnStopPump = new JButton("Stop Pump");
		btnReturn = new JButton("Main Menu");
		btnStartPump.setFont(defaultFont);
		btnStopPump.setFont(defaultFont);
		btnReturn.setFont(defaultFont);
		
		
		//Init all JPanels
		//
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel bodyPanel = new JPanel(new GridLayout(2,1));
		JPanel returnButtonPanel = new JPanel();
		JPanel dataPanel = new JPanel(new GridLayout(12,2));
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));

		
		//Add all the components to the correct panels
		//
		returnButtonPanel.add(btnReturn);
		
		dataPanel.add(memberNumberLabel);
		dataPanel.add(txtMemberNumber);
		dataPanel.add(nameLabel);
		dataPanel.add(txtFullName);
		dataPanel.add(addressLabel);
		dataPanel.add(txtAddress);
		dataPanel.add(cityLabel);
		dataPanel.add(txtCity);
		dataPanel.add(stateLabel);
		dataPanel.add(txtState);
		dataPanel.add(zipLabel);
		dataPanel.add(txtZip);
		dataPanel.add(phoneLabel);
		dataPanel.add(txtPhone);
		dataPanel.add(emailLabel);
		dataPanel.add(txtEmail);
		dataPanel.add(memberSinceLabel);
		dataPanel.add(txtMemberSince);
		dataPanel.add(gasPumpedLabel);
		dataPanel.add(txtGasPumped);
		dataPanel.add(perGalLabel);
		dataPanel.add(txtPerGal);
		dataPanel.add(totalPriceLabel);
		dataPanel.add(txtTotalPrice);
		
		buttonPanel.add(btnStartPump);
		buttonPanel.add(btnStopPump);
		
		
		bodyPanel.add(dataPanel);
		bodyPanel.add(buttonPanel);
		
		mainPanel.add(returnButtonPanel, BorderLayout.NORTH);
		mainPanel.add(bodyPanel, BorderLayout.CENTER);
		
		add(mainPanel);
		
			
	}

	
	public void returnActionListener(ActionListener ral){
		btnReturn.addActionListener(ral);
	}
	
	public void startActionListener(ActionListener sal){
		btnStartPump.addActionListener(sal);
	}
	
	public void stopActionListener(ActionListener sal){
		btnStopPump.addActionListener(sal);
	}
	
	public void setMemberNumber(String input){
		txtMemberNumber.setText(input);
	}
	
	public ClubMember getSelectedMember(){
		return currentUser;
	}
	
	public void setStatusField(String input){
		txtMemberStatusField.setText(input);
	}
	
	public void setNameField(String input){
		txtFullName.setText(input);
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
	
	public void setPricePerGalField(String input){
		txtPerGal.setText(input);
	}
	
	public void setCurrentUser(ClubMember input){
		currentUser = input;
	}
	
	public void setGallonsPumped(String input){
		txtGasPumped.setText(input);
	}
	
	public void setTotalPrice(String input){
		txtTotalPrice.setText(input);
	}
	
	public void selectGuestMember(){
		memberList.setSelectedIndex(1);
	}
	
	public void resetMember(){
		currentUser = new ClubMember();
		txtMemberNumber.setText("");
	}
}
