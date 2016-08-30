//------------------------------------------------------------------------------
//This is the main page that is viewed when the program launches.  It
//	consists of 3 buttons, top launches the pump, middle launches
//	the admin window, and the bottom closes the pump program
//
//------------------------------------------------------------------------------

import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends JPanel{
	private static final long serialVersionUID = 001;
	
	private JButton btnLaunchPump, btnLaunchAdmin, btnCloseProgram;
	
	public MainMenu() {
		setLayout(new BorderLayout());
		
		JPanel menuSubPanel = new JPanel();
		add(menuSubPanel, BorderLayout.CENTER);
		
		menuSubPanel.setLayout(new GridLayout(3,0));
		
		JPanel menuPumpPanel = new JPanel();
		menuSubPanel.add(menuPumpPanel);
		menuPumpPanel.setLayout(new BorderLayout(0, 0));
		
		btnLaunchPump = new JButton("Pump Gas");
		btnLaunchPump.setFont(new Font("Tahoma", Font.BOLD, 35));

		menuPumpPanel.add(btnLaunchPump);
		
		JPanel menuSubPanel_3 = new JPanel();
		menuPumpPanel.add(menuSubPanel_3, BorderLayout.WEST);
		
		JPanel menuSubPanel_4 = new JPanel();
		menuPumpPanel.add(menuSubPanel_4, BorderLayout.EAST);
		
		JPanel menuSubPanel_5 = new JPanel();
		menuPumpPanel.add(menuSubPanel_5, BorderLayout.SOUTH);
		
		JPanel menuSubPanel_6 = new JPanel();
		menuPumpPanel.add(menuSubPanel_6, BorderLayout.NORTH);

		//----------------------------------------------------
		//
		JPanel menuAdminPanel = new JPanel();
		menuSubPanel.add(menuAdminPanel);
		menuAdminPanel.setLayout(new BorderLayout(0, 0));
		
		btnLaunchAdmin = new JButton("Admin Functions");
		btnLaunchAdmin.setFont(new Font("Tahoma", Font.BOLD, 35));
				
	
		menuAdminPanel.add(btnLaunchAdmin);
		
		JPanel menuSubPanel_7 = new JPanel();
		menuAdminPanel.add(menuSubPanel_7, BorderLayout.NORTH);
		
		JPanel menuSubPanel_8 = new JPanel();
		menuAdminPanel.add(menuSubPanel_8, BorderLayout.WEST);
		
		JPanel menuSubPanel_9 = new JPanel();
		menuAdminPanel.add(menuSubPanel_9, BorderLayout.EAST);
		
		JPanel menuSubPanel_10 = new JPanel();
		menuAdminPanel.add(menuSubPanel_10, BorderLayout.SOUTH);

		
		//---------------------------------------------------
		//
		JPanel menuClosePanel = new JPanel();
		menuSubPanel.add(menuClosePanel);
		menuClosePanel.setLayout(new BorderLayout(0, 0));
		
		btnCloseProgram = new JButton("Close Program");
		btnCloseProgram.setFont(new Font("Tahoma", Font.BOLD, 35));
		
		menuClosePanel.add(btnCloseProgram);
		
		JPanel menuSubPanel_11 = new JPanel();
		menuClosePanel.add(menuSubPanel_11, BorderLayout.NORTH);
		
		JPanel menuSubPanel_12 = new JPanel();
		menuClosePanel.add(menuSubPanel_12, BorderLayout.WEST);
		
		JPanel menuSubPanel_13 = new JPanel();
		menuClosePanel.add(menuSubPanel_13, BorderLayout.EAST);
		
		JPanel menuSubPanel_14 = new JPanel();
		menuClosePanel.add(menuSubPanel_14, BorderLayout.SOUTH);
		
	}

	public void pumpActionListener(ActionListener pal){
		btnLaunchPump.addActionListener(pal);
	}
	
	public void adminActionListener(ActionListener aal){
		btnLaunchAdmin.addActionListener(aal);
	}
	
	public void closeActionListener(ActionListener cal){
		btnCloseProgram.addActionListener(cal);
	}
}
