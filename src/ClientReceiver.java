import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiver  extends Thread{

	Client client;
	Socket socket;
	ObjectInputStream in;
	BufferedReader br;
	FileOutputStream fos;
	Message message;
	
	public ClientReceiver(Client client, Socket socket) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.socket = socket;
		this.start();
	}
	
    public void run(){
    	Client local = client;
		String name;
		String msg = "";
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while(true){
				//System.out.println("1");
				message = (Message)in.readObject();
				msg = message.getmsg();
				//System.out.print(client.textArea.getText()+"\n");
				//br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//msg = br.readLine();
				String str[] = msg.split("\\s+",4);
				if(msg!=null&&msg.trim()!=""){
					//System.out.println(">> "+msg);
					if(msg.equals("success")){
						local.textArea.setText(local.textArea.getText()+ "connection established!" + "\n");
					}
					if(str[0].equals("lookup")){
						String group[] = str[1].split(";");
						client.list.setListData(group);
					}
					else if(str.length>2){
						if(str[1].equals("file")){
							
							String filename = str[2];
							fos = new FileOutputStream(local.name+"/"+filename);
							fos.write(message.getbytes());
							fos.close();
							local.textArea.setText(local.textArea.getText()+ "received a file: " +filename+"from "+str[0]+ "\n");
						}
						else
							local.textArea.setText(local.textArea.getText()+ ">> "+msg+"\n");
					}
						
					else
						local.textArea.setText(local.textArea.getText()+ ">> "+msg+"\n");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			local.textArea.setText(local.textArea.getText()+ ">> "+"server disconnect"+"\n");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
