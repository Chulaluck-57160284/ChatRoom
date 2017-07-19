package ProjectChatRoom;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Window.Type;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Name extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField Nametext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Name dialog = new Name();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});	
	}

	/**
	 * Create the dialog.
	 */
	public Name() {
		setFont(new Font("Dialog", Font.PLAIN, 18));
		setTitle("Screen name selection");
		setBounds(100, 100, 348, 171);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(245, 245, 220));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 220));
			buttonPane.setBounds(10, 78, 374, 54);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ChatClient sentName=new ChatClient();
						sentName.username = Nametext.getText();
					}
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
				okButton.setBounds(120, 11, 65, 36);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		{
			Nametext = new JTextField();
			Nametext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ChatClient sentName=new ChatClient();
					sentName.username = Nametext.getText();
				}
			});
			Nametext.setBounds(48, 40, 233, 27);
			contentPanel.add(Nametext);
			Nametext.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Choose a screen name:");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNewLabel.setBounds(38, 11, 253, 14);
			contentPanel.add(lblNewLabel);
		}
	}

}
