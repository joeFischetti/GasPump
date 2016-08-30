//------------------------------------------------------------------------------
//This is the admin panel.  The admin panel gives the admins a way to view
//	modify, add members, as well as change gas pricing
//
//------------------------------------------------------------------------------


import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.text.DecimalFormat;

public class AdminPanel extends JPanel {
	private static final long serialVersionUID = 002;
	
	
	private JComboBox<String> adminMemberList;

	private JTextField councilMemberPrice, lifeMemberPrice, memberPrice, guestPrice;
	private Font defaultFont;
	private String[] dropDownList;
	private DefaultComboBoxModel<String> dropDownModel;
	private ClubMember currentAdminMember;
	
	JPanel mainPanelCenter;
	JPanel pricingInfoPanel;
	
	private JButton btnReturn, btnSubmit, btnTransactionReports, btnDisparityReports,
					btnAddUser, btnDeleteUser, btnModifyUser, btnPricingLevels;
	private AdminReportsPanel trp;
	private DisparityReportPanel drp;
	private MemberInfoPanel addMemberPanel, deleteMemberPanel, modifyMemberPanel;
	
	//Static strings used for cardlayout names
	//
	private static String CREATEUSER = "Create User";  //Panel 0
	private static String DELETEUSER = 	"Delete User";  //Panel 1
	private static String MODIFYUSER = "Modify User";  //Panel 2
	private static String PRICINGLEVELS = "Pricing Levels";  //Panel 3
	private static String TRANSACTIONREPORT = "Transaction Report";  //Panel 4
	private static String DISPARITYREPORT = "Disparity Report";  //Panel 5
	
	private int currentPanel;
	
	private DecimalFormat money;
	
	public AdminPanel(Font specifiedFont, String[] dropDown) {
		super();
		defaultFont = specifiedFont;
		dropDownList = dropDown;
		
		currentPanel = 0;

		dropDownModel = new DefaultComboBoxModel<String>(dropDownList);
		currentAdminMember = new ClubMember();
		setLayout(new BorderLayout());

		
		councilMemberPrice = new JTextField();
		councilMemberPrice.setFont(defaultFont);
		councilMemberPrice.setEnabled(true);
		
		lifeMemberPrice = new JTextField();
		lifeMemberPrice.setFont(defaultFont);
		lifeMemberPrice.setEnabled(true);
		
		memberPrice = new JTextField();
		memberPrice.setFont(defaultFont);
		memberPrice.setEnabled(true);
		
		guestPrice = new JTextField();
		guestPrice.setFont(defaultFont);
		guestPrice.setEnabled(true);
		
		pricingInfoPanel = new JPanel();
		pricingInfoPanel.setLayout(new GridLayout(0, 2));
		
		mainPanelCenter = new JPanel(new CardLayout());
		add(mainPanelCenter, BorderLayout.CENTER);
			
			
		JPanel userInfoPanel = new JPanel();
		userInfoPanel.setLayout(new GridLayout(0, 2));
		
		addMemberPanel = new MemberInfoPanel(defaultFont, dropDownList, "Add New Member");
		addMemberPanel.enableEntryFields(true);
		addMemberPanel.enableDropList(false);
		
		deleteMemberPanel = new MemberInfoPanel(defaultFont, dropDownList, "Delete A Member");
		deleteMemberPanel.enableEntryFields(false);
		deleteMemberPanel.enableDropList(true);
		
		modifyMemberPanel = new MemberInfoPanel(defaultFont, dropDownList, "Modify Member Information");
		modifyMemberPanel.enableEntryFields(false);
		modifyMemberPanel.enableDropList(true);
		
		trp = new AdminReportsPanel(defaultFont, dropDownList);
		drp = new DisparityReportPanel(defaultFont);
		
		
		mainPanelCenter.add(addMemberPanel, CREATEUSER);
		mainPanelCenter.add(deleteMemberPanel, DELETEUSER);
		mainPanelCenter.add(modifyMemberPanel, MODIFYUSER);
		mainPanelCenter.add(trp, TRANSACTIONREPORT);
		mainPanelCenter.add(drp, DISPARITYREPORT);
		mainPanelCenter.add(pricingInfoPanel, PRICINGLEVELS);
			
		JLabel lblOptionSelect = new JLabel("Select admin options:  ");
		lblOptionSelect.setFont(defaultFont);
		userInfoPanel.add(lblOptionSelect);
				
		
		JLabel lblSelectExistingMember = new JLabel("Select Existing Member from the drop down:");
		lblSelectExistingMember.setFont(defaultFont);
		userInfoPanel.add(lblSelectExistingMember);
			
			
		adminMemberList = new JComboBox<String>();
		adminMemberList.setModel(dropDownModel);
		adminMemberList.setFont(defaultFont);
			
		userInfoPanel.add(adminMemberList);
					
					
		JLabel lblEnterMembershipCode = new JLabel("Membership Type:");
		lblEnterMembershipCode.setFont(defaultFont);
		userInfoPanel.add(lblEnterMembershipCode);
				
		
		JLabel lblMohawkCouncilMember = new JLabel("Mohawk Council Member: ");
		lblMohawkCouncilMember.setFont(defaultFont);
		pricingInfoPanel.add(lblMohawkCouncilMember);
		
		pricingInfoPanel.add(councilMemberPrice);
		
		JLabel lblLifeMember = new JLabel("Life Member:  ");
		lblLifeMember.setFont(defaultFont);
		pricingInfoPanel.add(lblLifeMember);
		
		pricingInfoPanel.add(lifeMemberPrice);
		
		JLabel lblMember = new JLabel("Member:  ");
		lblMember.setFont(defaultFont);
		pricingInfoPanel.add(lblMember);
		
		pricingInfoPanel.add(memberPrice);
		
		JLabel lblGuest = new JLabel("Guest:  ");
		lblGuest.setFont(defaultFont);
		pricingInfoPanel.add(lblGuest);
		
		pricingInfoPanel.add(guestPrice);
	
			
		btnSubmit = new JButton("Submit Changes");
		btnSubmit.setFont(defaultFont);
			
		btnReturn = new JButton("Return to main menu");
		btnReturn.setFont(defaultFont);
		
		btnTransactionReports = new JButton("Generate Transaction Reports");
		btnTransactionReports.setFont(defaultFont);
		
		btnDisparityReports = new JButton("Generate Disparity Reports");
		btnDisparityReports.setFont(defaultFont);
		
		btnAddUser = new JButton("Add New Member");
		btnAddUser.setFont(defaultFont);
		
		btnDeleteUser = new JButton("Delete member");
		btnDeleteUser.setFont(defaultFont);
		
		btnModifyUser = new JButton("Modify member info");
		btnModifyUser.setFont(defaultFont);
		
		btnPricingLevels = new JButton("Modify Pricing Levels");
		btnPricingLevels.setFont(defaultFont);
			
		JPanel buttonPanel = new JPanel(new GridLayout(7,1));
		buttonPanel.add(btnSubmit);
		buttonPanel.add(btnTransactionReports);
		buttonPanel.add(btnDisparityReports);
		buttonPanel.add(btnAddUser);
		buttonPanel.add(btnDeleteUser);
		buttonPanel.add(btnModifyUser);
		buttonPanel.add(btnPricingLevels);
		
		add(btnReturn, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.WEST);
		
		enableDropList(false);
		setVisible(true);
		
		money = new DecimalFormat("$#.##");
		money.setMinimumFractionDigits(2);
		money.setParseBigDecimal(true);
	}
	
	
	
