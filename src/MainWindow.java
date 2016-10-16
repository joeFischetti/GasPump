//------------------------------------------------------------------------------
//This is the controller for the gas pump.  All the logic
//	for the various cards is controlled in this window
//
//------------------------------------------------------------------------------


import jssc.*;
import java.awt.CardLayout;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import java.awt.event.*;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.*;
import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.MessageFormat;



public class MainWindow extends JFrame{
	private static final long serialVersionUID = 003;
	
	//The serial/comm port used for pump communication
	//
	private SerialPort pumpRelay;
	
	//Panel that holds the cardLayout
	//
	private JPanel pumpApplication;
	
	
	//Default font used throughout the program
	//
	private Font defaultFont;
	
	
	//Static strings for accessing panels and setting the administrator password
	//
	private static String MAINMENU = "Main Menu";
	private static String ADMINPANEL = "Administrator View";
	private static String GASPUMPPANEL = "Gas Pump";
	private static String ADMINPASSWORD = "admin";
	private static String SELECTMEMBERPANEL = "Select Member";
	
	
	//The main panels
	//
	private AdminPanel adminPanel;
	private MainMenu mainMenu;
	private PumpPanel pumpPanel;
	private SelectMemberPanel memberPanel;
		
	
	//Arraylist of club members, and the array that gets sent to the panels for the drop downs
	//
	private ArrayList<ClubMember> club;
	private String[] dropDownList;

	private DecimalFormat money, meter;
	
	private Transaction pumpGas;
	
	public MainWindow() {
		
		setSize(1268,1024);
		setTitle("Gas Pump");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Init the pump transaction
		//
		pumpGas = new Transaction();		
		
		//Init the serial port for the pump
		//
		pumpRelay = new SerialPort("COM3");
		
		
		//Set default font
		//
		defaultFont = new Font("Tahoma", Font.BOLD, 20);
		
		
		//Create new arraylist with type ClubMember
		//
		club = new ArrayList<ClubMember>();

				
		//Load members from the database list into the arrayList, and load the arrayList into the string array
		//
		loadMembersFromDB();
		dropDownList = prepareDropBox(club);
		
		
		//Create the main display page, add actionlisteners to each of the buttons
		//
		mainMenu = new MainMenu();
		mainMenu.pumpActionListener(new MainPumpButton());
		mainMenu.adminActionListener(new MainAdminButton());
		mainMenu.closeActionListener(new CloseProgramButton());

		
		//Create the administrators panel, passing to it the default font,
		//	members arrayList, and string array.  Add actionlisteners to each of the buttons
		//
		adminPanel = new AdminPanel(defaultFont, dropDownList);
		adminPanel.returnActionListener(new ReturnButton());
		adminPanel.submitActionListener(new AdminSubmitButton());
		adminPanel.reportsActionListener(new ShowTransactionPanelButton());
		adminPanel.queryTransactionActionListener(new TransactionQueryButton());
		adminPanel.printTransactionActionListener(new PrintTransactionReportButton());
		adminPanel.disparityReportActionListener(new ShowDisparityPanelButton());
		adminPanel.queryDisparityActionListener(new DisparityQueryButton());
		adminPanel.printDisparityActionListener(new PrintDisparityReportButton());
		adminPanel.addUserActionListener(new ShowAddMemberPanelButton());
		adminPanel.deleteUserActionListener(new ShowDeleteMemberPanelButton());
		adminPanel.modifyUserActionListener(new ShowModifyMemberPanelButton());
		adminPanel.editPricingActionListener(new ShowPricingPanelButton());
		adminPanel.modifyMemberListActionListener(new ModifyUserDropDown());
		adminPanel.deleteMemberListActionListener(new DeleteUserDropDown());
		adminPanel.showPasswordResetActionListener(new ShowPasswordResetPanelButton());
		adminPanel.passwordResetListActionListener(new PasswordResetDropDown());
		
		
		//Create the pump panel, passing to it the default font,
		//	members arrayList, and string array.  Add actionListeners for the buttons
		//
		pumpPanel = new PumpPanel(defaultFont);
		pumpPanel.startActionListener(new StartPumpButton());
		pumpPanel.stopActionListener(new StopPumpButton());
		pumpPanel.returnActionListener(new ReturnButton());
		

		//Create the member selection panel, passing to it the default font,
		//	members arraylist.  Add actionListeners for the buttons
		//
		memberPanel = new SelectMemberPanel(defaultFont, dropDownList);
		memberPanel.connectActionListener(new PumpConnectButton());
		memberPanel.connectGuestActionListener(new PumpConnectGuestButton());
		memberPanel.returnActionListener(new ReturnButton());

	
		
		//Create the Main CardLayout, adding to it the 3 main panels
		//
		pumpApplication = new JPanel(new CardLayout());
		pumpApplication.add(mainMenu, MAINMENU);
		pumpApplication.add(adminPanel, ADMINPANEL);
		pumpApplication.add(pumpPanel, GASPUMPPANEL);
		pumpApplication.add(memberPanel, SELECTMEMBERPANEL);
		
		
		//Add the main display panel to this frame,
		//	make it visible
		//
		add(pumpApplication);
		
		money = new DecimalFormat("$#.##");
		money.setMinimumFractionDigits(2);
		money.setParseBigDecimal(true);
		
		meter = new DecimalFormat("#.#");
		meter.setParseBigDecimal(true);
		
		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
		
		setResizable(false);
		setUndecorated(true);
		gDev.setFullScreenWindow(this);
	}


