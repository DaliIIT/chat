package chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class Client extends Thread {
    
  /*  public static void main(String args[]) throws Exception
	{   ClientJF cli = new ClientJF();
	
		//Socket sk=new Socket("127.0.0.1",5000);
		BufferedReader sin=new BufferedReader(new InputStreamReader(ClientJF.sk.getInputStream()));
		//PrintStream sout=new PrintStream(sk.getOutputStream());
		//BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		String s;
		while (  true )
		{
			s=sin.readLine();
			 cli.textArea.append("Server : "+s+"\n");
			System.out.print("Server : "+s+"\n");
			
			//System.out.print("Client : ");
			//s=stdin.readLine();
			//sout.println(s);
			
                        if ( s.equalsIgnoreCase("BYE") )
                        {
                           System.out.println("Connection ended by server");
 			   break;
                        }
                        
			
  			
		}
		 cli.sk.close();
		 sin.close();
		 cli.sout.close();
 		//stdin.close();
	}*/
	
    ClientJF cli;
    BufferedReader sin;
    public Client(ClientJF cli) throws IOException {
    	this.cli=cli;
    	sin=new BufferedReader(new InputStreamReader(cli.sk.getInputStream()));
    }
    
    public void run() {
 
    	
		//Socket sk=new Socket("127.0.0.1",5000);
	
		//PrintStream sout=new PrintStream(sk.getOutputStream());
		//BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		String s = "";
		while (  true )
		{
			try {
				s=sin.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(s.indexOf("isafile")!=-1) {
				 cli.textArea.append("\nFile recived , click on download to get it\n");
				 cli.btnDownload.setEnabled(true);
				 cli.File=s.replaceAll("isafile", "");
			}else {
			
			 cli.textArea.append("Server : "+s+"\n");
			 System.out.print("Server : "+s+"\n");
			/*
			System.out.print("Client : ");
			s=stdin.readLine();
			sout.println(s);
			*/
			}
			//cli.textArea.append("Server : "+s+"\n");
			
                        if ( s.equalsIgnoreCase("bye") )
                        {
                           System.out.println("Connection ended by server");
                           break;
                        }
		
		}
		
		 try {
			 cli.sk.close();
			 sin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 cli.sout.close();
 		//stdin.close();
    }
    
}