	//Action Listeners
	//
	public void returnActionListener(ActionListener ral){
		btnReturn.addActionListener(ral);
	}
	
	public void submitActionListener(ActionListener sal){
		btnSubmit.addActionListener(sal);
	}

	
	public void memberListActionListener(ActionListener mlal){
		adminMemberList.addActionListener(mlal);
	}
	
	public void reportsActionListener(ActionListener ral){
		btnTransactionReports.addActionListener(ral);
	}
	
	public void disparityReportActionListener(ActionListener dral){
		btnDisparityReports.addActionListener(dral);
	}
	
	public void addUserActionListener(ActionListener aual){
		btnAddUser.addActionListener(aual);
	}
	
	public void deleteUserActionListener(ActionListener dual){
		btnDeleteUser.addActionListener(dual);
	}
	
	public void modifyUserActionListener(ActionListener mual){
		btnModifyUser.addActionListener(mual);
	}
	
	public void queryTransactionActionListener(ActionListener qtal){
		trp.submitActionListener(qtal);
	}
	
	public void printTransactionActionListener(ActionListener ptal){
		trp.printActionListener(ptal);
	}
	
	public void queryDisparityActionListener(ActionListener qdal){
		drp.submitActionListener(qdal);
	}
	
	public void printDisparityActionListener(ActionListener pdal){
		drp.printActionListener(pdal);
	}
	
	public void editPricingActionListener(ActionListener epal){
		btnPricingLevels.addActionListener(epal);
	}
	
	public void modifyMemberListActionListener(ActionListener ddsal){
		modifyMemberPanel.memberListActionListener(ddsal);
	}
	
	public void deleteMemberListActionListener(ActionListener dmal){
		deleteMemberPanel.memberListActionListener(dmal);
	}
	
	
	public void setCurrentMember(ClubMember member){
		currentAdminMember = member;
	}
	
