package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


public class Add extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldId;
	private JTextField textFieldMedname;
	private JTextField textFieldQuantity;
	private JTextField textFieldPresc;
	private JTextField textFieldDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add frame = new Add();
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
	public Add() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter item id:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 94, 194, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter Medicine Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 130, 194, 25);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Enter Quantity:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(10, 166, 194, 28);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("Enter Prescription Requirement:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(10, 205, 258, 29);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("Enter Expiry Date:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(10, 245, 194, 28);
		contentPane.add(lblNewLabel_3);
		
		textFieldId = new JTextField();
		textFieldId.setToolTipText("Enter integer only");
		textFieldId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldId.setBounds(347, 98, 194, 20);
		contentPane.add(textFieldId);
		textFieldId.setColumns(10);
		
		textFieldMedname = new JTextField();
		textFieldMedname.setToolTipText("Enter first letter Capital");
		textFieldMedname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldMedname.setColumns(10);
		textFieldMedname.setBounds(347, 134, 194, 20);
		contentPane.add(textFieldMedname);
		
		textFieldQuantity = new JTextField();
		textFieldQuantity.setToolTipText("Enter in integer only");
		textFieldQuantity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldQuantity.setColumns(10);
		textFieldQuantity.setBounds(347, 172, 194, 20);
		contentPane.add(textFieldQuantity);
		
		textFieldPresc = new JTextField();
		textFieldPresc.setToolTipText("Enter T/F");
		textFieldPresc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldPresc.setColumns(10);
		textFieldPresc.setBounds(347, 211, 194, 20);
		contentPane.add(textFieldPresc);
		
		textFieldDate = new JTextField();
		textFieldDate.setToolTipText("Enter in yyyy-mm-dd");
		textFieldDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldDate.setColumns(10);
		textFieldDate.setBounds(347, 251, 194, 20);
		contentPane.add(textFieldDate);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldId.setText("");
				textFieldMedname.setText("");
				textFieldQuantity.setText("");
				textFieldPresc.setText("");
				textFieldDate.setText("");
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnClear.setBounds(67, 405, 137, 29);
		contentPane.add(btnClear);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				java.awt.Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				Login l = new Login();
				
				if(l.val == 1) {
					Aoptions a = new Aoptions();
					a.setVisible(true);
				}
				else if(l.val == 2) {
					Uoptions u = new Uoptions();
					u.setVisible(true);
				}
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setBounds(258, 405, 137, 29);
		contentPane.add(btnBack);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
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
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalstore","root","9780");
					Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					String sqlCreate = "CREATE TABLE IF NOT EXISTS"+"`medicalstore`.`medicinedata` (" +
							  "`itemid` INT NOT NULL,"+
							  "`medicinename` VARCHAR(45) NOT NULL,"+
							  "`quantity` INT NOT NULL,"+
							  "`prescriptionreq` VARCHAR(45) NOT NULL,"+
							  "`expirydate` DATE NOT NULL,"+
							  "PRIMARY KEY (`itemid`));";
					stmt.execute(sqlCreate);
					
					if(textFieldId.getText().equals("") && textFieldMedname.getText().equals("") && textFieldQuantity.getText().equals("") && textFieldPresc.getText().equals("") && textFieldDate.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Fields Cannot Be Empty", "Warning!!!", JOptionPane.WARNING_MESSAGE);
					}
					else {
						PreparedStatement ps = conn.prepareStatement("INSERT INTO `medicalstore`.`medicinedata` (`itemid`, `medicinename`, `quantity`, `prescriptionreq`, `expirydate` ) VALUES (?,?,?,?,?)");
						
					 	ps.setInt(1,Integer.parseInt(textFieldId.getText()));
					    ps.setString(2,textFieldMedname.getText());
					    ps.setInt(3,Integer.parseInt(textFieldQuantity.getText()));
						ps.setString(4,textFieldPresc.getText());
			            ps.setString(5,textFieldDate.getText());
			            ps.executeUpdate(); 
			            JOptionPane.showMessageDialog(null,"Added Medicine Successfully!!!");
			            bw.write("Data added to database ID: " + textFieldId.getText() + " ,Medicine Name: " + textFieldMedname.getText() + " ,Quantity: " + textFieldQuantity.getText() + " ,Prescription Requirement: " + textFieldPresc.getText() + ", Expiry Date: "+ textFieldDate.getText() + " at Time: " + dtf.format(now) + "\n" );
			            bw.close();
					}
				}

				catch(Exception e1) {
					
					System.out.println(e1);
					JOptionPane.showMessageDialog(null,"Error:"+e);
					
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAdd.setBounds(436, 405, 137, 29);
		contentPane.add(btnAdd);
		
	}
}
