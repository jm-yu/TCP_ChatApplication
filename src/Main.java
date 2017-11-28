import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Main {

	private JFrame frame;
    public static int i=1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 184, 195);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnStartServer = new JButton("start server");
		btnStartServer.setBounds(15, 31, 141, 29);
		btnStartServer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Server server = new Server();
				btnStartServer.setEnabled(false);
			}
			
		});
		frame.getContentPane().add(btnStartServer);
		
		JButton btnAddClient = new JButton("add client");
		btnAddClient.setBounds(15, 75, 141, 29);
		btnAddClient.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Client client = new Client("Client"+i);
				i++;
				
			}
			
		});
		frame.getContentPane().add(btnAddClient);
	}
}
