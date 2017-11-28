import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class ServerSender  extends Thread{

	Server server;
	ServerSocket ss; 
	BufferedReader br;
	Socket client;
	PrintWriter writer;
	ObjectOutputStream out;
	ObjectOutputStream out1;
	public ServerSender(Server server, Socket client) {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.client = client;
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.start();
	}
	
	public void run(){
		
		/*try {
			out = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*while(true){
			try {
				writer = new PrintWriter(client.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
	}
	
	/*public void sendmsg(String msg, int type, String c, String n){
		//String msg = "";
		//Scanner in  = new Scanner(System.in);
		//msg = in.nextLine();
		//System.out.println(msg);
		if(type==0){
			try {
				//out.reset();
				//out = new ObjectOutputStream(client.getOutputStream());
				writer = new PrintWriter(client.getOutputStream(), true);
	            writer.println(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type==1){
			//System.out.print(server.clients.size());
			for(Map.Entry<String, Socket> entry:server.clients.entrySet()){
				Socket sc = entry.getValue();
				if(!entry.getKey().equals(c)){
					try {
						writer = new PrintWriter(sc.getOutputStream(), true);
			            writer.println(c+": "+msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if(type==2){
			Socket sc = server.clients.get(n);
			try {
				writer = new PrintWriter(sc.getOutputStream(), true);
	            writer.println(c+": "+msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type==3){
			for(Map.Entry<String, Socket> entry:server.clients.entrySet()){
				Socket sc = entry.getValue();
				if(!entry.getKey().equals(c)&&!entry.getKey().equals(n)){
					try {
						writer = new PrintWriter(sc.getOutputStream(), true);
			            writer.println(c+": "+msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}*/
	public void sendfile(String msg, int type, String c, String n, byte[] b){
		if(type==0){
			//System.out.print(server.clients.size());
			for(Map.Entry<String, ObjectOutputStream> entry:server.clients.entrySet()){
				ObjectOutputStream oos = entry.getValue();
				if(!entry.getKey().equals(c)){
					try {
						//writer = new PrintWriter(sc.getOutputStream(), true);
			            //writer.println(c+" "+msg);
						Message message = new Message(c+" "+msg,1,b);
						oos.writeObject(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if(type==1){
			ObjectOutputStream oos = server.clients.get(n);
			try {
				//writer = new PrintWriter(sc.getOutputStream(), true);
	            //writer.println(c+" "+msg);
				Message message = new Message(c+" "+msg,1,b);
				oos.writeObject(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void sendobject(String message, int type, String c, String n, byte[] b){

		if(type==0){
			try {
				//out.reset();
				//out = new ObjectOutputStream(client.getOutputStream());
				Message msg = new Message(message, type, b);
				out.writeObject(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type==1){
			//System.out.print(server.clients.size());
			for(Map.Entry<String, ObjectOutputStream> entry:server.clients.entrySet()){
				ObjectOutputStream oos = entry.getValue();
				if(!entry.getKey().equals(c)){
					try {
						//out = new ObjectOutputStream(client.getOutputStream());
						Message msg = new Message(c+": " + message, type, b);
						oos.writeObject(msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if(type==2){
			ObjectOutputStream oos = server.clients.get(n);
			try {
				Message msg = new Message(c+": " + message, type, b);
				oos.writeObject(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type==3){
			for(Map.Entry<String, ObjectOutputStream> entry:server.clients.entrySet()){
				ObjectOutputStream oos = entry.getValue();
				if(!entry.getKey().equals(c)&&!entry.getKey().equals(n)){
					try {
						Message msg = new Message(c+": " + message, type, b);
						oos.writeObject(msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
