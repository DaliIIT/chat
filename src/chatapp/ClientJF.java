package chatapp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.event.ActionListener;
import java.awt.Panel;

public class ClientJF extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	  Socket sk;
	  PrintStream sout;
	 BufferedReader sin;
	JTextArea textArea;
	//static Thread t1;
	String FILEPATH;
	String File;
	Panel panel_1 ;
	JButton btnDisconnect;
	boolean connectionState=false;
	 JButton btnDownload;
	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args)  {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientJF frame = new ClientJF();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public ClientJF() throws Exception{
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 526, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 0, 510, 48);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNom = new JLabel("NOM:");
		lblNom.setBounds(10, 11, 47, 14);
		panel.add(lblNom);
		
		textField = new JTextField("");
		textField.setBounds(67, 8, 103, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		 btnDisconnect = new JButton("Connect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().equals("")) {
				try {
					stateChanger(textField.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					}else {
						textArea.append("\n please insert your name !! \n");
					}
				}
		});
		btnDisconnect.setBounds(349, 7, 89, 23);
		panel.add(btnDisconnect);
		
		 panel_1 = new Panel();
		panel_1.setBackground(Color.RED);
		panel_1.setBounds(458, 11, 19, 19);
		panel.add(panel_1);
		
		
		 textArea = new JTextArea();
		textArea.setBounds(10, 59, 490, 263);
		contentPane.add(textArea);
		
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 333, 392, 20);
		textField_1.setColumns(10);
		textField_1.addActionListener(new AbstractAction(){
		    
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e)
		    {
				if(connectionState) {
				try {
					sout = new PrintStream(sk.getOutputStream());
				} catch (IOException e1) {e1.printStackTrace();}
				if(connectionState) {
					textArea.append(textField.getText()+" : "+textField_1.getText()+" \n");
				    sout.println(textField_1.getText());
				    textField_1.setText("");
							}
					}
			}
		} );
		contentPane.add(textField_1);
		
		btnDownload = new JButton("Download");
		btnDownload.setEnabled(false);
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    final JFrame frame = new JFrame("Open File Example");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setLayout(new BorderLayout());
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                	
                	 FILEPATH =chooser.getSelectedFile().toString();
                		//System.out.println(chooser.getSelectedFile().toString());
                	 //String string = textField_3.getText();
                	 
                		System.out.println(FILEPATH);
         				String s = FILEPATH+"filtredFile.txt";
         				try {
         			    Writer w =new FileWriter(s);
         				w.write(File);    
         			    w.close();
         			    }catch(Exception ex){
         		        ex.printStackTrace();
                    	  }
         				btnDownload.setEnabled(false);
                	}
                }
		});
		btnDownload.setBounds(412, 332, 89, 23);
		contentPane.add(btnDownload);
		

		 //this.setVisible(true);
	}
	
		public void stateChanger(String name) throws UnknownHostException, IOException {
			if(connectionState==false) {
				 sk=new Socket("127.0.0.1",5000);
				 sout=new PrintStream(sk.getOutputStream());
				 //sin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
				btnDisconnect.setText("Disconnect");
				Thread t1= new Thread(new Client(this));
				t1.start();
				panel_1.setBackground(Color.GREEN);
				sout.println("clientName"+name);
				connectionState=!connectionState;
			}else {
				btnDisconnect.setText("Connect");
				sout.println("bye");
				panel_1.setBackground(Color.RED);
				connectionState=!connectionState;
			}
		}
}
