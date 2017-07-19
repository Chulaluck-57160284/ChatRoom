package ProjectChatRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import javax.swing.JOptionPane;

public class ChatServer  {
	private static final int PORT = 10011;// สร้างPORT
	public static ArrayList<String> names = new ArrayList<String>();// สร้างตัวเก็บชื่อทุกคน
	public static ArrayList<String> names1 = new ArrayList<String>();// สร้างตัวเก็บชื่อห้อง1
	public static ArrayList<String> names2 = new ArrayList<String>();// สร้างตัวเก็บชื่อห้อง2

	private static ArrayList<PrintWriter> writers1 = new ArrayList<PrintWriter>();// สร้างตัวเก็บข้อความห้อง1
	private static ArrayList<PrintWriter> writers2 = new ArrayList<PrintWriter>();// สร้างตัวเก็บข้อความห้อง2
	public static void main(String[] args) throws Exception {
		System.out.println("The chat server is running.");
		ServerSocket serverSocket = new ServerSocket(PORT); // สร้างsocket
		System.out.println("Connection Socket Created");
		try {
			System.out.println("Waiting for Connection");
			while (true) {
				new Handler(serverSocket.accept()).start();
				// serverยอมรับการเชื่อมต่อ
				// Handler มันมีไว้ให้ Execute คำสั่งใดๆที่อยากให้ทำงานนอกเหนือ
				// Thread หลัก
				System.out.println("ขณะนี้มีผู้เข้ามาในระบบใหม่");
				System.out.println("จำนวนคนที่เข้ามาในระบบขณะนี้คือ"+ (names.size() + 1));
			}
		} catch (IOException e) {
			System.out.println("Accept failed."); // การเชื่อมต่อถูกยกเลิก
			System.exit(1);
		} finally {
			try {
				serverSocket.close(); // ปิดsocket
			} catch (IOException e) {
				System.out.println("Could not close port: " + PORT + "."); // ไม่สามารถปิดportได้
				System.exit(1); // ออกจากการรันทันที
			}

		
		}

	}
	
	static public void sentM(String a,String name,String input){			
		// a คือ ห้อง
		if(a.equals("child")||a.equals("1")){//ห้อง 1

			System.out.println("ส่งข้อความไปให้ผู้ที่อยู่ในระบบทั้งหมด"+names1.size()+"คน");
			String users ="";
			for(int i=0;i<names1.size();i++){						
				if(i==0){
					users = users+names1.get(i);
				}else{
					users = users+"/"+names1.get(i);
				}					
			}	
			for (PrintWriter writer : writers1) {	
				System.out.println(writers1.size()+"ข้อความคนเเรกห้อง1");
				if(!input.equals("")){											
						writer.println(name + ": " + input);
						System.out.println("By " + name + ": " + input);
							if(true){
							writer.println("getuser");
							writer.println(users);
							}	
				}															
			}
		}else{
			String users ="";
			if(!input.equals("")){
			System.out.println("ส่งข้อความไปให้ผู้ที่อยู่ในระบบทั้งหมด"+names2.size()+"คน");
			
			for(int i=0;i<names2.size();i++){						
				if(i==0){
					users = users+names2.get(i);
				}else{
					users = users+"/"+names2.get(i);
				}					
			}		
		}
			for (PrintWriter writer : writers2) {
				System.out.println(writers2.size()+"ข้อความคนเเรกห้อง2");
				if(!input.equals("")){						
						//Calendar c = Calendar.getInstance();
						//SimpleDateFormat df = new SimpleDateFormat("EHH:mm");
						//String currentDate = df.format(c.getTime());
						//writer.println("\t\t" + currentDate);						
						writer.println(name + ": " + input);
						System.out.println("By " + name + ": " + input);
							if(true){
							writer.println("getuser");
							writer.println(users);
							}	
				}															
			}
			
		}
		
	}

	private static class Handler extends Thread {
		//ประโยชน์ของ Handler คือเพื่อ Execute คำสั่งใดๆ ที่อยากให้ทำงานนอกเหนือ Thread หลัก  
		//Handler จะเป็นตัวส่งข้อความจากผู้ใช้หนึ่งคนไปยังทุกๆๆเเชท
		
		
		public String name;// สร้างตัวเเปรรับชื่อ
		private Socket socket;
		private BufferedReader in;// สร้างตัวอ่าน
		private PrintWriter out;// สร้างตัวเขียน
		public static ChatServer chatserver = new ChatServer();
		