	//Load the members from the database into the 'club' arraylist for
	//	for use in the drop down panels.
	//
	private void loadMembersFromDB(){
		//Clear out the arrayList
		//
		club.clear();
		
		
		//Add the default			
		club.add(new ClubMember("Select a member", "", "xxxx"));
		
		//Connection and statement variables for database access
		//
		Connection c = null;
		Statement stmt = null;
		
		//try-catch block for connecting to sqlite db and pulling member list/id numbers from database
		//
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
			
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM members ORDER BY last_name;");
			
			
			while(rs.next()){
				if(rs.getString("active").equals("1")){
					club.add(
						new ClubMember(rs.getString("first_name"), 
										rs.getString("last_name"), 
										rs.getString("id")));
			
				}
			}
			
			rs.close();
			stmt.close();
			c.close();
		} 
		//catch exception, and if-so, load test values
		//
		catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
			//Add a few test values to the arrayList
			//
			club.add(new ClubMember("Error", "Loading Data", "1234"));


		}
		    
		
	}
	

	//Create a string array that will be used in each of the drop down boxes
	//
	private String[] prepareDropBox(ArrayList<ClubMember> club){
		//Initialize members string array with the size of the arraylist
		//
		String[] members = new String[club.size()];
		
		
		//Add all the names from the arraylist to the members array string
		// for use in the drop down
		int i = 0;
		for (ClubMember member : club){
			members[i] = member.getFullName();
			i++;
		}
		
		return members;	
	}
	
	
	//Update Member with full info from database
	//
	private ClubMember loadFullMember(ClubMember input){
		ClubMember member = input;
		
		Connection c = null;
		Statement stmt = null;
				
		//try-catch block for connecting to sqlite db and pulling member list/id numbers from database
		//
		
		if(!(member.getFirstName().equals("Select a member")))	
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
			stmt = c.createStatement();
	
			ResultSet rs = stmt.executeQuery("SELECT first_name, last_name, id, status, password, price, spouse_name, address, city, state, zip, phone, member_since, email FROM members, status_codes WHERE members.status_code = status_codes.code AND id = " + member.getID() + ";");
			while(rs.next()){
				member.setStatus(rs.getString("status"));
				member.setPassword(rs.getString("password"));
				member.setSpouse(rs.getString("spouse_name"));
				member.setEmail(rs.getString("email"));
				member.setAddress(rs.getString("address"));
				member.setCity(rs.getString("city"));
				member.setState(rs.getString("state"));
				member.setZip(rs.getString("zip"));
				member.setPhone(rs.getString("phone"));
				member.setPrice((BigDecimal)(money.parse((rs.getString("price")))));
				member.setMemberSince(rs.getString("member_since"));
			}	
			
			//System.out.println(member.toString());
			rs.close();
			stmt.close();
			c.close();
		} 
				
		//catch exception
		//
		catch ( Exception ex ) {
			System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
			System.exit(0);
		
		}
		
		
		return member;
		
	}


	//Get gas pricing information from the database, and load it into the admin panel
	//
	private void getPricingInfo(){
		
		Connection c = null;
		Statement stmt = null;
				
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
			stmt = c.createStatement();
	
			ResultSet rs = stmt.executeQuery("SELECT code, price FROM status_codes;");
			while(rs.next()){
				switch(rs.getString("code")){
				case "1":	adminPanel.setCouncilMemberPrice(rs.getString("price"));
							break;
				case "2":	adminPanel.setMemberPrice(rs.getString("price"));
							break;
				case "3":	adminPanel.setGuestPrice(rs.getString("price"));
							break;
				case "4":	adminPanel.setLifeMemberPrice(rs.getString("price"));
							break;
							
				}
				
				
			}	
			
			//System.out.println(member.toString());
			rs.close();
			stmt.close();
			c.close();
		} 
				
		//catch exception
		//
		catch ( Exception ex ) {
			System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
			System.exit(0);
		
		}
	
		
	}


	//Set pricing information
	//
	private void setPricingInfo(){
			
			Connection c = null;
			Statement stmt = null;
			
			BigDecimal councilMemberPrice, memberPrice, probationPrice, lifeMemberPrice;
			
			try{
				if(!adminPanel.getCouncilMemberPrice().contains("$")){
					councilMemberPrice = (BigDecimal)money.parse("$" + adminPanel.getCouncilMemberPrice());	
				}
				
				else{
					councilMemberPrice = (BigDecimal)money.parse(adminPanel.getCouncilMemberPrice());
				}
				
				if(!adminPanel.getMemberPrice().contains("$")){
					memberPrice = (BigDecimal)money.parse("$" + adminPanel.getMemberPrice());
				}
				
				else{
					memberPrice = (BigDecimal)money.parse(adminPanel.getMemberPrice());
				}
				
				if(!adminPanel.getProbationPrice().contains("$")){
					probationPrice = (BigDecimal)money.parse("$" + adminPanel.getProbationPrice());
				}
				
				else{
					probationPrice = (BigDecimal)money.parse(adminPanel.getProbationPrice());
				}
				
				if(!adminPanel.getLifeMemberPrice().contains("$")){
					lifeMemberPrice = (BigDecimal)money.parse("$" + adminPanel.getLifeMemberPrice());
				}
				
				else{
					lifeMemberPrice = (BigDecimal)money.parse(adminPanel.getLifeMemberPrice());
				}
					
			
					
			//try-catch block for connecting to sqlite db and pulling member list/id numbers from database
			//
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
				stmt = c.createStatement();				
				
				stmt.executeUpdate("UPDATE status_codes SET price = '"+
						money.format(councilMemberPrice) + "' WHERE code = '1';" + 
						"\nUPDATE status_codes SET price = '" +
						money.format(memberPrice) + "' WHERE code = '2';" +
						"\nUPDATE status_codes SET price = '" +
						money.format(probationPrice) + "' WHERE code = '3';" + 
						"\nUPDATE status_codes SET price = '" + 
						money.format(lifeMemberPrice) + "' WHERE code = '4';");
				
			
				
				
			//System.out.println(member.toString());
			stmt.close();				
			c.close(); 
				
			}
			//catch exception
			//
			catch ( Exception ex ) {
				System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
				System.exit(0);
			
			}
	
			
		}


	//Prompt for the password of the current user
	//
	private boolean passwordPrompt(ClubMember input){
		ClubMember currentUser = input;
		
		currentUser = loadFullMember(currentUser);
		
		//Connection and statement variables for database access
		//
		Connection c = null;
		Statement stmt = null;
				
	
		//If a default or invalid password was detected, prompt the user
		//  to enter a new password, as well as the administrator code
		//
		if(currentUser.getPassword().length() < 25){
			
			JPanel defaultPassPopup = new JPanel(new GridLayout(4,1));
			JPanel row1 = new JPanel(new GridLayout(1,2));
			JPanel row2 = new JPanel(new GridLayout(1,2));
			JPanel row3 = new JPanel(new GridLayout(1,2));
			JPasswordField defaultPassEntry = new JPasswordField(25);
			JPasswordField defaultPassReEntry = new JPasswordField(25);
			JPasswordField adminPasswordEntry = new JPasswordField(25);
			
			defaultPassPopup.add(new JLabel("Your password is not valid or has been reset, please create a new password"));
			row1.add(new JLabel("Enter your new password"));
			row1.add(defaultPassEntry);
			row2.add(new JLabel("Please Re-enter your new password"));
			row2.add(defaultPassReEntry);
			row3.add(new JLabel("Please enter the admin password"));
			row3.add(adminPasswordEntry);
			defaultPassPopup.add(row1);
			defaultPassPopup.add(row2);
			defaultPassPopup.add(row3);
			
			//Array with optional buttons for the popup
			//
			String[] createNewPassOptions = new String[]{"Ok", "Cancel"};
			
			
			//boolean for looping password reset window
			//
			boolean loopForNewPass = true;
			
			
			//int that follows the selection (ok or cancel)
			//
			int createNewPassOption = 0;
			
			
			//char arrays for the values in the password entry boxes
			//
			char[] pass = defaultPassEntry.getPassword();
			char[] pass2 = defaultPassReEntry.getPassword();
			char[] adminPass = adminPasswordEntry.getPassword();
			
			
			//while a valid password has not been entered and the password entry boxes are not empty
			//
			while(loopForNewPass &&
					defaultPassEntry != null &&
					defaultPassReEntry != null){
				
				
				//display popup
				//
				createNewPassOption = JOptionPane.showInternalOptionDialog(getContentPane(),  defaultPassPopup,  "Reset password",
					JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE,
					null, createNewPassOptions, createNewPassOptions[0]);
				
				
				//Set the char arrays to equal the values in the password entry boxes
				//
				pass = defaultPassEntry.getPassword();
				pass2 = defaultPassReEntry.getPassword();
				adminPass = adminPasswordEntry.getPassword();
				
				
				//if user selected cancel, or the passwords are equal and the admin password is accepted
				//	break the while loop
				if(createNewPassOption == 1 ||
						(new String(pass).equals(new String(pass2))
								&& new String(adminPass).equals(ADMINPASSWORD))){
					break;
				}
			}
			
			
			//If user selected OK
			//
			if(createNewPassOption == 0){
						
				//Hash the user's password, and set the currentUser password to that value
				//	and store the new hashed password in the database
				//
				try {
					currentUser.setPassword(PasswordHash.createHash(new String(pass)));

					Class.forName("org.sqlite.JDBC");
					c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
					stmt = c.createStatement();
					System.out.println(currentUser.getID());
					stmt.executeUpdate("UPDATE members SET password=\"" + currentUser.getPassword() 
												+ "\" WHERE id = "
												+ currentUser.getID()
												+ ";");
					
					stmt.close();
					c.close();
				} 
						
				//catch exception
				//
				catch ( Exception ex ) {
					System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
					System.exit(0);
				
				}

				
				
				//Check the password, and return result
				//
				return currentUser.checkPass(new String(pass));
			}
			
			else
				return false;
		}
		
		
		//If user has a valid password stored in the database
		//
		else{
			//Prompt for password entry
			JPanel popup = new JPanel();
			JLabel prompt = new JLabel("Please enter your password");
			JPasswordField passwordEntry = new JPasswordField(25);
				
			popup.add(prompt);
			popup.add(passwordEntry);
				
			String[] options = new String[]{"Ok", "Cancel"};
			
			int option = JOptionPane.showInternalOptionDialog(getContentPane(), popup, "Enter your password",
											JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
											null, options, options[0]);
			if(option == 0)
			{
				char[] pass = passwordEntry.getPassword();
				return currentUser.checkPass(new String(pass));
						
			}
			
			else
				return false;
		}
		
	}
	

	//Prompt for an admin password
	//
	private boolean promptForAdminPassword(){
		JPanel popup = new JPanel();
		JLabel prompt = new JLabel("Please enter administrative password");
		JPasswordField passwordEntry = new JPasswordField(25);
				
		popup.add(prompt);
		popup.add(passwordEntry);
				
		String[] options = new String[]{"Ok", "Cancel"};
				
		int option = JOptionPane.showInternalOptionDialog(getContentPane(), popup, "Enter admin password",
											JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
											null, options, options[0]);
		
		String adminPass = new String(passwordEntry.getPassword());
		
		if(option == 0 && adminPass.equals(ADMINPASSWORD)){
			return true;
		}
		
		else
			return false;
	}


	//Add new member to the database
	//
	private void addNewMember(){
	
		int statusIndicator = matchMemberType(adminPanel.getMemberStatus());
		
		//	Connection and statement variables for database access
		//
		Connection c = null;
		Statement stmt = null;
			
		//try-catch block for connecting to sqlite db and inserting new member database
		//
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
			stmt = c.createStatement();
	
			stmt.executeUpdate("INSERT INTO members (first_name, last_name, status_code, password, spouse_name, address, city, state, zip, phone, email, member_since) VALUES ('"
						+ adminPanel.getFirstNameField() + "', '"
						+ adminPanel.getLastNameField() + "', "
						+ statusIndicator + ", '"
						+ "defaultPassword" + "', '"
						+ adminPanel.getSpouseNameField() + "', '"
						+ adminPanel.getAddressField() + "', '"
						+ adminPanel.getCityField() + "', '"
						+ adminPanel.getStateField() + "', '"
						+ adminPanel.getZipField() + "', '"
						+ adminPanel.getPhoneField() + "', '"
						+ adminPanel.getEmailField() + "', '"
						+ (new SimpleDateFormat("MM/dd/yyyy").format(new Date())) + "');");
		
			stmt.close();
			c.close();
		} 
			
		//catch exception
		//
		catch ( Exception ex ) {
			System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
			System.exit(0);
	
		}
		
	}


	//Remove member from the database
	//
	private void removeMember(){
		//		Connection and statement variables for database access
		//
		Connection c = null;
		Statement stmt = null;
			
		//try-catch block for connecting to sqlite db and inserting new member database
		//	
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
			
			
			
			String remove = new String("UPDATE members SET active = '0' WHERE id=" +
					adminPanel.getSelectedMember().getID() + ";");
	
			
			stmt = c.createStatement();
			
			stmt.executeUpdate(remove);
		
			
			stmt.close();
			c.close();
		} 
			
		//catch exception
		//
		catch ( Exception ex ) {
			System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
			System.exit(0);
	
		}
	}


	//Load the member database for the selected member with the new information
	// entered on the admin panel
	//
	private void updateMemberInformation(){
		//Connection and statement variables for database access
		//
		Connection c = null;
		Statement stmt = null;
	
		//try-catch block for connecting to sqlite db and inserting new member database
		//
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
					
			String update = new String("UPDATE members SET "
							+ " first_name = \"" + adminPanel.getFirstNameField() + "\","
							+ " last_name = \"" + adminPanel.getLastNameField() + "\","
							+ " spouse_name = \"" + adminPanel.getSpouseNameField() + "\","
							+ " address = \"" + adminPanel.getAddressField() + "\","
							+ " city = \"" + adminPanel.getCityField() + "\","
							+ " state = \"" + adminPanel.getStateField() + "\","
							+ " zip = \"" + adminPanel.getZipField() + "\","
							+ " email = \"" + adminPanel.getEmailField() + "\","
							+ " phone = \"" + adminPanel.getPhoneField() + "\","
							+ " status_code = \"" + matchMemberType(adminPanel.getMemberStatus()) + "\""
							+ " WHERE id="
							+ adminPanel.getSelectedMember().getID()
							+ ";");
			
			stmt = c.createStatement();
		
			stmt.executeUpdate(update);
	
			stmt.close();
			c.close();
		} 
	
		//catch exception
		//
		catch ( Exception ex ) {
			System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
			System.exit(0);
	
		}
		
	}


	//Reset member password
	//
	private void resetPassword(){
		//Connection and statement variables for database access
		//
		Connection c = null;
		Statement stmt = null;
	
		//try-catch block for connecting to sqlite db and inserting new member database
		//
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
			
			String update = new String("UPDATE members SET password=\"defaultPassword\" "
							+ " WHERE id="
							+ adminPanel.getSelectedMember().getID()
							+ ";");
	
			stmt = c.createStatement();
	
			stmt.executeUpdate(update);
	
			stmt.close();
			c.close();
		} 
	
		//catch exception
		//
		catch ( Exception ex ) {
			System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
			System.exit(0);
	
		}
	}


	//Set all the pump fields to match the currently selected member
	//
	private void updatePumpDisplay(){
		pumpPanel.setNameField(pumpPanel.getSelectedMember().getFullName());
		pumpPanel.setStatusField(pumpPanel.getSelectedMember().getStatus());
		pumpPanel.setPricePerGalField(money.format(pumpPanel.getSelectedMember().getPrice()));
		pumpPanel.setAddressField(pumpPanel.getSelectedMember().getAddress());
		pumpPanel.setCityField(pumpPanel.getSelectedMember().getCity());
		pumpPanel.setStateField(pumpPanel.getSelectedMember().getState());
		pumpPanel.setZipField(pumpPanel.getSelectedMember().getZip());
		pumpPanel.setPhoneField(pumpPanel.getSelectedMember().getPhone());
		pumpPanel.setEmailField(pumpPanel.getSelectedMember().getEmail());
		pumpPanel.setMemberSinceField(pumpPanel.getSelectedMember().getMemberSince());
		pumpPanel.setGallonsPumped(new String());
		pumpPanel.setTotalPrice(new String());
	}
	
	
	//Set all the member selection fields to match the curently selected member
	//
	private void updateSelectMemberPanel(){
		memberPanel.setMemberNumber(memberPanel.getSelectedMember().getID());
	}
	
	//Display an error window, with string input to describe whats wrong
	//
	private void displayError(String errorType){
		JPanel popup = new JPanel();
		JLabel message = new JLabel(errorType);
				
		popup.add(message);
				
		String[] options = new String[]{"Ok"};
				
		JOptionPane.showInternalOptionDialog(getContentPane(), popup, "Error",
			JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
			null, options, options[0]);
	}

	
	//Display confirmation window confirming changes to the database
	//  Use string to display detailed message
	//
	private boolean confirmDBUpdate(String input){
		JPanel popup = new JPanel();
		JLabel prompt = new JLabel("Are you sure you want to " + input + "?");
				
		popup.add(prompt);
				
		String[] options = new String[]{"Ok", "Cancel"};
				
		int option = JOptionPane.showInternalOptionDialog(getContentPane(), popup, input,
			JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
			null, options, options[0]);
		
		
		if(option == 0){
			return true;
		}
		
		else
			return false;
	}
	
	private void setAdminPanelMember(){
		//for each member in club, compare the currently selected full name to 
		// the member.  When a match is found, set the current member in the adminPanel
		// to the selected member
		
		for(ClubMember member : club){
			if(adminPanel.getSelectedName().equals(member.getFullName())){
				adminPanel.setCurrentMember(member);
			break;
			}
		}
	}
	
	
	//Send transaction data to the database transactions table
	//
	private void storeTransaction(Transaction input){
	
		//Connection and statement variables for database access
		//
		Connection c = null;
		Statement stmt = null;
	
	
		//try-catch block for connecting to sqlite db and retrieving
		//	the most recent transaction end value
		//
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
			stmt = c.createStatement();
	
			
			stmt.executeUpdate(
				"INSERT INTO transactions (member_id, date, time, price, pump_start, pump_end, num_gallons, price_paid) VALUES ('"
				+ input.getMemberID() + "', '"
				+ input.getDate() + "', '"
				+ input.getTime() + "', '"
				+ money.format(input.getPrice()) + "', '"
				+ meter.format(input.getPumpStart()) + "', '"
				+ meter.format(input.getPumpEnd()) + "', '"
				+ meter.format(input.getNumberGallons()) + "', '"
				+ money.format(input.getTransactionTotal()) + "');");
	
			ResultSet rs = stmt.executeQuery("SELECT first_name, last_name, date, time, trans_num, num_gallons, price, price_paid FROM members, transactions WHERE member_id = id AND trans_num = (SELECT MAX(trans_num) FROM transactions);");
			
	
			JTextArea receipt = new JTextArea();
	
			while(rs.next()){	
				receipt.setText("\n\nTransaction:  " + rs.getString("trans_num") + "		"
					+ rs.getString("date") + " " + rs.getString("time")
					+ "\n\n	" + "SAUGERTIES POWER BOAT ASSOCIATION GAS TICKET"
					+ "\n\n\n\n\n	" + "Member Name : " + rs.getString("first_name") + " " + rs.getString("last_name")
					+ "\n\n		Number of gallons: " + rs.getString("num_gallons")
					+ "\n		Price per gallon: " + rs.getString("price")
					+ "\n		Total Due:  " + rs.getString("price_paid"));
			}
			
			MessageFormat header = new MessageFormat(" ");
			MessageFormat footer = new MessageFormat(" ");
			
			PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
	        set.add(OrientationRequested.LANDSCAPE);
			
			receipt.print(header, footer, false, null, set, false);
			
			rs.close();
			stmt.close();
			c.close();
			
		} 
	
		//catch exception
		//
		catch ( Exception ex ) {
			System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
			System.exit(0);
	
		}
	
	}


	//Send transaction data to the database transactions table
	//
	private void logMeterDisparity(String meterReading, ClubMember currentMember){
	
		//Connection and statement variables for database access
		//
		Connection c = null;
		Statement stmt = null;
	
		//try-catch block for connecting to sqlite db and retrieving
		//	the most recent transaction end value, as well as 
		//	entering disparity into the meter_disparity table
		//
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
			stmt = c.createStatement();
	
			ResultSet rs = stmt.executeQuery(
				"SELECT member_id, pump_end FROM transactions WHERE trans_num = (SELECT MAX(trans_num) FROM transactions);");
			
						
			stmt.executeUpdate(
				"INSERT INTO meter_disparity (date, time, expected_reading, actual_reading, expected_member, actual_member) VALUES ('"
				+ new SimpleDateFormat("MM/dd/yyyy").format(new Date()) + "', '"
				+ new SimpleDateFormat("HH:mm:ss").format(new Date()) + "', '"
				+ rs.getString("pump_end") + "', '"
				+ meter.format((BigDecimal)meter.parse(meterReading)) + "', '"
				+ rs.getString("member_id") + "', '"
				+ currentMember.getID() + "');");
	
			
			rs.close();
			stmt.close();
			c.close();
			
		} 
		
		//catch exception
		//
		catch ( Exception ex ) {
			System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
			System.exit(0);
	
		}
	}


	//Get most recent pump reading from the transactions table
	//
	private String queryForPumpReading(){
		String returnValue = new String();
		
		//Connection and statement variables for database access
		//
		Connection c = null;
		Statement stmt = null;
	
		//try-catch block for connecting to sqlite db and retrieving
		//	the most recent transaction end value
		//
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
			stmt = c.createStatement();
			
			ResultSet rs = stmt.executeQuery(
				"SELECT first_name, last_name, pump_end FROM transactions, members WHERE member_id = id AND trans_num = (SELECT MAX(trans_num) FROM transactions);");
			
			/*--- Diag
			System.out.println(rs.getString("first_name") + " " +
				rs.getString("last_name") + " said the pump reading is " +
				rs.getString("pump_end"));
			---*/
			
			while(rs.next()){
				returnValue = rs.getString("pump_end");
			}
			
			rs.close();
			stmt.close();
			c.close();
			
		} 
	
		//catch exception
		//
		catch ( Exception ex ) {
			System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
			System.exit(0);
	
		}
	
		return returnValue;
	}


	//Match supplied string to a member type as stored
	// in the database
	//
	private int matchMemberType(String memberType){
		
		switch(memberType){
		case "Mohawk Council Member":	return 1;
										
		case "Life Member":				return 4;
										
		case "Member":					return 2;
										
		case "Guest":				return 3;
										
		default:	displayError("Didn't select member type, adding as 'Guest'");
								return 3;
			
		}
	}


	//Action performed on launch pump button from main menu
	//
	class MainPumpButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			CardLayout cl = (CardLayout)(pumpApplication.getLayout());
			cl.show(pumpApplication, SELECTMEMBERPANEL);
		}
	}
	
	
	
	//Action performed on launch admin button from main menu
	//
	class MainAdminButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(promptForAdminPassword()){
				CardLayout cl = (CardLayout)(pumpApplication.getLayout());
				cl.show(pumpApplication,  ADMINPANEL);
				getPricingInfo();
			}
			
			else
				displayError("The password you entered is incorrect");
		}
	}
	
	
	//Action performed on close program button from
	// main menu
	//
	class CloseProgramButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(promptForAdminPassword()){
				System.exit(0);
			}
		}
	}
	
	
	//Action performed on admin 'submit' button 
	//
	
	//Action performed when a return button is pressed.  In general, reset all fields/options
	// that are currently enable/filled in, so they aren't saved for the next person
	// to view
	//
	class ReturnButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//Reset the currently selected member from the pump panel
			// and update the display (to clear the fields)
			pumpPanel.resetMember();
			updatePumpDisplay();
			
			
			//Reset the member for the member selection panel
			// and reset the fields
			//
			memberPanel.resetMember();
			updateSelectMemberPanel();
			
			
			//Reset the member for the admin panel, and update the displays
			//
			adminPanel.resetMember();
			adminPanel.updateModifyPanel();
			adminPanel.updateDeletePanel();
			
			
			//Reload the dropdown list in case members were added/removed
			//
			loadMembersFromDB();
			dropDownList = prepareDropBox(club);
			memberPanel.setDropList(dropDownList);
			adminPanel.setDropList(dropDownList);
	
			
			CardLayout cl = (CardLayout)(pumpApplication.getLayout());
			cl.show(pumpApplication, MAINMENU);
		}
	}



	class AdminSubmitButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			
			if(promptForAdminPassword()){
				
				int option = adminPanel.getCurrentPanel();
				
				
				//Switch for optionSelected drop down menu
				//  0 - create new member
				//  1 - remove a member
				//  2 - modify an existing member
				//  3 - Update pricing levels
				//  
				switch(option){
					case 0:		//Prompt for confirmation
								if(confirmDBUpdate("Create new member")){
									addNewMember();
								}
								break;
				
					case 1:		//Prompt for confirmation
								if(confirmDBUpdate("delete " + adminPanel.getSelectedMember().getFullName() 
												+ " from the database")){
									removeMember();
								}
								break;
				
					case 2:		//Prompt for confirmation
								if(confirmDBUpdate("modify member info for " + adminPanel.getSelectedMember().getFullName()))
									updateMemberInformation();
								break;
				
					case 3:		//Prompt for confirmation
								if(confirmDBUpdate("update all pricing values")){
									setPricingInfo();
								}
								break;
					
					case 4:		displayError("No changes entered");
								break;
								
					case 5:		displayError("No changes entered");
								break;
					
					case 6:		if(confirmDBUpdate("Reset member password for " + adminPanel.getSelectedMember().getFullName()))
									resetPassword();
								break;
					
				}
			}
			

			else
				displayError("The admin password you entered is incorrect");
			
			
			loadMembersFromDB();
			dropDownList = prepareDropBox(club);
			adminPanel.setDropList(dropDownList);
			
			
		}
		
	}
	

	//Show transaction reports panel
	//
	class ShowTransactionPanelButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			adminPanel.showTransReportPanel();
		}
	}



	class ShowDisparityPanelButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			adminPanel.showDisparityReportPanel();
		}
	}



	//Show add member panel
	//
	class ShowAddMemberPanelButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			adminPanel.showAddMemberPanel();
		}
	}
	
	
	//Show delete member panel
	//
	class ShowDeleteMemberPanelButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			adminPanel.showDeleteMemberPanel();
		}
	}
	
	
	//Show modify member panel
	//
	class ShowModifyMemberPanelButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			adminPanel.showModifyMemberPanel();
		}
	}
	
	
	//Show reset member password panel
	//
	class ShowPasswordResetPanelButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			adminPanel.showPasswordResetPanel();
		}
	}
	
	class ShowPricingPanelButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			adminPanel.showPricingPanel();
		}
	}



	//Submit new transaciton report query
	//
	class TransactionQueryButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//Clear the display window
			//
			adminPanel.clearTransactionResults();
			
			
			//Integer variables are the indexes of the drop downs
			//	String variables are what will get passed to the database
			//
			int monthIndex, dateIndex, yearIndex;
			String month, date, year;
			
			//Get the name of the person that was selected from the drop down
			//
			String memberSelected = new String(adminPanel.getTransactionSearchMember());
			
			
			//Get the INDEX value of each of the drop downs
			//
			monthIndex = adminPanel.getTransactionSearchMonth();
			dateIndex = adminPanel.getTransactionSearchDate();
			yearIndex = adminPanel.getTransactionSearchYear();
			
			
			//If/else statements set the strings accordingly.  [underscore] is used
			//	in a database query as a wildcard, and since date formatting is MM/dd/yyyy, 
			//	each single digit entries need to be padded.  If the selected index is
			//	0, the string is saved as two or four [underscores].  If the index is greater
			//	than 0, pad it to the correct lenght, and save the string.  For the year
			//	2015 is added to create the correct year.
			//
			if(monthIndex == 0)
				month = "__";
			
			else
				month = String.format("%02d", monthIndex);
			
			if(dateIndex == 0)
				date = "__";
			
			else
				date = String.format("%02d", dateIndex);
			
			if(yearIndex == 0)
				year = "____";
			
			else
				year = String.format("%04d", (yearIndex + 2015));
		
			int includeInactive = 1;
			
			if(adminPanel.includeInactive()){
				includeInactive = 0;
			}
			
			//Connection and statement variables for database access
			//
			Connection c = null;
			Statement stmt = null;

			//try-catch block for connecting to sqlite db and retrieving
			//	the most recent transaction end value
			//
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
				stmt = c.createStatement();
				
				ResultSet rs;
				
				//If no specific member is selected, then do a generic 
				// query based on the dates
				//
				if((memberSelected.equals("Select a member "))){
					rs = stmt.executeQuery(
							"SELECT trans_num, date, time, first_name, last_name, num_gallons, price, price_paid, active FROM transactions, members WHERE id = member_id AND date LIKE '" 
									+ month + "/" + date + "/" + year + "' AND (active = '" + includeInactive + "' OR active = '1');");
					
				}
				
				//Else, if a member is selected, include the first/last name in the
				// query, using the whitespace as the separator between first and last name
				//
				else{
					rs = stmt.executeQuery(
							"SELECT trans_num, date, time, first_name, last_name, num_gallons, price, price_paid FROM transactions, members WHERE id = member_id AND date LIKE '" 
									+ month + "/" + date + "/" + year 
									+ "' AND first_name LIKE '" + memberSelected.split(" ")[0] 
									+ "' AND last_name LIKE '" + memberSelected.split(" ")[1] + "';");
				}
					
				while(rs.next()){
					
					adminPanel.displayTransactionResults(new String[]{rs.getString("trans_num"),
							rs.getString("date"),rs.getString("time"),
							rs.getString("first_name") + " " + rs.getString("last_name"),
							rs.getString("num_gallons"),rs.getString("price"),rs.getString("price_paid")});

				}

				
				rs.close();
				stmt.close();
				c.close();
				
			} 	

			//catch exception
			//
			catch ( Exception ex ) {
				System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
				System.exit(0);
			
			}
			
		}
	}
	
	
	
	class DisparityQueryButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//Clear the display window
			//
			adminPanel.clearDisparityResults();
			
			
			//Integer variables are the indexes of the drop downs
			//	String variables are what will get passed to the database
			//
			int monthIndex, dateIndex, yearIndex;
			String month, date, year;
			
			
			//Get the INDEX value of each of the drop downs
			//
			monthIndex = adminPanel.getDisparitySearchMonth();
			dateIndex = adminPanel.getDisparitySearchDate();
			yearIndex = adminPanel.getDisparitySearchYear();
			
			
			//If/else statements set the strings accordingly.  [underscore] is used
			//	in a database query as a wildcard, and since date formatting is MM/dd/yyyy, 
			//	each single digit entries need to be padded.  If the selected index is
			//	0, the string is saved as two or four [underscores].  If the index is greater
			//	than 0, pad it to the correct lenght, and save the string.  For the year
			//	2015 is added to create the correct year.
			//
			if(monthIndex == 0)
				month = "__";
			
			else
				month = String.format("%02d", monthIndex);
			
			if(dateIndex == 0)
				date = "__";
			
			else
				date = String.format("%02d", dateIndex);
			
			if(yearIndex == 0)
				year = "____";
			
			else
				year = String.format("%04d", (yearIndex + 2015));
		
			
			int includeInactive = 1;
			
			if(adminPanel.includeInactive()){
				includeInactive = 0;
			}
			
			//Connection and statement variables for database access
			//
			Connection c = null;
			Statement stmt = null;

			//try-catch block for connecting to sqlite db and retrieving
			//	the most recent transaction end value
			//
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:BoatClub");
				stmt = c.createStatement();
				
				ResultSet rs;
				
				rs = stmt.executeQuery(
							"SELECT md.date, md.time, expected_reading, actual_reading, expectedReading.last_name AS 'expected_last', actualReading.last_name AS 'actual_last' FROM meter_disparity md, members expectedReading, members actualReading WHERE (md.expected_member = expectedReading.id AND md.actual_member = actualReading.id) AND (date LIKE '" 
									+ month + "/" + date + "/" + year + "') AND (expectedReading.active = " + includeInactive + " OR expectedReading.active = 1) AND  (actualReading.active = " + includeInactive + " OR actualReading.active = 1);");
					
					
				while(rs.next()){
					
					adminPanel.displayDisparityResults(new String[]{rs.getString("date"),
							rs.getString("time"),rs.getString("expected_reading"),
							rs.getString("actual_reading"), rs.getString("expected_last"),
							rs.getString("actual_last")});

				}

				
				rs.close();
				stmt.close();
				c.close();
				
			} 	

			//catch exception
			//
			catch ( Exception ex ) {
				System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
				System.exit(0);
			
			}
			
		}
	}
	
	
	//Print report query from reports page
	//
	class PrintTransactionReportButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			adminPanel.printTransactionReport();
		}
	}
	
	
	//Print the disparity report from the disparity panel
	//
	class PrintDisparityReportButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			adminPanel.printDisparityReport();
		}
	}

	
	//ActionListener for the drop down box on the modify member
	//	panel.  If a user is actually selected, set the adminPanelMember to the member
	//	that's listed in the drop down.  Then load the full member info from the
	//	database into the adminPanel member.  Then update the modifyPanel
	//
	class ModifyUserDropDown implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setAdminPanelMember();
			
			adminPanel.setCurrentMember(loadFullMember(adminPanel.getSelectedMember()));
			adminPanel.updateModifyPanel();
		}
	}

	
	//ActionListener for the drop down box on the delete member
	//	panel.  If a user is actually selected, set the adminPanelMember to the member
	//	that's listed in the drop down.  Then load the full member info from the
	//	database into the adminPanel member.  Then update the deletePanel
	//
	class DeleteUserDropDown implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setAdminPanelMember();
			
			adminPanel.setCurrentMember(loadFullMember(adminPanel.getSelectedMember()));
			adminPanel.updateDeletePanel();
		}
	}
	
	
	class PasswordResetDropDown implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setAdminPanelMember();
			
			adminPanel.setCurrentMember(loadFullMember(adminPanel.getSelectedMember()));
			adminPanel.updatePasswordResetPanel();
		}
	}
	
	
	//Start the pump
	//	Connect to the RS232 port to throw the pump relay,
	//	Pole the database for the last pump reading, display
	//	a popup window that promps the user for the current pump
	//	reading (and if it's different, prompt for the new
	//	pump reading).  If the pump values were different, 
	//	save a database entry with the two transaction codes
	//
	class StartPumpButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			String pumpStartingValue;
			boolean checkInput = false;
				
			try{
			//Turn on the pump
			//
			pumpRelay.openPort();
				
			//Create a new transaction
			//	Date = current date
			//	Time = current time
			//	memberID = current member on pump page
			//	price = current member's rate
			//
			pumpGas = new Transaction();
			pumpGas.setDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
			pumpGas.setTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			pumpGas.setMemberID(pumpPanel.getSelectedMember().getID());
			pumpGas.setPrice(pumpPanel.getSelectedMember().getPrice());
				
				
			//Check what the current meter reading should
			//	be
			//
			pumpStartingValue = queryForPumpReading();
				
				
			//String array for the popup window
			//
			String[] options = new String[]{"Yes", "No"};
				
				
			//Popup window.  Prompts user for the current
			//	meter reading.  Asks the user
			//	if the current actual reading matches
			//	the reading from the last time the
			//	pump was used
			//
			int option = JOptionPane.showInternalOptionDialog(getContentPane(), 
				"The current meter reading should be " +
				pumpStartingValue + ".  Is this correct?",
				"Confirm Meter", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
				
				
				
				
				//If the user said the meter reading was incorrect
				//	ask them to answer the correct reading
				//	and then log the error in the database
				//
				if(option == 1){
					
					do{
						
						
						pumpStartingValue = JOptionPane.showInternalInputDialog(
							getContentPane(),
							"Please enter the meter reading",
							"Confirm Meter",
							JOptionPane.PLAIN_MESSAGE,
							null,
							null,
							pumpStartingValue).toString();
						
						//Make sure the input is a valid float
						//
						try{
							Float.parseFloat(pumpStartingValue);
							checkInput = true;
						}
					
						//if the input was not a valid float, set the
						//	boolean to false so the prompt
						//	comes up again
						//
						catch(NumberFormatException nfe)
						{
							checkInput = false;
						}
						
					
					}while(!checkInput);
					
					
					logMeterDisparity(pumpStartingValue, pumpPanel.getSelectedMember());
				}
				
				//Set the transaction's pump starting value
				//
				pumpGas.setPumpStart(pumpStartingValue);
			
			}
		
			catch(SerialPortException serialException){
				System.out.println(serialException);
			}
		}
	}

	
	//Pump Stop
	class StopPumpButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
				
			try{
				//Turn off the pump
				//
				pumpRelay.closePort();
			
				String pumpEndingValue = Integer.toString(-1);
				
				//While ending value is less than the starting value
				//
				while(Float.parseFloat(pumpEndingValue) < Float.parseFloat(pumpGas.getPumpStart().toString())){
					
					//Prompt user for current meter reading
					//
					pumpEndingValue = JOptionPane.showInternalInputDialog(
							getContentPane(),
							"Please enter the meter reading",
							"Confirm Meter",
							JOptionPane.PLAIN_MESSAGE,
							null,
							null,
							pumpGas.getPumpStart()).toString();
					
					//Make sure the entered value is a valid float
					//	and if not (exception is thrown)
					//	then set it to -1 so the prompt comes
					//	up again
					//
					try{
						Float.parseFloat(pumpEndingValue);
					}
					
					catch(NumberFormatException nfe){
						pumpEndingValue = "-1";
					}
					
				}
				
				//Set the transaction details
				//	Pump ending value, number of gallons
				//	pumped, and total price paid
				//
				pumpGas.setPumpEnd(pumpEndingValue);				
				pumpGas.setNumGallons();			
				pumpGas.setPricePaid();
					
				
				//Store the transaction in the database
				//
				storeTransaction(pumpGas);
				
				
				//Display the amount due
				//
				JOptionPane.showInternalMessageDialog(getContentPane(), new String("Number of gallons pumped: " +
					pumpGas.getNumberGallons() + "\n" +
					"Your total amount due: " + 
					money.format(pumpGas.getTransactionTotal())));
				
				
				//Update the pump panel display
				//
				pumpPanel.setGallonsPumped(pumpGas.getNumberGallons().toString());
				pumpPanel.setTotalPrice(money.format(pumpGas.getTransactionTotal()));
			}
			
			catch(SerialPortException serialException){
				System.out.println(serialException);
			}
				
		}
	}
	
	//Connect to the pump with membership credentials
	//
	class PumpConnectButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			//Read values from the input boxes, and save to strings
			//
			String memberSelected = new String(memberPanel.getCurrentlySelected());
			String memberNumberEntered = new String(memberPanel.getMemberNumber());
			

			//Login successful boolean check
			//
			boolean loginSuccess = false;
			
			
			//Check if the member numbered entered was even a number,
			//  and if not, clear the variable
			//
			try{
				Integer.parseInt(memberNumberEntered);
			}
			catch(NumberFormatException nfe){
				memberNumberEntered = "";
				memberPanel.setMemberNumber("");
			}
			
			
			//If nothing was entered in the member box, and nothing was selected,
			// prompt the user for input.
			//
			if((memberNumberEntered.equals("")) && (memberSelected.equals("Select a member "))){
				displayError("Please select a member or enter a valid ID number");
			}
			
			
			//else if a member was selected but a number wasn't entered
			//
			else if(memberNumberEntered.equals("") && (!(memberSelected.equals("Select a member")))){
				
				//for each member in the arraylist, check the name selected
				//  if a match is found, set the currentUser to that member
				//  and break the loop
				for(ClubMember member : club){
					if(memberSelected.equals(member.getFullName())){
						pumpPanel.setCurrentUser(member);
						break;
					}
				}
				
				//update the membernumber field to reflect the user that was selected
				//
				memberPanel.setMemberNumber(pumpPanel.getSelectedMember().getID());
				
				
				//Prompt the user for the password and attempt to log in
				//
				loginSuccess = passwordPrompt(pumpPanel.getSelectedMember());
			}
			
			
			
			else{ //if memberNumber was entered, match it to a member
				
				//for each member in the club arraylist, check the membernumber
				//  entered.  if a match is found, set the currentUser to that
				//  member and break the loop
				//
				for(ClubMember member : club){
					if(memberNumberEntered.equals(member.getID())){
						pumpPanel.setCurrentUser(member);
						break;
					}
				}
				
				
				//prompt the user for the password and attempt to log in
				//
				loginSuccess = passwordPrompt(pumpPanel.getSelectedMember());
			}
					
			
			//If the login was successful (matched password)
			//  populate the name field, and the member status field, 
			//				
			if(loginSuccess){
				CardLayout cl = (CardLayout)(pumpApplication.getLayout());				
				cl.show(pumpApplication,  GASPUMPPANEL);
				pumpPanel.setMemberNumber(pumpPanel.getSelectedMember().getID());
				updatePumpDisplay();
			}
			
			else{
				pumpPanel.resetMember();
				updatePumpDisplay();
			}
			
		}
	}

	
	//Connect to the pump as a guest
	//
	class PumpConnectGuestButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			
			pumpPanel.selectGuestMember();
		
				
			for(ClubMember member : club){
				if(member.getFullName().equals("GUEST USER")){
					pumpPanel.setCurrentUser(member);
					break;
				}
			}
			
			pumpPanel.setCurrentUser(loadFullMember(pumpPanel.getSelectedMember()));
			updatePumpDisplay();
		}
	}
}
