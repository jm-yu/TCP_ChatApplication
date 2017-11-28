import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;

public class Client extends JFrame {

	private JPanel contentPane;
	ClientReceiver cr;
	ClientSender cs;
	Socket socket;
	JTextArea textArea;
	JList list;
	int connection=0;
	private JTextField textField;
	String name;
	
	public Client(String name) {
		this.name=name;
		this.setTitle(name);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               disconnect();
            }
        });
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    textArea = new JTextArea();
		textArea.setBounds(15, 15, 398, 265);
		textArea.setBorder(BorderFactory.createEtchedBorder());
		textArea.setEditable(false);
		JScrollPane sp = new JScrollPane();
		sp.setBounds(15, 15, 398, 265);
		sp.setViewportView(textArea);
		contentPane.add(sp);
		
		textField = new JTextField();
		textField.setBounds(15, 313, 398, 58);
		textField.setBorder(BorderFactory.createEtchedBorder());
		textField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				 if (e.getKeyCode() == KeyEvent.VK_ENTER){
					 String str = textField.getText();
					 String s1[] = str.split("\\s+",4);
					 if(connection == 0){
						
						 if(s1[0].equals("connect")){
							 String s2[] = s1[1].split(":",2);
							 String IP = s2[0];
							 int port = Integer.parseInt(s2[1]);
							 try {
									socket = new Socket(IP, port);
									cr = new ClientReceiver(Client.this, socket);
									cs = new ClientSender(Client.this, socket);
									connection++;
									//cs.sendmsg(str, 0, null);
									cs.sendobject(str, 0, null, null);
								} catch (UnknownHostException e1) {
									// TODO Auto-generated catch block
									connection--;
									textArea.setText("invalid socket"+"\n");
									//e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									textArea.setText("invalid socket"+"\n");
									//e1.printStackTrace();
								}
								
						 }
					 }
					 if(connection==1&&s1[0].equals("disconnect")){
							disconnect();
							connection--;
						 }
					 if(s1.length>2){
						 if(connection==1&&s1[1].equals("msg")){
							 //cs.sendmsg(str, 0, null);
							 cs.sendobject(str, 0, null, null);
						 }
						 if(connection==1&&s1[1].equals("file")){
							 //cs.sendmsg(str, 1, s1[s1.length-1]);
							 cs.sendobject(str, 1, null, s1[s1.length-1]);
							/* try {
								cs.sendfile(s1[s1.length-1]);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}*/
						 }
					 }
					 else if(connection==1)
						 cs.sendobject(str, 0, null, null);
					
					 textField.setText("");
					 textArea.setText(textArea.getText()+"<< "+str+"\n");
				 }
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblClients = new JLabel("clients:");
		lblClients.setBounds(428, 15, 81, 21);
		contentPane.add(lblClients);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(428, 51, 127, 321);
		contentPane.add(scrollPane);
		
		list = new JList();
		scrollPane.setViewportView(list);
		
		File file = new File(name);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
		
	}
	public void disconnect(){
		if(socket!=null){
			cs.sendobject("disconnect", 0, null, null);
			try {
				this.cr.in.close();
				this.cs.out.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.dispose();
	}
}
