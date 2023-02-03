package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.eclipse.jface.window.Window;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class Aoptions extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Aoptions frame = new Aoptions();
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
	public Aoptions() {
		setTitle("Admin Options");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				java.awt.Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				
				View v  = new View();
				v.setVisible(true);
			}
		});
		btnView.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnView.setBounds(32, 125, 172, 34);
		contentPane.add(btnView);
		
		JButton btnEdit = new JButton("Update");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				java.awt.Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				
				Update u = new Update();
				u.setVisible(true);
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnEdit.setBounds(238, 124, 172, 37);
		contentPane.add(btnEdit);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				java.awt.Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				
				Add a = new Add();
				a.setVisible(true);
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAdd.setBounds(442, 125, 172, 34);
		contentPane.add(btnAdd);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
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
			        String data = "\nLogged out at Time: " + dtf.format(now) + "\n";
			        bw.write(data);
			        bw.close();
					JComponent comp = (JComponent) e.getSource();
					java.awt.Window win = SwingUtilities.getWindowAncestor(comp);
					dispose();
					
					Login l = new Login();
					l.frmLogin.setVisible(true);
				}
				catch(Exception e1) {
					System.out.println(e1);
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogout.setBounds(259, 382, 124, 34);
		contentPane.add(btnLogout);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				java.awt.Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				
				Delete d = new Delete();
				d.setVisible(true);
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.setBounds(32, 185, 172, 34);
		contentPane.add(btnDelete);
		
		JButton btnLogo = new JButton("Create User");
		btnLogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				java.awt.Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				
				CreateUser c = new CreateUser();
				c.setVisible(true);
			}
		});
		btnLogo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogo.setBounds(238, 185, 172, 34);
		contentPane.add(btnLogo);
		
		JButton btnManageUsers = new JButton("Manage Users");
		btnManageUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				java.awt.Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				
				ManageUsers u = new ManageUsers();
				u.setVisible(true);
			}
		});
		btnManageUsers.setToolTipText("Manage Users");
		btnManageUsers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnManageUsers.setBounds(442, 185, 172, 34);
		contentPane.add(btnManageUsers);
		
		JButton btnReportslogs = new JButton("Reports/Logs");
		btnReportslogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec("notepad.exe D:\\Java Projects\\Medical Store Software\\log.txt");
				}
				catch(Exception e1) {
					System.out.println(e1);
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnReportslogs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnReportslogs.setBounds(238, 232, 172, 34);
		contentPane.add(btnReportslogs);
		
	}
}
