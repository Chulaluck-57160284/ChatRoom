package ProjectChatRoom;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient {
	static Scanner kb = new Scanner (System.in);
	static BufferedReader in; //�Ѻ�����Ũҡ server
	static PrintWriter out; //�觢���������server
	static String ip = "-1"; // �纤�ҨҡGUI ���� ServerAddress 
	static String room = "-1"; // �纤�ҨҡGUI ���� SelectRoom 
	static String username = "-1";// �纤�ҨҡGUI ���� Name
	JTextField textField = new JTextField(40);
	Name name = new Name();

	public ChatClient() {
	}

	private static final int PORT = 10011;// ���ҧPORT

	private void run() throws IOException {
		ChatRoom chat = new ChatRoom(); //���ҧ�ͺ�礨ҡChatroom
		ServerAddress address = new ServerAddress(); //���ҧ�ͺ�礨ҡServerAddress
		address.setVisible(true); //GUI ServerAddress ����ö�ͧ�����
		boolean checkip = false; 
		while (!checkip) { //���Checkip==true �֧���͡�ҡ�ٻ
			System.out.print("");//���������С�OK�����
			if (!ip.equals("-1") && !ip.equals("")) {
				try {
					Socket socket = new Socket(ip, PORT);//���ҧsocket
					System.out.println("Attemping to connect to host " + ip
							+ " on port " + PORT + ".");
					in = new BufferedReader(new InputStreamReader(
							socket.getInputStream())); //�Ѻ�����Ũҡserver
					out = new PrintWriter(socket.getOutputStream(), true); //�觢���������server
					System.out.println("Connect to the Server sucess.");
					checkip = true;
					break;
				} catch (UnknownHostException e) {
					// System.err.println("Don't know about host: " +serverAddress);
					// System.exit(1);
					JOptionPane.showMessageDialog(null,
							"Don't know about host: " + ip);
					ip = "-1";
					continue;
				} catch (IOException e) {
					// System.err.println("Couldn't get I/O for "+"the connection to: "+serverAddress);
					// System.exit(1);
					JOptionPane.showMessageDialog(null, "Couldn't get I/O for "
							+ "the connection to: " + ip);
					ip = "-1";
					continue;
				}
			}// ��if��ip
		}
		address.setVisible(false); //�ԴGUI ServerAddress
			
		while (true) {
			String line = in.readLine(); 
			System.out.println("��Ҩҡserver ��� "+line);
			// �Ѻ��Ҩҡserver �� SUBMITNAME,NAMEACCEPTED
			if(line.equals("SELECTROOM")){
				SelectRoom selectRoom = new SelectRoom ();
				selectRoom.setVisible(true);
				while (room.equals("-1")) {
					
					//������ǹ�ٻ���Ѻ��������
					System.out.print("");
				}
				selectRoom.setVisible(false);
				out.println(room);//�觪�������server
			}
			
			if (line.equals("SUBMITNAME")) {
				name.setVisible(true); //�Դ GUI name
				//System.out.println(in.readLine()); // ������ͧ�˹
				//��͹���ͼ����				
				while (username.equals("-1")) {
					
					//������ǹ�ٻ���Ѻ��������
					System.out.print("");
				}
				out.println(username);//�觪�������server
				
			System.out.println(in.readLine());// �͡Client�������͹�����������
			} else if (line.equals("NAMEACCEPTED")) {
				//���������ö����͹����
				System.out.println("���ͧ͢�س���" + username);
				name.setVisible(false);
				textField.setEditable(true);
				
			} else  if(line.equals("MESSAGE")){
				//�觢�ͤ���
				chat.setVisible(true); //�Դ GUI Chatroom
				 while(true){
					String word = in.readLine();//��ҷ���Ѻ�Ҩҡserver
					if(!word.equals("getuser")){
						chat.apen(word);
						System.out.println(word);
					}else{
						String users = in.readLine();
						//System.out.println("�����������к����"+users);
						String user[] = users.split("/");
						chat.getUserArea.setText("");
						for(int i=0;i<user.length;i++)
						chat.apen2(user[i]);

					}
				 }
			}
		}
	}
	public static void getChat(String n) {
		// �Ѻ�����Ũҡ Chatroom ����������� server �� server
		// �Ѻ�����š�л��鹡Ѻ�ҷ���÷Ѵ��� 84 (line.equals("MESSAGE"))
		if(n!=""){
			out.println(n); 
		}
	}

	public static void main(String[] args) throws IOException {
		ChatClient client = new ChatClient();
		client.run();
	}
}
