package chatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

	int port;
	ServerSocket server = null;
	Socket client = null;
	ExecutorService pool = Executors.newFixedThreadPool(20); // pool size = number of threads or we can say number of connected clients
	int clientcount = 0;

	public Server(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws IOException {
		Server serverobj = new Server(5000);
		serverobj.startServer();
	}

	public void startServer() throws IOException {
		ServerJF f = new ServerJF();
		server = new ServerSocket(5000);
		System.out.println("Server run");
		//System.out.println("");
		clientcount=0;
		while (true) {
			client = server.accept(); // pause here
			f.addnewCli(clientcount);
			clientcount++;
			ServerThread runnable = new ServerThread(client, clientcount/*, this*/,f);
			pool.execute(runnable);
		}

	}

}
