package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
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
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class Delete extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delete frame = new Delete();
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
	public Delete() {
		setTitle("Delete");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(344, 132, 221, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Enter Item Id or Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(30, 132, 184, 17);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane;
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 163, 624, 174);
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
		btnBack.setBounds(146, 439, 151, 23);
		contentPane.add(btnBack);
		
		String[] column = {"Id", "Medicine Name"};
		JComboBox comboBox = new JComboBox(column);
		comboBox.setBounds(344, 85, 221, 22);
		contentPane.add(comboBox);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
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
					String get = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalstore","root","9780");
					Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					if(textField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Field Cannot be Empty", "Warning!!!", JOptionPane.WARNING_MESSAGE);
					}
					else {
						if(get.equals("Id")) {
							
							int id = Integer.parseInt(textField.getText());
							int r = 0;
							ResultSet rs = stmt.executeQuery("select * from medicalstore.medicinedata where itemid = " + id + ";" );
							DefaultTableModel dm = (DefaultTableModel)table.getModel();
							while(rs.next()) {
					        	
					        	dm.insertRow(r++,new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
					        }
							PreparedStatement ps = conn.prepareStatement("delete from medicalstore.medicinedata where itemid="+ id);
							int val=ps.executeUpdate();
							if(val == 0) {
								JOptionPane.showMessageDialog(null,"Record not present.");
					        
							}
							else {
								JOptionPane.showMessageDialog(null,"Record is deleted.");
								bw.write("Record Deleted: " + textField.getText() + " at Time: " + dtf.format(now) + "\n");
								bw.close();
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
							else {
								PreparedStatement ps = conn.prepareStatement("delete from medicalstore.medicinedata where medicinename= '"+ name + "';" );
								int val=ps.executeUpdate();
								if(val == 0) {
									JOptionPane.showMessageDialog(null,"Record not present.");
						        
								}
								else {
									JOptionPane.showMessageDialog(null,"Record is deleted.");
									bw.write("Record Deleted Item id: " + textField.getText() + " at Time: " + dtf.format(now) + "\n");
									bw.close();
								}
							}
						}
					}
				}
				catch(Exception e1) {
					System.out.println(e1);
					
					JOptionPane.showMessageDialog(null, e1);
				}
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.setBounds(359, 439, 151, 23);
		contentPane.add(btnDelete);
		
		JLabel lblChooseFromWhere = new JLabel("Choose from where to delete");
		lblChooseFromWhere.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChooseFromWhere.setBounds(30, 89, 230, 17);
		contentPane.add(lblChooseFromWhere);
	}
}
