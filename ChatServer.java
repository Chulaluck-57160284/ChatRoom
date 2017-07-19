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
	private static final int PORT = 10011;// ���ҧPORT
	public static ArrayList<String> names = new ArrayList<String>();// ���ҧ����纪��ͷء��
	public static ArrayList<String> names1 = new ArrayList<String>();// ���ҧ����纪�����ͧ1
	public static ArrayList<String> names2 = new ArrayList<String>();// ���ҧ����纪�����ͧ2

	private static ArrayList<PrintWriter> writers1 = new ArrayList<PrintWriter>();// ���ҧ����红�ͤ�����ͧ1
	private static ArrayList<PrintWriter> writers2 = new ArrayList<PrintWriter>();// ���ҧ����红�ͤ�����ͧ2
	public static void main(String[] args) throws Exception {
		System.out.println("The chat server is running.");
		ServerSocket serverSocket = new ServerSocket(PORT); // ���ҧsocket
		System.out.println("Connection Socket Created");
		try {
			System.out.println("Waiting for Connection");
			while (true) {
				new Handler(serverSocket.accept()).start();
				// server����Ѻ�����������
				// Handler �ѹ�������� Execute �����������ҡ���ӧҹ�͡�˹��
				// Thread ��ѡ
				System.out.println("��й���ռ���������к�����");
				System.out.println("�ӹǹ������������к���й����"+ (names.size() + 1));
			}
		} catch (IOException e) {
			System.out.println("Accept failed."); // ����������Ͷ١¡��ԡ
			System.exit(1);
		} finally {
			try {
				serverSocket.close(); // �Դsocket
			} catch (IOException e) {
				System.out.println("Could not close port: " + PORT + "."); // �������ö�Դport��
				System.exit(1); // �͡�ҡ����ѹ�ѹ��
			}

		
		}

	}
	
	static public void sentM(String a,String name,String input){			
		// a ��� ��ͧ
		if(a.equals("child")||a.equals("1")){//��ͧ 1

			System.out.println("�觢�ͤ�����������������к�������"+names1.size()+"��");
			String users ="";
			for(int i=0;i<names1.size();i++){						
				if(i==0){
					users = users+names1.get(i);
				}else{
					users = users+"/"+names1.get(i);
				}					
			}	
			for (PrintWriter writer : writers1) {	
				System.out.println(writers1.size()+"��ͤ�������á��ͧ1");
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
			System.out.println("�觢�ͤ�����������������к�������"+names2.size()+"��");
			
			for(int i=0;i<names2.size();i++){						
				if(i==0){
					users = users+names2.get(i);
				}else{
					users = users+"/"+names2.get(i);
				}					
			}		
		}
			for (PrintWriter writer : writers2) {
				System.out.println(writers2.size()+"��ͤ�������á��ͧ2");
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
		//����ª��ͧ Handler ������� Execute ������� �����ҡ���ӧҹ�͡�˹�� Thread ��ѡ  
		//Handler ���繵���觢�ͤ����ҡ�����˹�觤���ѧ�ء���ષ
		
		
		public String name;// ���ҧ�������Ѻ����
		private Socket socket;
		private BufferedReader in;// ���ҧ�����ҹ
		private PrintWriter out;// ���ҧ�����¹
		public static ChatServer chatserver = new ChatServer();
		
		public Handler(Socket socket) {
			this.socket = socket;
			//System.out.println("open socket"+(names.size()+1));
		}
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));// �Ѻ�����Ũҡclient
				out = new PrintWriter(socket.getOutputStream(), true);// �觢������client
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
					out.println("SUBMITNAME"); //�觤����SUBMITNAME����Client ����� Client���Ѻ����觪��͡�Ѻ��					
					name = in.readLine(); //�纪���������㹵����� name					
					synchronized (chatserver.names) {
						// synchronized ��ǹ����繵�Ƿ��� lock ������¡��ҹ
						// ��������ҹ�������ѹ
						// ��觨��ջ���ª���ҡ���������ҵ�ͧ��èСѹ���������ӧҹ����е��
						if (name.equals("")) {
							System.out.println("�����������͹�����������к�");
							out.println("��سһ�͹�����������к�");
						} else if (!chatserver.names.contains(name)) {
							System.out.println("���������ö����͹����к���  ���ͧ͢�������"+name);
							out.println("�س����ö����͹����к���");
							if(rooms.equals("1")||rooms.equals("child")){
								chatserver.names1.add(name);	
							}else{
								chatserver.names2.add(name);	
							}
							chatserver.names.add(name);							
							break;
						} else {
							System.out.println("������͹���ͫ��");
							out.println("���͹���ռ���������");
						}
					}
				}
				out.println("NAMEACCEPTED");
				if(rooms.equals("1")||rooms.equals("child")){
					//�����ʹ�ѹ�����������鹤���͡�ҷҧ˹�Ҩ�
					writers1.add(out);//name ��������� writers�� PrintWriter	
					System.out.println("writer1");
				}else{
					if(rooms.equals("2")||rooms.equals("adult")){
						writers2.add(out);//name ��������� writers�� PrintWriter	
						System.out.println("writer2");
					}			
				}						
				out.println("MESSAGE");
				while (true) {	
					//���鹪��ͼ����������к�												
					String input = in.readLine();//��ͤ�������Ѻ�ҡclient
					int rr=0;
					if(!input.equals("")){
						/*System.out.println("�觢�ͤ�����������������к�������"+chatserver.names1.size()+"��");
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
						System.out.println("�觢�ͤ�����������������к�������"+chatserver.names2.size()+"��");
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
					System.out.println("�������� " + name + " ���͡�ҡ�к������ ");// �����server
					//out.println("MESSAGE " + "�����" + name + "�͡�ҡ�к������ ");
					names.remove(name);
					for(int i=0;i<names1.size();i++){
						if(name.equals(names1.get(i))){
							System.out.println(name+"name!=null");
							System.out.println("�������� " + name + " ���͡�ҡ�к������ ");// �����server
							names1.remove(name);
						}
					}
					for(int i=0;i<names2.size();i++){
						if(name.equals(names2.get(i))){
							System.out.println(name+"name!=null");
							System.out.println("�������� " + name + " ���͡�ҡ�к������ ");// �����server
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
