package ProjectChatRoom;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.Dialog.ModalExclusionType;

public class ChatRoom extends JFrame {
	static ChatClient sentChat=new ChatClient();
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea ChatArea;
	public JTextArea getUserArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatRoom frame = new ChatRoom();
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
	public ChatRoom() {
		setBackground(new Color(0, 0, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 489, 410);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\User\\Pictures\\Chat\\header.PNG"));
		label.setBounds(0, 0, 473, 75);
		contentPane.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(-3, 77, 387, 187);
		contentPane.add(scrollPane);
		
		ChatArea = new JTextArea();
		scrollPane.setViewportView(ChatArea);
		
		getUserArea = new JTextArea();
		getUserArea.setBounds(394, 77, 79, 294);
		contentPane.add(getUserArea);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\Users\\User\\Pictures\\Chat\\pic1.PNG"));
		label_1.setBounds(0, 282, 384, 28);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=textField.getText()+"\n";
				
				if(!text.equals("")){
				sentChat.getChat(text);
		   		textField.setCaretPosition(textField.getText().length() - 1);
		   		//System.out.println(text);
		   		textField.setText("");
				}
			}
		});
		textField.setBounds(0, 310, 384, 61);
		contentPane.add(textField);
		textField.setColumns(10);
	}

	public void apen(String line) {
		// TODO Auto-generated method stub
		//String text=line+"\n";
		//ChatArea.append(text);
		//ChatArea.append(line.substring(8) + "\n");
		ChatArea.append("  "+line+ "\n");
		ChatArea.setCaretPosition(ChatArea.getText().length() - 1);	    
	}
	public void apen2(String line) {
		// TODO Auto-generated method stub
		getUserArea.append(line+ "\n");
		//getUserArea.setCaretPosition(ChatArea.getText().length() - 1);	
	}
}
