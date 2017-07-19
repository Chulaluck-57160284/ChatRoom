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
	static BufferedReader in; //รับข้อมูลจาก server
	static PrintWriter out; //ส่งข้อมูลไปให้server
	static String ip = "-1"; // เก็บค่าจากGUI คลาส ServerAddress 
	static String room = "-1"; // เก็บค่าจากGUI คลาส SelectRoom 
	static String username = "-1";// เก็บค่าจากGUI คลาส Name
	JTextField textField = new JTextField(40);
	Name name = new Name();

	public ChatClient() {
	}

	private static final int PORT = 10011;// สร้างPORT

	private void run() throws IOException {
		ChatRoom chat = new ChatRoom(); //สร้างออบเจ็คจากChatroom
		ServerAddress address = new ServerAddress(); //สร้างออบเจ็คจากServerAddress
		address.setVisible(true); //GUI ServerAddress สามารถมองเห็นได้
		boolean checkip = false; 
		while (!checkip) { //ถ้าCheckip==true จึงจะออกจากลูป
			System.out.print("");//ถ้าไม่ใส่จะกดOKไม่ได้
			if (!ip.equals("-1") && !ip.equals("")) {
				try {
					Socket socket = new Socket(ip, PORT);//สร้างsocket
					System.out.println("Attemping to connect to host " + ip
							+ " on port " + PORT + ".");
					in = new BufferedReader(new InputStreamReader(
							socket.getInputStream())); //รับข้อมูลจากserver
					out = new PrintWriter(socket.getOutputStream(), true); //ส่งข้อมูลไปให้server
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
			}// จบifเช็คip
		}
		address.setVisible(false); //ปิดGUI ServerAddress
			
		while (true) {
			String line = in.readLine(); 
			System.out.println("ค่าจากserver คือ "+line);
			// รับค่าจากserver เช่น SUBMITNAME,NAMEACCEPTED
			if(line.equals("SELECTROOM")){
				SelectRoom selectRoom = new SelectRoom ();
				selectRoom.setVisible(true);
				while (room.equals("-1")) {
					
					//ถ้าไม่วนลูปจะรับค่าไม่ได้
					System.out.print("");
				}
				selectRoom.setVisible(false);
				out.println(room);//ส่งชื่อไปให้server
			}
			
			if (line.equals("SUBMITNAME")) {
				name.setVisible(true); //เปิด GUI name
				//System.out.println(in.readLine()); // อยู่ห้องไหน
				//ป้อนชื่อผู้ใช้				
				while (username.equals("-1")) {
					
					//ถ้าไม่วนลูปจะรับค่าไม่ได้
					System.out.print("");
				}
				out.println(username);//ส่งชื่อไปให้server
				
			System.out.println(in.readLine());// บอกClientว่าใช้ชื่อนี้ได้หรือไม่
			} else if (line.equals("NAMEACCEPTED")) {
				//ผู้ใช้สามารถใช้ชื่อนี้ได้
				System.out.println("ชื่อของคุณคือ" + username);
				name.setVisible(false);
				textField.setEditable(true);
				
			} else  if(line.equals("MESSAGE")){
				//ส่งข้อความ
				chat.setVisible(true); //เปิด GUI Chatroom
				 while(true){
					String word = in.readLine();//ค่าที่รับมาจากserver
					if(!word.equals("getuser")){
						chat.apen(word);
						System.out.println(word);
					}else{
						String users = in.readLine();
						//System.out.println("ผู้ที่อยู่ในระบบคือ"+users);
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
		// รับข้อมูลจาก Chatroom เเล้วส่งไปให้ server พอ server
		// รับข้อมูลก็จะปริ้นกับมาที่บรรทัดที่ 84 (line.equals("MESSAGE"))
		if(n!=""){
			out.println(n); 
		}
	}

	public static void main(String[] args) throws IOException {
		ChatClient client = new ChatClient();
		client.run();
	}
}