		public Handler(Socket socket) {
			this.socket = socket;
			//System.out.println("open socket"+(names.size()+1));
		}
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));// รับข้อมูลจากclient
				out = new PrintWriter(socket.getOutputStream(), true);// ส่งข้อมูลไปclient
				out.println("SELECTROOM");
				String rooms = in.readLine();
				while(true){
					if(rooms.equals("child")||rooms.equals("1")){
						System.out.println("you select 1");
					}else{
						if(rooms.equals("adult")||rooms.equals("2")){
							System.out.println("you select 2");
						}
					}
					break;
				}			
				while (true) {
					out.println("SUBMITNAME"); //ส่งคำว่าSUBMITNAMEไปให้Client เมื่อ Clientได้รับก็จะส่งชื่อกลับมา					
					name = in.readLine(); //เก็บชื่อเอาไว้ในตัวเเปร name					
					synchronized (chatserver.names) {
						// synchronized ตัวนี้จะเป็นตัวที่ไป lock การเรียกใช้งาน
						// ไม่ให้ใช้งานได้พร้อมกัน
						// ซึ่งจะมีประโยชน์มากที่เวลาเราต้องการจะกันข้อมูลให้ทำงานได้ทีละตัว
						if (name.equals("")) {
							System.out.println("ผู้ใช้ไม่ได้ป้อนชื่อเข้ามาในระบบ");
							out.println("กรุณาป้อนชื่อเข้ามาในระบบ");
						} else if (!chatserver.names.contains(name)) {
							System.out.println("ผู้ใช้สามารถใช้ชื่อนี้ในระบบได้  ชื่อของผู้ใช้คือ"+name);
							out.println("คุณสามารถใช้ชื่อนี้ในระบบได้");
							if(rooms.equals("1")||rooms.equals("child")){
								chatserver.names1.add(name);	
							}else{
								chatserver.names2.add(name);	
							}
							chatserver.names.add(name);							
							break;
						} else {
							System.out.println("ผู้ใช้ป้อนชื่อซ้ำ");
							out.println("ชื่อนี้มีผู้ใช้เเล้ว");
						}
					}
				}
				out.println("NAMEACCEPTED");
				if(rooms.equals("1")||rooms.equals("child")){
					//ไม่เเอดมันจะไม่ยอมปริ้นค่าออกมาทางหน้าจอ
					writers1.add(out);//name ไม่ได้เพราะ writersเป็น PrintWriter	
					System.out.println("writer1");
				}else{
					if(rooms.equals("2")||rooms.equals("adult")){
						writers2.add(out);//name ไม่ได้เพราะ writersเป็น PrintWriter	
						System.out.println("writer2");
					}			
				}						
				out.println("MESSAGE");
				while (true) {	
					//ปริ้นชื่อผู้ที่อยู่ในระบบ												
					String input = in.readLine();//ข้อความที่รับจากclient
					int rr=0;
					if(!input.equals("")){
						/*System.out.println("ส่งข้อความไปให้ผู้ที่อยู่ในระบบทั้งหมด"+chatserver.names1.size()+"คน");
						String users ="";
						for(int i=0;i<chatserver.names1.size();i++){						
							if(i==0){
								users = users+chatserver.names1.get(i);
							}else{
								users = users+"/"+chatserver.names1.get(i);
							}					
						}*/				
						chatserver.sentM(rooms, name, input);		
					}
					/*if(!input.equals("")){
						System.out.println("ส่งข้อความไปให้ผู้ที่อยู่ในระบบทั้งหมด"+chatserver.names2.size()+"คน");
						String users ="";
						for(int i=0;i<chatserver.names2.size();i++){						
							if(i==0){
								users = users+chatserver.names2.get(i);
							}else{
								users = users+"/"+chatserver.names2.get(i);
							}					
						}
						
					
					}*/
				}
			} catch (IOException e) {
				System.out.println(e);
			} finally {
				if (name != null) {
					System.out.println(name+"name!=null");
					System.out.println("ผู้ใช้ชื่อ " + name + " ได้ออกจากระบบเเล้ว ");// ส่งให้server
					//out.println("MESSAGE " + "ผู้ใช้" + name + "ออกจากระบบเเล้ว ");
					names.remove(name);
					for(int i=0;i<names1.size();i++){
						if(name.equals(names1.get(i))){
							System.out.println(name+"name!=null");
							System.out.println("ผู้ใช้ชื่อ " + name + " ได้ออกจากระบบเเล้ว ");// ส่งให้server
							names1.remove(name);
						}
					}
					for(int i=0;i<names2.size();i++){
						if(name.equals(names2.get(i))){
							System.out.println(name+"name!=null");
							System.out.println("ผู้ใช้ชื่อ " + name + " ได้ออกจากระบบเเล้ว ");// ส่งให้server
							names2.remove(name);
						}
					}
				}
				if (out != null) {
					//writers.remove(out);
				}
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
