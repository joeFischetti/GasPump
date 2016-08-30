//------------------------------------------------------------------------------
//This panel is used for viewing disparity reports
//
//------------------------------------------------------------------------------


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class DisparityReportPanel extends JPanel {
	private static final long serialVersionUID = 005;
	
	private JComboBox<String> monthSelection,
				dateSelection, yearSelection;
	private Font defaultFont;
	private JLabel memberLabel, monthLabel, dateLabel, yearLabel, inactiveLabel;
	private JButton btnSubmit, btnPrint;
	private JScrollPane responseScroll;
	private String[] tableColumns;
	private DefaultTableModel responseTableModel;
	private JTable responseTable;
	private JCheckBox includeInactive;
	
	public DisparityReportPanel(Font specifiedFont) {
		
		defaultFont = specifiedFont;
		
		//Initialize the dropdown list for the members list
		//
		defaultFont = specifiedFont;

		includeInactive = new JCheckBox();
		
		String[] months = new String[]{"ALL", "January", "February", "March", "April",
								"May", "June", "July", "August", "September",
								"October", "November", "December"};
								
		String[] dates = new String[]{"ALL", "1", "2", "3", "4", "5", "6", "7", "8",
								"9", "10", "11", "12", "13", "14", "15", "16",
								"17", "18", "19", "20", "21", "22", "23",
								"24", "25", "26", "27", "28", "29", "30", "31"};
		
		String[] years = new String[]{"ALL", "2016"};
		
		monthSelection = new JComboBox<String>(months);
		monthSelection.setFont(defaultFont);
		dateSelection = new JComboBox<String>(dates);
		dateSelection.setFont(defaultFont);
		yearSelection = new JComboBox<String>(years);
		yearSelection.setFont(defaultFont);
		
		//Init the JTextArea for the query response
		//
		tableColumns = new String[]{"Date","Time","Expected Reading","Actual Reading","Member (Expected)","Member (Actual)"};
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
		JPanel dataPanel = new JPanel(new GridLayout(4,2));
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
			responseTable.print();
			
		}
		catch(Exception e){
			
		}
	}
}