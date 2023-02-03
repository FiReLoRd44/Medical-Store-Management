package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class ManageUsers extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUsersearch;
	private JTable table;
	private JLabel lblEnterUseridTo;
	private JTextField textFielduserIddel;
	private JButton btnDelete;
	private JButton btnClear;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageUsers frame = new ManageUsers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManageUsers() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File f1 = new File("log.txt");
			        if(!f1.exists()) {
			           f1.createNewFile();
			        }
			        FileWriter fileWritter = new FileWriter(f1.getName(),true);
			        BufferedWriter bw = new BufferedWriter(fileWritter);
			        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			        LocalDateTime now = LocalDateTime.now(); 
					String username = textFieldUsersearch.getText();
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalstore","root","9780");
					Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					Vector rows = new Vector();
					if(textFieldUsersearch.getText().equals("")) {
						ResultSet rs = stmt.executeQuery("select * from medicalstore.login;");
						while(rs.next()) {
							Vector one_row = new Vector();
							one_row.add(rs.getString(1));
							one_row.add(rs.getString(2));
							one_row.add(rs.getString(3));
							one_row.add(rs.getString(4));
							rows.add(one_row);
						}
						DefaultTableModel dm = (DefaultTableModel)table.getModel();
						Iterator i = rows.iterator();
						int count = 0;
						while(i.hasNext()) {
							dm.insertRow(count, (Vector)i.next());
							count++;
						}
						bw.write("All users Searched at Time: " + dtf.format(now) + "\n");
						bw.close();
					}
					else {
						int r = 0;
						int rec = 0;
						ResultSet rs = stmt.executeQuery("select * from medicalstore.login where username = '" + username + "';" );
						DefaultTableModel dm = (DefaultTableModel)table.getModel();
						while(rs.next()) {
				        	
				        	dm.insertRow(r++,new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
				        	rec = 1;
				        }
						if(rec == 1) {
							bw.write("User Searched: " + username +" At Time: " + dtf.format(now) + "\n");
							bw.close();
						}
						else if(rec == 0) {
			        		JOptionPane.showMessageDialog(null,"Not any user available by given username","Dialog",JOptionPane.WARNING_MESSAGE);
			        	}
						
					}
				}
				catch(Exception e1) {
					System.out.println(e1);
					JOptionPane.showMessageDialog(null,"Error:"+e1);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(477, 15, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Enter Username to search");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(60, 17, 215, 19);
		contentPane.add(lblNewLabel);
		
		textFieldUsersearch = new JTextField();
		textFieldUsersearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldUsersearch.setBounds(277, 15, 160, 22);
		contentPane.add(textFieldUsersearch);
		textFieldUsersearch.setColumns(10);
		
		JScrollPane scrollPane;
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 103, 602, 174);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Username", "Password", "UserType"
			}
		));
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(table);
		table.setBounds(277, 90, 1, 1);
		
		lblEnterUseridTo = new JLabel("Enter User Id to delete");
		lblEnterUseridTo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterUseridTo.setBounds(60, 324, 215, 19);
		contentPane.add(lblEnterUseridTo);
		
		textFielduserIddel = new JTextField();
		textFielduserIddel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFielduserIddel.setColumns(10);
		textFielduserIddel.setBounds(277, 321, 160, 22);
		contentPane.add(textFielduserIddel);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = textFieldUsersearch.getText();
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalstore","root","9780");
					PreparedStatement ps = conn.prepareStatement("delete from medicalstore.login where id="+textFielduserIddel.getText());
					int val = ps.executeUpdate();
					if(val == 0) {
						JOptionPane.showMessageDialog(null,"User not present.");
			        
					}
					else {
						JOptionPane.showMessageDialog(null,"User "+ textFielduserIddel.getText() +" is deleted.");
					}
				}
				catch(Exception e1) {
					System.out.println(e1);
					JOptionPane.showMessageDialog(null,"Error:"+e1);
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.setBounds(477, 320, 89, 23);
		contentPane.add(btnDelete);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldUsersearch.setText("");
				textFielduserIddel.setText("");
				DefaultTableModel dm = (DefaultTableModel)table.getModel();
				while(dm.getRowCount() > 0)
				{
				    dm.removeRow(0);
				}
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnClear.setBounds(60, 448, 89, 23);
		contentPane.add(btnClear);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				java.awt.Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				
				Aoptions a = new Aoptions();
				a.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setBounds(477, 450, 89, 23);
		contentPane.add(btnBack);
	}
}