	public void setDropList(String[] input){
		dropDownList = input;
		
		while(adminMemberList.getItemCount() > 1)
			dropDownModel.removeElementAt(1);
		
		for(int i = 1; i < input.length; i++){
			dropDownModel.addElement(input[i]);
		}
		
		deleteMemberPanel.setDropList(input);
		modifyMemberPanel.setDropList(input);
		trp.setDropList(input);
		
	}
	
	public void setDropListDefault(){
		adminMemberList.setSelectedIndex(0);
	}
	
	public void enableDropList(boolean enable){
		adminMemberList.setEnabled(enable);
	}
	

	public void enablePricingUpdate(boolean enable){
		councilMemberPrice.setEnabled(enable);
		memberPrice.setEnabled(enable);
		lifeMemberPrice.setEnabled(enable);
		guestPrice.setEnabled(enable);
	}
	
	
	public void setCouncilMemberPrice(String input){
		councilMemberPrice.setText(input);
	}
	
	public void setLifeMemberPrice(String input){
		lifeMemberPrice.setText(input);
	}
	
	public void setMemberPrice(String input){
		memberPrice.setText(input);
	}
	
	public void setGuestPrice(String input){
		guestPrice.setText(input);
	}
	
	
	//Public getter methods
	//	
	public String getSelectedName(){
		
		switch(currentPanel){
					
			case 1:	return deleteMemberPanel.getSelectedName();
					
			case 2: return modifyMemberPanel.getSelectedName();
					
			case 4:	return trp.getSelectedMember();
				
			default: return new String("No User Selected");
		}

	}
	
	public int getSelectedIndex(){
		switch(currentPanel){
		
			case 1:	return deleteMemberPanel.getSelectedIndex();
				
			case 2: return modifyMemberPanel.getSelectedIndex();
			
			default: return 0;
		}
		
	}
	
	public ClubMember getSelectedMember(){
		return currentAdminMember;
	}
	
