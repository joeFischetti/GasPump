/**
 * AdminPanel
 * @author Joe Fischetti
 */


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

	//adminMemberList is used for the member list drop down, and the string[] and model
	//	are used for the combobox
	private JComboBox<String> adminMemberList;
	private String[] dropDownList;
	private DefaultComboBoxModel<String> dropDownModel;

	//JTextFields for each of the gas prices on the modify gas price panel
	private JTextField councilMemberPrice, lifeMemberPrice, memberPrice, guestPrice;

	//Default font used for formatting purposes (passed from main)
	private Font defaultFont;

	//Current member being worked with
	private ClubMember currentAdminMember;

	//Various panels used to display/work with certain information.  trp = transaction reports,
	//	drp = disparity reports.  The rest are MemberInfoPanels that show and/or allow certain fields
	//	to be modified.
	JPanel mainPanelCenter;
	JPanel pricingInfoPanel;
	private AdminReportsPanel trp;
	private DisparityReportPanel drp;
	private MemberInfoPanel addMemberPanel, deleteMemberPanel, modifyMemberPanel, passwordResetPanel;
	private MemberStatusPanel memberStatusPanel;


	//Each of the buttons that appear in the panel
	private JButton btnReturn, btnSubmit, btnTransactionReports, btnDisparityReports,
					btnAddUser, btnDeleteUser, btnModifyUser, btnShowPasswordReset, btnPricingLevels, btnAdditionalStatus;

	//Static strings used for cardlayout names
	private static String CREATEUSER = "Create User";  //Panel 0
	private static String DELETEUSER = 	"Delete User";  //Panel 1
	private static String MODIFYUSER = "Modify User";  //Panel 2
	private static String PRICINGLEVELS = "Pricing Levels";  //Panel 3
	private static String TRANSACTIONREPORT = "Transaction Report";  //Panel 4
	private static String DISPARITYREPORT = "Disparity Report";  //Panel 5
	private static String PASSWORDRESET = "Reset Member Password"; //Panel 6
	private static String ADDITIONALSTATUS = "More Member Statuses"; //Panel 7


	//currentPanel is used for tracking the current panel that's displayed
	private int currentPanel;

	//DecimalFormat used for showing money $X.XX
	private DecimalFormat money;


	/**
	 * This is the constructor for for the AdminPanel which displays each of the different
	 * 	panels used for admin purposes.
	 *
	 * @param specifiedFont	The default font that will be used throughout the panels
	 * @param dropDown	The array of member names that will be used for the drop downs
	 */
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

		passwordResetPanel = new MemberInfoPanel(defaultFont, dropDownList, "Reset Member Password");
		passwordResetPanel.enableEntryFields(false);
		passwordResetPanel.enableDropList(true);

		trp = new AdminReportsPanel(defaultFont, dropDownList);
		drp = new DisparityReportPanel(defaultFont);


		mainPanelCenter.add(addMemberPanel, CREATEUSER);
		mainPanelCenter.add(deleteMemberPanel, DELETEUSER);
		mainPanelCenter.add(modifyMemberPanel, MODIFYUSER);
		mainPanelCenter.add(trp, TRANSACTIONREPORT);
		mainPanelCenter.add(drp, DISPARITYREPORT);
		mainPanelCenter.add(pricingInfoPanel, PRICINGLEVELS);
		mainPanelCenter.add(passwordResetPanel, PASSWORDRESET);

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

		JLabel lblLifeMember = new JLabel("Club:  ");
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

		btnShowPasswordReset = new JButton("Reset Member Password");
		btnShowPasswordReset.setFont(defaultFont);

		btnAdditionalStatus = new JButton("Additional Member Status");
		btnAdditionalStatus.setFont(defaultFont);

		JPanel buttonPanel = new JPanel(new GridLayout(9,1));
		buttonPanel.add(btnSubmit);
		buttonPanel.add(btnTransactionReports);
		buttonPanel.add(btnDisparityReports);
		buttonPanel.add(btnAddUser);
		buttonPanel.add(btnDeleteUser);
		buttonPanel.add(btnModifyUser);
		buttonPanel.add(btnShowPasswordReset);
		buttonPanel.add(btnPricingLevels);
		buttonPanel.add(btnAdditionalStatus);

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

	public void showPasswordResetActionListener(ActionListener spral){
		btnShowPasswordReset.addActionListener(spral);
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

	public void passwordResetListActionListener(ActionListener prlal){
		passwordResetPanel.memberListActionListener(prlal);
	}

	public void additionalStatusActionListener(ActionListener asal){
		btnAdditionalStatus.addActionListener(asal);
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

			case 6: return passwordResetPanel.getSelectedName();

			default: return new String("No User Selected");
		}

	}

	public int getSelectedIndex(){
		switch(currentPanel){

			case 1:	return deleteMemberPanel.getSelectedIndex();

			case 2: return modifyMemberPanel.getSelectedIndex();

			case 6: return passwordResetPanel.getSelectedIndex();

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

	public void showPasswordResetPanel(){
		CardLayout cl = (CardLayout)(mainPanelCenter.getLayout());
		cl.show(mainPanelCenter, PASSWORDRESET);
		currentPanel = 6;
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


	public void updatePasswordResetPanel(){
		passwordResetPanel.setFirstNameFieldText(getSelectedMember().getFirstName());
		passwordResetPanel.setLastNameFieldText(getSelectedMember().getLastName());
		passwordResetPanel.setSpouseNameField(getSelectedMember().getSpouse());
		passwordResetPanel.setAddressField(getSelectedMember().getAddress());
		passwordResetPanel.setCityField(getSelectedMember().getCity());
		passwordResetPanel.setStateField(getSelectedMember().getState());
		passwordResetPanel.setZipField(getSelectedMember().getZip());
		passwordResetPanel.setPhoneField(getSelectedMember().getPhone());
		passwordResetPanel.setEmailField(getSelectedMember().getEmail());
		passwordResetPanel.setMemberStatus(getSelectedMember().getStatus());
		passwordResetPanel.setMemberSinceField(getSelectedMember().getMemberSince());

		passwordResetPanel.enableEntryFields(false);
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
