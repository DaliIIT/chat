package chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class ServerThread implements Runnable {

	// Server server=null;
	Socket client = null;
	InputStream incin;
	BufferedReader cin;
	PrintStream cout;
	Scanner sc = new Scanner(System.in);
	int id;
	String s;
	String name="";
	ServerJF f;
	

	public ServerThread(Socket client, int count /* ,Server server */,ServerJF f ) throws IOException {
		this.f=f;
		this.client = client;
		// this.server=server;
		this.id = count;
		System.out.println("Connection " + id + " with client " + client);
		incin= client.getInputStream();
		cin = new BufferedReader(new InputStreamReader(incin));
		cout = new PrintStream(client.getOutputStream());
		f.client[id-1]=client;
	}

	
	public void sendmsgtocli(String s) {
		cout.println(s);
		//cout.close();
	}
	
	@Override
	public void run() {
		int x = 1;
		try {
			while (true) {
				
			//	if(incin.available()!=0) {

				s = cin.readLine(); // pause till client send something 
				
				if(s.toLowerCase().indexOf("clientname") == -1 ) {
				System.out.print(name+"Client(" + id + ") :" + s + "\n");
				f.setTextlbl(id-1,name+" :" + s + "\n");
				}else {
					name=s.replaceAll("clientName", "");
					f.btnNewButton[id-1].setText(name);
				}
				
				System.out.print("Server : ");
				//}
				// s=stdin.readLine();
				//s = sc.nextLine();
				//s=f.message.toString();
				//System.out.println(s);
				//f.message="";
				if (s.equalsIgnoreCase("bye")) {
					//cout.println("BYE");
					f.cout = new PrintStream(f.client[id-1].getOutputStream());
					f.cout.println("bye");
					f.deleteClient(id-1);
					System.out.println("Connection ended by serv");
					break;
					}
				/*if(!s.equals("")) {
					System.out.println(id);
					f.textArea[f.sendto].append("Server : "+s+" \n");
				    cout.println(s);
				}*/
			}

			cin.close();
			client.close();
			f.cout.close();
			
		  /*if (x == 0) {
				System.out.println("Server cleaning up.");
				System.exit(0);
			}*/
			
		} catch (IOException ex) {
			System.out.println("Error : " + ex);
		}finally {
			try {
				cin.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			f.cout.close();
		}

	}
}