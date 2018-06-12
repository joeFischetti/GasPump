//------------------------------------------------------------------------------
//This Panel is used for viewing transaction reports
//
//------------------------------------------------------------------------------


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.awt.Font;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JCheckBox;


public class AdminReportsPanel extends JPanel {
	private static final long serialVersionUID = 004;
	
	private JComboBox<String> memberList, monthSelection,
				dateSelection, yearSelection;	
	private String[] dropDownList, tableColumns;
	private DefaultComboBoxModel<String> dropDownModel;
	private Font defaultFont;
	private JLabel memberLabel, monthLabel, dateLabel, yearLabel, inactiveLabel;
	private JButton btnSubmit, btnPrint;
	private JScrollPane responseScroll;
	private DefaultTableModel responseTableModel;
	private JTable responseTable;
	private JCheckBox includeInactive;
	
	public AdminReportsPanel(Font specifiedFont, String[] dropDown) {
		
		defaultFont = specifiedFont;
		
		//Initialize the dropdown list for the members list
		//
		dropDownList = dropDown;
		defaultFont = specifiedFont;
		dropDownModel = new DefaultComboBoxModel<String>(dropDownList);
		memberList = new JComboBox<String>();
		memberList.setFont(defaultFont);
		memberList.setModel(dropDownModel);
		
		
		includeInactive = new JCheckBox();
		
		String[] months = new String[]{"ALL", "January", "February", "March", "April",
								"May", "June", "July", "August", "September",
								"October", "November", "December"};
								
		String[] dates = new String[]{"ALL", "1", "2", "3", "4", "5", "6", "7", "8",
								"9", "10", "11", "12", "13", "14", "15", "16",
								"17", "18", "19", "20", "21", "22", "23",
								"24", "25", "26", "27", "28", "29", "30", "31"};
		
		String[] years = new String[]{"ALL", "2016", "2017", "2018"};
		
		monthSelection = new JComboBox<String>(months);
		monthSelection.setFont(defaultFont);
		dateSelection = new JComboBox<String>(dates);
		dateSelection.setFont(defaultFont);
		yearSelection = new JComboBox<String>(years);
		yearSelection.setFont(defaultFont);
		
		//Init the JTextArea for the query response
		//
		tableColumns = new String[]{"Transaction","Date","Time","Member","Amount Pumped","Price","Total"};
		responseTable = new JTable();
		responseTableModel = (DefaultTableModel)responseTable.getModel();
		responseTableModel.setColumnIdentifiers(tableColumns);
		responseScroll = new JScrollPane(responseTable);
		
		
		//Init main panel buttons
		//
		btnSubmit = new JButton("Submit query");
		btnSubmit.setFont(defaultFont);
		btnPrint = new JButton("Print");
		btnPrint.setFont(defaultFont);
		
		
		//Init all JPanels
		//
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel returnButtonPanel = new JPanel();
		JPanel dataPanel = new JPanel(new GridLayout(5,2));
		JPanel bodyPanel = new JPanel(new GridLayout(2,1));
		JPanel buttonPanel = new JPanel();

		memberLabel = new JLabel("Select a member");
		memberLabel.setFont(defaultFont);
		monthLabel = new JLabel("Select a month");
		monthLabel.setFont(defaultFont);
		dateLabel = new JLabel("Select a date");
		dateLabel.setFont(defaultFont);
		yearLabel = new JLabel("Select a year");
		yearLabel.setFont(defaultFont);
		inactiveLabel = new JLabel("Include deleted members?");
		inactiveLabel.setFont(defaultFont);
		
		//Add all the components to the correct panels
		//
		
		dataPanel.add(memberLabel);
		dataPanel.add(memberList);
		dataPanel.add(monthLabel);
		dataPanel.add(monthSelection);
		dataPanel.add(dateLabel);
		dataPanel.add(dateSelection);
		dataPanel.add(yearLabel);
		dataPanel.add(yearSelection);
		dataPanel.add(inactiveLabel);
		dataPanel.add(includeInactive);
		
		buttonPanel.add(btnSubmit);
		buttonPanel.add(btnPrint);

		bodyPanel.add(dataPanel);
		bodyPanel.add(buttonPanel);
		
		mainPanel.add(returnButtonPanel, BorderLayout.NORTH);
		mainPanel.add(bodyPanel, BorderLayout.CENTER);
		mainPanel.add(responseScroll, BorderLayout.SOUTH);
		
		add(mainPanel);
		
			
	}
	
	public void submitActionListener(ActionListener sal){
		btnSubmit.addActionListener(sal);
	}
	
	public void printActionListener(ActionListener pal){
		btnPrint.addActionListener(pal);
	}

	
	public void setDropList(String[] input){
		dropDownList = input;
		dropDownModel.removeAllElements();
		for(String name : input){
			dropDownModel.addElement(name);
		}	
	}
	
	public String getSelectedMember(){
		return (String)memberList.getSelectedItem();
	}
	
	public int getSelectedMonth(){
		return monthSelection.getSelectedIndex();
	}
	
	public int getSelectedDate(){
		return dateSelection.getSelectedIndex();
	}
	
	public int getSelectedYear(){
		return yearSelection.getSelectedIndex();
	}
	
	public void clearResults(){
		responseTableModel.setRowCount(0);
	}
	
	public void displayResults(String[] input){
		responseTableModel.addRow(input);
	}
	
	public boolean includeInactive(){
		return includeInactive.isSelected();
	}
	
	public void printReport(){
		try{
			
			MessageFormat header = new MessageFormat(" ");
			MessageFormat footer = new MessageFormat(" ");
			
			PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
	        set.add(OrientationRequested.PORTRAIT);
			
			responseTable.print(JTable.PrintMode.FIT_WIDTH, header, footer, false, null, false);
			
		}
		catch(Exception e){
			
		}
	}
}
