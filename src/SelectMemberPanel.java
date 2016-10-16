//------------------------------------------------------------------------------
//This is the panel (card) called when the user selects launch pump
//	from the main window.  This panel allows the user to either
//	select a member from the drop down OR enter their membership number
//
//------------------------------------------------------------------------------


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SelectMemberPanel extends JPanel{
	private static final long serialVersionUID = 010;
	
	private JComboBox<String> memberList;
	private ClubMember currentUser;
	private String[] dropDownList;
	private DefaultComboBoxModel<String> dropDownModel;
	private Font defaultFont;
	private JTextField memberNumber;
	private JButton btnConnectWithLogin, btnConnectAsGuest, btnReturn;
	
	public SelectMemberPanel(Font specifiedFont, String[] dropDown) {
		
		//Initialize the dropdown list for the combobox
		dropDownList = dropDown;
		defaultFont = specifiedFont;
		dropDownModel = new DefaultComboBoxModel<String>(dropDownList);
		memberList = new JComboBox<String>();
		memberList.setFont(defaultFont);
		memberList.setModel(dropDownModel);
		
		
		//Init each panel section
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel bodyPanel = new JPanel(new GridLayout(2,1));
		JPanel returnButtonPanel = new JPanel();
		JPanel selectMemberPanel = new JPanel(new GridLayout(2,2));
		JPanel loginButtonPanel = new JPanel(new GridLayout(1,2));
		
		
		//Init each component
		//
		btnConnectWithLogin = new JButton("Login");	
		btnConnectWithLogin.setFont(defaultFont);
		
		btnConnectAsGuest = new JButton("Connect (AS GUEST)");
		btnConnectAsGuest.setFont(defaultFont);

		btnReturn = new JButton("Return to main menu");
		btnReturn.setFont(defaultFont);
		
		memberNumber = new JTextField(20);
		memberNumber.setFont(defaultFont);
		memberNumber.setPreferredSize(new Dimension(150,150));

		JLabel selectName = new JLabel("Select your name from the drop down:  ", JLabel.CENTER);
		selectName.setFont(defaultFont);
		
		JLabel enterID = new JLabel("OR enter your member ID number:", JLabel.CENTER);
		enterID.setFont(defaultFont);

		
		
		//Populate each panel
		//
		returnButtonPanel.add(btnReturn);
		selectMemberPanel.add(selectName);
		selectMemberPanel.add(memberList);
		selectMemberPanel.add(enterID);
		selectMemberPanel.add(memberNumber);
		loginButtonPanel.add(btnConnectWithLogin);
		//loginButtonPanel.add(btnConnectAsGuest);
		
		bodyPanel.add(selectMemberPanel);
		bodyPanel.add(loginButtonPanel);
		
		mainPanel.add(returnButtonPanel, BorderLayout.NORTH);
		mainPanel.add(bodyPanel, BorderLayout.CENTER);
		
		add(mainPanel);
		
	}

	public void setDropList(String[] input){
		dropDownList = input;
		dropDownModel.removeAllElements();
		for(String name : input){
			dropDownModel.addElement(name);
		}
		
		
	}
	
	public void connectActionListener(ActionListener cal){
		btnConnectWithLogin.addActionListener(cal);
	}
	
	public void connectGuestActionListener(ActionListener cal){
		btnConnectAsGuest.addActionListener(cal);
	}
	
	public void returnActionListener(ActionListener ral){
		btnReturn.addActionListener(ral);
	}
	
	public ClubMember getSelectedMember(){
		return currentUser;
	}
	
	public void setCurrentUser(ClubMember input){
		currentUser = input;
	}
	
	public String getCurrentlySelected(){
		return (String)memberList.getSelectedItem();
	}
	
	public void selectGuestMember(){
		memberList.setSelectedIndex(1);
	}
	
	public String getMemberNumber(){
		return memberNumber.getText();
		
	}
	
	public void setMemberNumber(String input){
		memberNumber.setText(input);
	}
	
	
	public void resetMember(){
		currentUser = new ClubMember();
		memberList.setSelectedIndex(0);
	}
}
