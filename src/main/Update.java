package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

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
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class Update extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textFieldNewname;
	private JTextField textFieldnewQuantity;
	private JTextField textFieldNewPresc;
	private JTextField textFieldnewDate;
	private JTable table;
	private JTextField textFieldItemid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Update frame = new Update();
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
	public Update() {
		setTitle("Update");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Item Id or Medicine Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 91, 245, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setToolTipText("Enter integer or string");
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setBounds(294, 88, 173, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane;
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 121, 624, 174);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ItemId", "Medicine Name", "Quantity", "Presciption Requirement", "Expiry Date"
			}
		));
		table.setBounds(305, 131, 1, 1);
		
		String[] column = {"Id", "Medicine Name"};
		JComboBox comboBox = new JComboBox(column);
		comboBox.setBounds(294, 43, 173, 22);
		contentPane.add(comboBox);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String get = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalstore","root","9780");
					Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					if(textField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Field Cannot be Empty", "Warning!!!", JOptionPane.WARNING_MESSAGE);
					}
					if(get.equals("Id")) {
						int id = Integer.parseInt(textField.getText());
						int r = 0;
						ResultSet rs = stmt.executeQuery("select * from medicalstore.medicinedata where itemid = " + id + ";" );
						DefaultTableModel dm = (DefaultTableModel)table.getModel();
						while(rs.next()) {
				        	
				        	dm.insertRow(r++,new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
				        }
					}
					else if(get.equals("Medicine Name")) {
						
						String name = textField.getText();
						int r = 0;
						int rec = 0;
						int count = 0;
						ResultSet rs = stmt.executeQuery("select * from medicalstore.medicinedata where medicinename = '" + name + "';" );
						DefaultTableModel dm = (DefaultTableModel)table.getModel();
						while(rs.next()) {
				        	
				        	dm.insertRow(r++,new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
				        	rec = 1;
				        	count ++;
				        }
						if(rec == 0) {
			        		JOptionPane.showMessageDialog(null,"Not any Medicine available by given Name","Dialog",JOptionPane.WARNING_MESSAGE);
			        	}
						if(count > 1) {
							JOptionPane.showMessageDialog(null,"More than "+count+" same medicines are present please specify using ItemId");
						}
						
					}
				}
					
				catch(Exception e1) {
					System.out.println(e1);
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSearch.setBounds(492, 87, 89, 23);
		contentPane.add(btnSearch);
		
		JLabel lblNewLabel_1 = new JLabel("Enter New Medicine Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 369, 199, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Enter Quantity");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(10, 394, 113, 20);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Enter Prescription Requirement ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(10, 425, 225, 20);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Enter Expiry Date");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(10, 456, 225, 20);
		contentPane.add(lblNewLabel_4);
		
		textFieldNewname = new JTextField();
		textFieldNewname.setToolTipText("Enter First letter capital");
		textFieldNewname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldNewname.setBounds(294, 366, 173, 20);
		contentPane.add(textFieldNewname);
		textFieldNewname.setColumns(10);
		
		textFieldnewQuantity = new JTextField();
		textFieldnewQuantity.setToolTipText("Enter Integer only");
		textFieldnewQuantity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldnewQuantity.setColumns(10);
		textFieldnewQuantity.setBounds(294, 394, 173, 20);
		contentPane.add(textFieldnewQuantity);
		
		textFieldNewPresc = new JTextField();
		textFieldNewPresc.setToolTipText("Enter T/F");
		textFieldNewPresc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldNewPresc.setColumns(10);
		textFieldNewPresc.setBounds(294, 425, 173, 20);
		contentPane.add(textFieldNewPresc);
		
		textFieldnewDate = new JTextField();
		textFieldnewDate.setToolTipText("Enter in yyyy-mm-dd");
		textFieldnewDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldnewDate.setColumns(10);
		textFieldnewDate.setBounds(294, 456, 173, 20);
		contentPane.add(textFieldnewDate);
		
		JLabel lblNewLabel_5 = new JLabel("Enter Update Details");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(10, 306, 199, 14);
		contentPane.add(lblNewLabel_5);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textFieldItemid.setText("");
				textFieldNewname.setText("");
				textFieldnewQuantity.setText("");
				textFieldNewPresc.setText("");
				textFieldnewDate.setText("");
				DefaultTableModel dm = (DefaultTableModel)table.getModel();
				while(dm.getRowCount() > 0)
				{
				    dm.removeRow(0);
				}
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnClear.setBounds(103, 499, 89, 23);
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
		btnBack.setBounds(253, 499, 89, 23);
		contentPane.add(btnBack);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
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
					String str1="UPDATE medicalstore.medicinedata SET "+
							"medicinename='"+textFieldNewname.getText()+
							"',quantity='"+textFieldnewQuantity.getText()+
					        "',prescriptionreq='"+textFieldNewPresc.getText()+
					        "',expirydate='"+textFieldnewDate.getText()+"'"+"where itemid='"+textFieldItemid.getText()+"'";
					if(textFieldItemid.getText().equals("") && textFieldNewname.getText().equals("") && textFieldnewQuantity.getText().equals("") && textFieldNewPresc.getText().equals("") && textFieldnewDate.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Fields Cannot be Empty", "Warning!!!", JOptionPane.WARNING_MESSAGE);
					}
					else {
						stmt.executeUpdate(str1);
				    	JOptionPane.showMessageDialog(null, "Record is updated");
				    	bw.write("Update operation performed on " + textFieldItemid.getText() + " at Time: " + dtf.format(now) +"\n");
				    	bw.write("Updated Data Medicine Name:" + textFieldNewname.getText() +" ,Quantity: " + textFieldnewQuantity.getText() + " ,Prescription Requirement: " + textFieldNewPresc.getText() + " ,Expiry Date:" + textFieldnewDate.getText() + "\n" );
					    bw.close();
					}
				}
				catch(Exception e1) {
					System.out.println(e1);
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUpdate.setBounds(401, 499, 89, 23);
		contentPane.add(btnUpdate);
		
		JLabel lblChooseItemId = new JLabel("Choose Item Id or Medicine Name");
		lblChooseItemId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChooseItemId.setBounds(10, 47, 245, 14);
		contentPane.add(lblChooseItemId);
		
		JLabel lblNewLabel_1_1 = new JLabel("Enter Item Id to be Edited");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(10, 344, 199, 14);
		contentPane.add(lblNewLabel_1_1);
		
		textFieldItemid = new JTextField();
		textFieldItemid.setToolTipText("Enter Integer Only\r\n");
		textFieldItemid.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldItemid.setColumns(10);
		textFieldItemid.setBounds(294, 335, 173, 20);
		contentPane.add(textFieldItemid);
	}

}
