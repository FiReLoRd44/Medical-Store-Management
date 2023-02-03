package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
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

public class CreateUser extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateUser frame = new CreateUser();
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
	public CreateUser() {
		setTitle("Create User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(98, 92, 118, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(98, 144, 118, 25);
		contentPane.add(lblPassword);
		
		JLabel lblUserType = new JLabel("User Type");
		lblUserType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUserType.setBounds(98, 195, 118, 25);
		contentPane.add(lblUserType);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldUsername.setBounds(292, 96, 163, 21);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBounds(292, 148, 163, 21);
		contentPane.add(passwordField);
		
		String[] utype = {"admin", "user"};
		JComboBox comboBox = new JComboBox(utype);
		comboBox.setBounds(292, 198, 163, 22);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldUsername.setText("");
				passwordField.setText("");
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(98, 348, 118, 34);
		contentPane.add(btnNewButton);
		
		JButton btnBack = new JButton("Back");
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
		btnBack.setBounds(279, 348, 118, 34);
		contentPane.add(btnBack);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
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
					PreparedStatement ps = conn.prepareStatement("INSERT INTO `medicalstore`.`login` (`id`, `username`, `password`, `usertype`) VALUES (?,?,?,?)");
					ps.setInt(1, 0);
					ps.setString(2,textFieldUsername.getText());
				    ps.setString(3,passwordField.getText());
					ps.setString(4,(String) comboBox.getItemAt(comboBox.getSelectedIndex()));
		            ps.executeUpdate();
		            int reply=JOptionPane.showConfirmDialog(null,"User added successfully.Do you want add more Users?","Added User",JOptionPane.YES_NO_OPTION);
			
						if (reply == JOptionPane.YES_OPTION)
			   			{
				   		      new CreateUser();
				   		}
				   		else
				   		{
				   			  Aoptions a = new Aoptions();
				   			  a.setVisible(true);
				   			  dispose();
					    }
						bw.write("User Created:" + textFieldUsername.getText() + " Type:" + (String) comboBox.getItemAt(comboBox.getSelectedIndex()) + " at Time: " + dtf.format(now) + "\n");
						bw.close();
					}
					
					catch(Exception e1) {
						
						System.out.println(e1);
						JOptionPane.showMessageDialog(null,"Error:"+e);
						
					}
			}
			
		});
		btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCreate.setBounds(450, 348, 118, 34);
		contentPane.add(btnCreate);
	}
}
