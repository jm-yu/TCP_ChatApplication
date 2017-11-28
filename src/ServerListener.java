import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerListener extends Thread{

	Server server;
	ServerSocket ss; 
	BufferedReader br;
	ServerReceiver sr;
	ServerSender ssr;
	
	
	public ServerListener(Server server) {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.start();
	}



	public void run(){
		try {
			ss = new ServerSocket(1112);
			//server.clients = new Map<String, ObjectOutputStream>();
			//System.out.println("Server started, port: 1111");
			//System.out.println("wait for clients");
			server.textArea.setText(server.textArea.getText()+"Server started, port: 1112"+"\n");
			server.textArea.setText(server.textArea.getText()+"wait for clients"+"\n");
			while(true){
				Socket client = ss.accept();
				//System.out.println("client:" + client.toString() +" connected");
				//server.textArea.setText(server.textArea.getText()+"client:" + client.toString() +" connected"+"\n");
				ssr = new ServerSender(server, client);
				sr = new ServerReceiver(server, client, ssr);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
