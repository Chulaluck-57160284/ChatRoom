package ProjectChatRoom;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerAddress extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textIP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {	
		/*EventQueue.invokeLater คือ Thread เป็นตัว ควบคุมการเปิด Form นั้น ๆ 
		 * เพราะจะทำให้ Process ของ Program ทำงานได้อย่างมีประสิทธิภาพ และป้องกัน Process อื่นที่ค้างอยู่ 
		 * จะมีผลต่อการทำงานใน Form หรือ Windows ปัจจุบัน*/
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ServerAddress dialog = new ServerAddress(); //สร้างไดอาล็อก
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //สร้างปุ่มเปิดปิด
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					//ปริ้นerror 
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ServerAddress() {
		setBackground(new Color(255, 140, 0));
		setForeground(Color.ORANGE);
		setFont(new Font("Dialog", Font.PLAIN, 18));
		setTitle("Welcome to the Chat");
		setBounds(100, 100, 348, 182); //ตำเเหน่งของไดอาล็อก
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 228, 225));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblE = new JLabel("Enter IP Address of the Server:");
		lblE.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblE.setHorizontalAlignment(SwingConstants.CENTER);
		lblE.setBounds(24, 11, 275, 25);
		contentPanel.add(lblE);
		
		textIP = new JTextField();
		textIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChatClient sentIP = new ChatClient();
				sentIP.ip=textIP.getText();
			}
		});
		textIP.setBounds(34, 56, 251, 25);
		contentPanel.add(textIP);
		textIP.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 228, 225));
			buttonPane.setBounds(0, 92, 370, 51);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ChatClient sentIP = new ChatClient();
						sentIP.ip=textIP.getText();
					}
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
				okButton.setBounds(133, 0, 64, 35);
				okButton.setBackground(new Color(245, 245, 220));
				okButton.setForeground(new Color(0, 0, 0));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
