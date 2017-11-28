import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;

public class Server extends JFrame {

	private JPanel contentPane;
	static Map<String,ObjectOutputStream> clients = new HashMap<String,ObjectOutputStream>();
	ArrayList client = new ArrayList();
	ArrayList files = new ArrayList();
	public ServerListener sl;
	JTextArea textArea;
	private JLabel lblFiles;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JList list;
	private JList list_1;
	
	public Server() {
		this.setTitle("Server");
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 15, 300, 293);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setBorder(BorderFactory.createEtchedBorder());
		textArea.setBounds(15, 15, 300, 293);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JLabel lblClients = new JLabel("clients:");
		lblClients.setBounds(340, 15, 81, 21);
		contentPane.add(lblClients);
		
		lblFiles = new JLabel("files:");
		lblFiles.setBounds(472, 15, 81, 21);
		contentPane.add(lblFiles);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(330, 53, 107, 255);
		contentPane.add(scrollPane_1);
		
		list = new JList();
		scrollPane_1.setViewportView(list);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(466, 51, 116, 260);
		contentPane.add(scrollPane_2);
		
		list_1 = new JList();
		scrollPane_2.setViewportView(list_1);
		
		sl = new ServerListener(Server.this);
		
		File file = new File("Server");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        refreshList();
	}
	public void refreshList(){
		for(Map.Entry<String, ObjectOutputStream> entry:clients.entrySet()){
			client.add(entry.getKey());
		}
		list.setListData(client.toArray());
		client.clear();
		File file = new File("Server");
		File[] filter =  file.listFiles();
		for(int i=0;i<filter.length;i++){
			files.add(filter[i].getName());
		}
		list_1.setListData(files.toArray());
		files.clear();
		updateclient();
	}
	public void updateclient(){
		String group="";
		if(clients.size()!=0){
			for(Map.Entry<String, ObjectOutputStream> entry:clients.entrySet()){
				group = group + entry.getKey() + ";";
			}
			group = group.substring(0, group.length()-1);
			for(Map.Entry<String, ObjectOutputStream> entry:clients.entrySet()){
				ObjectOutputStream oos = entry.getValue();
				//if(!entry.getKey().equals(c)){
					try {
						//out = new ObjectOutputStream(client.getOutputStream());
						Message msg = new Message("lookup " + group, 0, null);
						oos.writeObject(msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
		
	}
}
