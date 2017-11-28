import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSender extends Thread{

	Client client;
	Socket socket;
	ObjectOutputStream out;
	BufferedReader reader;
    PrintWriter writer;
    FileInputStream fis;
    BufferedReader br;
    
	public ClientSender(Client client, Socket socket) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		this.start();
	}
	
	public void run(){
		
		
		/*while(true){
			try {
				//writer = new PrintWriter(socket.getOutputStream(), true);
				out = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		/*while(true){
			msg = client.textArea_1.getText();
			System.out.println(msg);
			client.textArea_1.setText("");
			//Scanner in  = new Scanner(System.in);
			//msg = in.nextLine();
			try {
				    out.writeObject(msg);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		*/
	}
	
	/*public void sendmsg(String msg, int type, String path){
		if(type==0){
			try {
				//System.out.println(msg);
				writer = new PrintWriter(socket.getOutputStream(), true);
	            writer.println(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				fis = new FileInputStream(path);
				BufferedReader br1 = new BufferedReader(new InputStreamReader(fis));
				byte[] b = new byte[1];
				int len;
				String str ="";
				String data = "";
				while((len=fis.read(b))!=-1){
					String subdata = new String(b,0,b.length);
					//System.out.print(b[0]+" ");
					//System.out.println(subdata=="\r");
					data = data + subdata;
				}
				while((str=br1.readLine())!=null){
					data = data + str;
				}
				writer = new PrintWriter(socket.getOutputStream(), true);
				data=data.replaceAll("\r", "\"\\\\r\"");
				data=data.replaceAll("\n","\"\\\\n\"");
	            writer.println(msg+" "+data);
	            //System.out.println(msg+" "+data);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}*/
	public void sendobject(String message, int type, byte[] b, String path){
		if(type==0){
			try {
				Message msg = new Message(message, type, b);
				out.writeObject(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				fis = new FileInputStream(path);
				//BufferedReader br1 = new BufferedReader(new InputStreamReader(fis));
				byte[] b1 = new byte[fis.available()];
				int len;
				String str ="";
				String data = "";
				/*while((len=fis.read(b))!=-1){
					String subdata = new String(b,0,b.length);
					//System.out.print(b[0]+" ");
					//System.out.println(subdata=="\r");
					data = data + subdata;
				}*/
				fis.read(b1);
				/*while((str=br1.readLine())!=null){
					data = data + str;
				}*/
				//writer = new PrintWriter(socket.getOutputStream(), true);
				//data=data.replaceAll("\r", "\"\\\\r\"");
				//data=data.replaceAll("\n","\"\\\\n\"");
	            //writer.println(msg+" "+data);
	            //System.out.println(msg+" "+data);
				Message msg = new Message(message, type, b1);
				out.writeObject(msg);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
		
}
