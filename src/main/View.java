package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class View extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldname;
	private JTable table;
	private JButton btnBack;
	private JLabel lblNewLabel_1;
	private JButton btnClear;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
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
	public View() {
		setTitle("View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JLabel lblNewLabel = new JLabel("Enter medicine for specific item or just press search to load the whole table");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(39, 11, 565, 28);
		contentPane.add(lblNewLabel);
		
		textFieldname = new JTextField();
		textFieldname.setToolTipText("Enter String Only");
		textFieldname.setBounds(172, 50, 235, 23);
		contentPane.add(textFieldname);
		textFieldname.setColumns(10);
		
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
					String name = textFieldname.getText();
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalstore","root","9780");
					Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					Vector rows = new Vector();
					if(textFieldname.getText().equals("")) {
						ResultSet rs = stmt.executeQuery("select * from medicalstore.medicinedata;");
						while(rs.next()) {
							Vector one_row = new Vector();
							one_row.add(rs.getString(1));
							one_row.add(rs.getString(2));
							one_row.add(rs.getString(3));
							one_row.add(rs.getString(4));
							one_row.add(rs.getString(5));
							rows.add(one_row);
						}
						DefaultTableModel dm = (DefaultTableModel)table.getModel();
						Iterator i = rows.iterator();
						int count = 0;
						while(i.hasNext()) {
							dm.insertRow(count, (Vector)i.next());
							count++;
						}
						bw.write("All Data viewed at Time: " + dtf.format(now) + "\n");
						bw.close();
					}
					else {
						int r = 0;
						int rec = 0;
						ResultSet rs = stmt.executeQuery("select * from medicalstore.medicinedata where medicinename = '" + name + "';" );
						DefaultTableModel dm = (DefaultTableModel)table.getModel();
						while(rs.next()) {
				        	
				        	dm.insertRow(r++,new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
				        	rec = 1;
				        }
						if(rec == 1) {
							bw.write("Searched for Medicine Name: " + name +" at Time: " + dtf.format(now) + "\n");
							bw.close();
						}
						else if(rec == 0) {
			        		JOptionPane.showMessageDialog(null,"Not any Medicine available by given Name","Dialog",JOptionPane.WARNING_MESSAGE);
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
		btnNewButton.setBounds(483, 50, 121, 23);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane;
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 103, 624, 174);
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
		
		btnBack = new JButton("Back");
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
		btnBack.setBounds(90, 399, 121, 23);
		contentPane.add(btnBack);
		
		lblNewLabel_1 = new JLabel("Medicine Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(27, 53, 135, 17);
		contentPane.add(lblNewLabel_1);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldname.setText("");
				DefaultTableModel dm = (DefaultTableModel)table.getModel();
				while(dm.getRowCount() > 0)
				{
				    dm.removeRow(0);
				}
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnClear.setBounds(440, 399, 121, 23);
		contentPane.add(btnClear);
	}
}
