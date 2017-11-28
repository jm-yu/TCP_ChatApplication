import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ServerReceiver extends Thread{

	Server server;
	ServerSocket ss; 
	BufferedReader br;
	ObjectInputStream in;
	Socket client;
	ServerSender ssr;
	FileOutputStream fos;
	Message message;
	
	public ServerReceiver(Server server, Socket client, ServerSender ssr){
		this.server = server;
		this.client = client;
		this.ssr = ssr;
		this.start();
	}
	
	public void run(){
		String name = null;
		String msg = "";
		String err = "invalid instruction";
		Socket sc = client;
		try {
			in = new ObjectInputStream(client.getInputStream());
			//br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while(true){
				//System.out.println("1");
				message = (Message)in.readObject();
				msg = message.getmsg();
				msg = msg.trim();
				System.out.println(msg);
				//msg = br.readLine();
				//System.out.println(msg);
				String s1[] = msg.split("\\s+", 5);
				switch(s1[0]){
				
				case "lookup":
					String group="";
					if(server.clients.size()!=0){
						for(Map.Entry<String, ObjectOutputStream> entry:server.clients.entrySet()){
							group = group + entry.getKey() + ";";
						}
						group = group.substring(0, group.length()-1);
						ssr.sendobject(group, 0, null, null, null);
					}
					server.textArea.setText(server.textArea.getText()+">> "+name+": "+msg+"\n");
					break;
				case "connect":
					server.textArea.setText(server.textArea.getText()+s1[2]+" connected"+"\n");
					name = s1[2];
					server.clients.put(name, ssr.out);
					//ssr.sendmsg("success", 0, null, null);
					ssr.sendobject("success", 0, null, null, null);
					server.refreshList();
					break;
				case "disconnect":
					server.textArea.setText(server.textArea.getText()+name+" disconnect"+"\n");
					server.clients.remove(name);
				    server.refreshList();
				    ssr.out.close();
				    in.close();
				    sc.close();
				    //server.textArea.setText(server.textArea.getText()+">> "+name+": "+msg+"\n");
				    break;
				case "broadcast":
					String temp[] = msg.split("\\s+", 3);
					if(temp.length==3){
						if(temp[1].equals("msg")){
							ssr.sendobject(temp[2], 1, name, null, null);
						}
						if(temp[1].equals("file")){
							String s2[] = temp[temp.length-1].split("/");
							String filename = s2[s2.length-1];
							fos = new FileOutputStream("Server/"+filename);
							fos.write(message.getbytes());
							fos.close();
							server.refreshList();
							ssr.sendfile("file "+filename, 0, name, null, message.getbytes());
						}
					}
					server.textArea.setText(server.textArea.getText()+">> "+name+": "+msg+"\n");
					break;
				case "unicast":
					String temp1[] = msg.split("\\s+", 4);
					if(temp1.length==4){
						if(temp1[1].equals("msg")){
							ssr.sendobject(temp1[3], 2, name, temp1[2], null);
						}
						if(temp1[1].equals("file")){
							String s2[] = temp1[temp1.length-1].split("/");
							String filename = s2[s2.length-1];
							fos = new FileOutputStream("Server/"+filename);
							fos.write(message.getbytes());
							fos.close();
							server.refreshList();
							ssr.sendfile("file "+filename, 1, name, temp1[2], message.getbytes());
						}
					}
					server.textArea.setText(server.textArea.getText()+">> "+name+": "+msg+"\n");
					break;
				case "blockcast":
					String temp2[] = msg.split("\\s+", 4);
					if(temp2.length==4){
						ssr.sendobject(temp2[3], 3, name, temp2[2], null);
					}
					server.textArea.setText(server.textArea.getText()+">> "+name+": "+msg+"\n");
					break;
				default:
					server.textArea.setText(server.textArea.getText()+">> "+name+": "+msg+"\n");
					break;
					
				}
				/*if(s1[0].equals("lookup")){
					String group="";
					if(server.clients.size()!=0){
						for(Map.Entry<String, ObjectOutputStream> entry:server.clients.entrySet()){
							group = group + entry.getKey() + ";";
						}
						group = group.substring(0, group.length()-1);
						ssr.sendobject(group, 0, null, null, null);
					}
					
				}
				else if(s1[1].equals("disconnect")){
				    server.clients.remove(name);
				    server.refreshList();
				    ssr.out.close();
				    in.close();
				    sc.close();
				}
				else if(s1.length<3){
					//ssr.sendmsg(err, 0, null, null);
					ssr.sendobject(err, 0, null, null, null);
				}
				else{
					//System.out.println(s1[0]);
					if(s1[0].equals("connect")){
						server.textArea.setText(server.textArea.getText()+s1[2]+" connected"+"\n");
						name = s1[2];
						server.clients.put(name, ssr.out);
						//ssr.sendmsg("success", 0, null, null);
						ssr.sendobject("success", 0, null, null, null);
						server.refreshList();
					}
					else if(s1[1].equals("msg")){
						switch(s1[0]){
						
						case "broadcast":
							//ssr.sendmsg(s1[2], 1, name, null);
							ssr.sendobject(s1[2], 1, name, null, null);
							break;
						case "unicast":
							//ssr.sendmsg(s1[3], 2, name, s1[2]);
							ssr.sendobject(s1[3], 2, name, s1[2], null);
							break;
						case "blockcast":
							//ssr.sendmsg(s1[3], 3, name, s1[2]);
							ssr.sendobject(s1[3], 3, name, s1[2], null);
							break;
						}
					}
					else if(s1[1].equals("file")){
						//System.out.println("1111111");
						String s2[] = s1[s1.length-1].split("/");
						String filename = s2[s2.length-1];
						fos = new FileOutputStream("Server/"+filename);
						fos.write(message.getbytes());
						fos.close();
						server.refreshList();
						switch(s1[0]){
						
						case "broadcast":
							ssr.sendfile("file "+filename+" "+s1[s1.length-1], 0, name, null, message.getbytes());
							break;
						case "unicast":
							ssr.sendfile("file "+filename+" "+s1[s1.length-1], 1, name, s1[2], message.getbytes());
							break;
							
							
						}
					}
					else{
						ssr.sendobject(err, 0, null, null,null);
					}
				}
				
				if(msg!=null&&msg.trim()!=""){
					server.textArea.setText(server.textArea.getText()+">> "+name+": "+msg+"\n");
					//System.out.println(">> "+msg);
					//ssr.sendmsg(msg, 0);
				}*/
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