	public String getMemberStatus(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getMemberStatus();
		
			case 1:	return deleteMemberPanel.getMemberStatus();
		
			case 2: return modifyMemberPanel.getMemberStatus();
	
			default: return new String("No User Selected");
		}
	}
	
	public String getFirstNameField(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getFirstNameField();
				
			case 1:	return deleteMemberPanel.getFirstNameField();
				
			case 2: return modifyMemberPanel.getFirstNameField();
			
			default: return new String("No User Selected");
		}
	}
	
	public String getLastNameField(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getLastNameField();
			
			case 1:	return deleteMemberPanel.getLastNameField();
			
			case 2: return modifyMemberPanel.getLastNameField();
		
			default: return new String("No User Selected");
		}
	}
	
	public String getAddressField(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getAddressField();
			
			case 1:	return deleteMemberPanel.getAddressField();
			
			case 2: return modifyMemberPanel.getAddressField();
		
			default: return new String("No User Selected");
		}
	}
	
	public String getCityField(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getCityField();
			
			case 1:	return deleteMemberPanel.getCityField();
			
			case 2: return modifyMemberPanel.getCityField();
		
			default: return new String("No User Selected");
		}
	}
	
	public String getEmailField(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getEmailField();
			
			case 1:	return deleteMemberPanel.getEmailField();
			
			case 2: return modifyMemberPanel.getEmailField();
		
			default: return new String("No User Selected");
		}
	}
	
	public String getPhoneField(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getPhoneField();
			
			case 1:	return deleteMemberPanel.getPhoneField();
			
			case 2: return modifyMemberPanel.getPhoneField();
		
			default: return new String("No User Selected");
		}
	}
	
	public String getSpouseNameField(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getSpouseNameField();
			
			case 1:	return deleteMemberPanel.getSpouseNameField();
			
			case 2: return modifyMemberPanel.getSpouseNameField();
		
			default: return new String("No User Selected");
		}
	}
	
	public String getStateField(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getStateField();
			
			case 1:	return deleteMemberPanel.getStateField();
			
			case 2: return modifyMemberPanel.getStateField();
		
			default: return new String("No User Selected");
		}
	}
	
	public String getZipField(){
		switch(currentPanel){
			case 0:	return addMemberPanel.getZipField();
			
			case 1:	return deleteMemberPanel.getZipField();
			
			case 2: return modifyMemberPanel.getZipField();
		
			default: return new String("No User Selected");
		}
	}
	
	public int getCurrentPanel(){
		return currentPanel;
	}

	
	public void resetMember(){
		currentAdminMember = new ClubMember();
		adminMemberList.setSelectedIndex(0);
	}
	
	public void showTransReportPanel(){
		CardLayout cl = (CardLayout)(mainPanelCenter.getLayout());
		cl.show(mainPanelCenter, TRANSACTIONREPORT);
		currentPanel = 4;
	}
	
	public void showDisparityReportPanel(){
		CardLayout cl = (CardLayout)(mainPanelCenter.getLayout());
		cl.show(mainPanelCenter, DISPARITYREPORT);
		currentPanel = 5;
	}
	
	public void showAddMemberPanel(){
		CardLayout cl = (CardLayout)(mainPanelCenter.getLayout());
		cl.show(mainPanelCenter, CREATEUSER);
		currentPanel = 0;
	}
	
	public void showDeleteMemberPanel(){
		CardLayout cl = (CardLayout)(mainPanelCenter.getLayout());
		cl.show(mainPanelCenter, DELETEUSER);
		currentPanel = 1;
	}
	
	public void showModifyMemberPanel(){
		CardLayout cl = (CardLayout)(mainPanelCenter.getLayout());
		cl.show(mainPanelCenter, MODIFYUSER);
		currentPanel = 2;
	}
	
	public void showPricingPanel(){
		CardLayout cl = (CardLayout)(mainPanelCenter.getLayout());
		cl.show(mainPanelCenter, PRICINGLEVELS);
		currentPanel = 3;
	}
	
	public void clearTransactionResults(){
		trp.clearResults();
	}
	
	public String getTransactionSearchMember(){
		return trp.getSelectedMember();
	}
	
	public int getTransactionSearchMonth(){
		return trp.getSelectedMonth();
	}
	
	public int getTransactionSearchDate(){
		return trp.getSelectedDate();
	}
	
	public int getTransactionSearchYear(){
		return trp.getSelectedYear();
	}

	public void displayTransactionResults(String[] input){
		trp.displayResults(input);
	}
	
	public void clearDisparityResults(){
		drp.clearResults();
	}
	
	public int getDisparitySearchMonth(){
		return drp.getSelectedMonth();
	}
	
	public int getDisparitySearchDate(){
		return drp.getSelectedDate();
	}
	
	public int getDisparitySearchYear(){
		return drp.getSelectedYear();
	}

	public void displayDisparityResults(String[] input){
		drp.displayResults(input);
	}
	
	public void updateModifyPanel(){
		modifyMemberPanel.setFirstNameFieldText(getSelectedMember().getFirstName());
		modifyMemberPanel.setLastNameFieldText(getSelectedMember().getLastName());
		modifyMemberPanel.setSpouseNameField(getSelectedMember().getSpouse());
		modifyMemberPanel.setAddressField(getSelectedMember().getAddress());
		modifyMemberPanel.setCityField(getSelectedMember().getCity());
		modifyMemberPanel.setStateField(getSelectedMember().getState());
		modifyMemberPanel.setZipField(getSelectedMember().getZip());
		modifyMemberPanel.setPhoneField(getSelectedMember().getPhone());
		modifyMemberPanel.setEmailField(getSelectedMember().getEmail());
		modifyMemberPanel.setMemberStatus(getSelectedMember().getStatus());
		modifyMemberPanel.setMemberSinceField(getSelectedMember().getMemberSince());
		
		modifyMemberPanel.enableEntryFields(true);
	}
	
	public void clearModifyPanel(){
		modifyMemberPanel.enableEntryFields(false);
	}
	
	public void updateDeletePanel(){
		deleteMemberPanel.setFirstNameFieldText(getSelectedMember().getFirstName());
		deleteMemberPanel.setLastNameFieldText(getSelectedMember().getLastName());
		deleteMemberPanel.setSpouseNameField(getSelectedMember().getSpouse());
		deleteMemberPanel.setAddressField(getSelectedMember().getAddress());
		deleteMemberPanel.setCityField(getSelectedMember().getCity());
		deleteMemberPanel.setStateField(getSelectedMember().getState());
		deleteMemberPanel.setZipField(getSelectedMember().getZip());
		deleteMemberPanel.setPhoneField(getSelectedMember().getPhone());
		deleteMemberPanel.setEmailField(getSelectedMember().getEmail());
		deleteMemberPanel.setMemberStatus(getSelectedMember().getStatus());
		deleteMemberPanel.setMemberSinceField(getSelectedMember().getMemberSince());
		
	}
	
	public String getCouncilMemberPrice(){
		return councilMemberPrice.getText();
	}
	
	public String getLifeMemberPrice(){
		return lifeMemberPrice.getText();
	}
	
	public String getMemberPrice(){
		return memberPrice.getText();
	}
	
	public String getProbationPrice(){
		return guestPrice.getText();
	}
	
	public boolean includeInactive(){
		switch(currentPanel){
		case 4:  return trp.includeInactive();
		case 5:  return drp.includeInactive();
		default:  return false;
		}
	}
	
	public void printTransactionReport(){
		trp.printReport();
	}
	
	public void printDisparityReport(){
		drp.printReport();
	}
	
}
