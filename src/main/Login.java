package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Choice;
import javax.swing.JComboBox;

public class Login implements MouseListener {

	
	JFrame frmLogin;
	JComboBox comboBox;
	JButton btnCancel;
	String getType = null;
	static int val;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public String gettype() {
		getType = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
		return getType;
	}
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 660, 572);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(148, 156, 104, 20);
		frmLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(148, 213, 104, 20);
		frmLogin.getContentPane().add(lblNewLabel_1);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldUsername.setBounds(310, 156, 161, 20);
		frmLogin.getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBounds(310, 215, 161, 20);
		frmLogin.getContentPane().add(passwordField);
		
		String[] utype = {"admin", "user"};
		comboBox = new JComboBox(utype);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox.setBounds(310, 272, 161, 22);
		frmLogin.getContentPane().add(comboBox);
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = textFieldUsername.getText();
					String password = passwordField.getText();
					String get = gettype();
					File f1 = new File("log.txt");
			        if(!f1.exists()) {
			           f1.createNewFile();
			        }
			        FileWriter fileWritter = new FileWriter(f1.getName(),true);
			        BufferedWriter bw = new BufferedWriter(fileWritter);
			        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			        LocalDateTime now = LocalDateTime.now(); 
			        String data = "\n\nLoggged in id: "+ textFieldUsername.getText()+"\t Time: "+dtf.format(now)+"\n"; 
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalstore","root","toor");
					Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					String sql = "SELECT * FROM medicalstore.login WHERE username = '"+ username + "' and password = '"+ password +"' and usertype = '"+ get +"';";
					ResultSet rs  = stmt.executeQuery(sql);
					if(rs.next()) {
						JOptionPane.showMessageDialog(frmLogin, "Login Successfull");
						if(get.equals("admin")) {
							Aoptions a = new Aoptions();
							a.setVisible(true);
							val = 1;
							frmLogin.dispose();
							bw.write("*************************\n");
						    bw.write(data);
						    bw.close();
						}
						else if(get.equals("user")){
							Uoptions u = new Uoptions();
							u.setVisible(true);
							val = 2;
							frmLogin.dispose();
							bw.write("*************************\n");
							bw.write(data);
							bw.close();
						}
					}
					else {
						JOptionPane.showMessageDialog(frmLogin, "Incorrect Login Details", "Warning!!!", JOptionPane.WARNING_MESSAGE);
					}
					
				}
				catch(Exception e1) {
					System.out.println(e1);
					
					JOptionPane.showMessageDialog(frmLogin, e1);
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setBounds(148, 347, 133, 42);
		frmLogin.getContentPane().add(btnLogin);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCancel.setBounds(367, 347, 126, 42);
		btnCancel.addMouseListener(this);
		frmLogin.getContentPane().add(btnCancel);
		
		JLabel lblUserType = new JLabel("User Type");
		lblUserType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUserType.setBounds(148, 271, 104, 20);
		frmLogin.getContentPane().add(lblUserType);
		
		JLabel lblMedicalStoreManagement = new JLabel("MEDICAL STORE MANAGEMENT SYSTEM");
		lblMedicalStoreManagement.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMedicalStoreManagement.setBounds(148, 67, 378, 42);
		frmLogin.getContentPane().add(lblMedicalStoreManagement);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == btnCancel) {
			frmLogin.dispose();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
