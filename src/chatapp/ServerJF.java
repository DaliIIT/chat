package chatapp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class ServerJF extends JFrame {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 JButton[] btnNewButton=new JButton[20];
	JTextArea[] textArea=new JTextArea[20]; 
	private JPanel contentPane;
	 	
		protected String message="";
		int currentCli =0;
		int sendto=0;
		private JTextField textField;
		
		Socket client[]= new Socket[20];
		PrintStream cout;
		
		String FILEPATH;
		String fileToSend;
		JButton btnSend;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerJF frame = new ServerJF();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public void addnewCli(int indice) {
		currentCli ++;
		btnNewButton[indice] = new JButton("New button");
		btnNewButton[indice].setBounds(10, 11+(indice*25), 89, 23);
		btnNewButton[indice].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 hidealllbl() ;
				 sendto=indice;
				 textArea[indice].setVisible(true);
			}
		});
		contentPane.add(btnNewButton[indice]);
		//btnNewButton[indice].setVisible(false);
		textArea[indice] = new JTextArea();
		//textArea[indice].setText("client : "+indice+" :  ");
		//textArea[indice].setEditable(false);
		textArea[indice].setBounds(159,11,335,443);
		contentPane.add(textArea[indice]);
		redorder();
	}
	public void deleteClient(int indice) {
		contentPane.remove(btnNewButton[indice]);
		contentPane.remove(textArea[indice]);
		btnNewButton[indice]=null;
		textArea[indice]=null;
		redorder();
	}
	
	public void redorder() {
		int indice=0;
		for (int i =0; i<currentCli;i++) {
			if(btnNewButton[i]!=null) {
				btnNewButton[i].setBounds(10, 11+(indice*25), 89, 23);
				indice++;
			}	
		}
		this.repaint();
	}
	
	public void hidealllbl() {
		
		for(int i=0;i<currentCli;i++) {
			if(textArea[i]!=null)
			textArea[i].setVisible(false);
		}
	}
	
	public void setTextlbl(int indice,String s) {
		textArea[indice].append("\n"+s);
	}
	
	public ServerJF() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 581);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(168, 484, 326, 36);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addActionListener(new AbstractAction(){
		    
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e)
		    {
				message=textField.getText().toString();
				//System.out.println(message);
				textField.setText("");
				try {
					cout = new PrintStream(client[sendto].getOutputStream());
				} catch (IOException e1) {e1.printStackTrace();}
				if(!message.equals("")) {
					textArea[sendto].append("Server : "+message+" \n");
				    cout.println(message);
				    message="";
				}
				//cout.close();
		    }
		} );
		
		JButton btnNewButton_1 = new JButton("upload");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFrame frame = new JFrame("Open File Example");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(new BorderLayout());
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					 FILEPATH = chooser.getSelectedFile().toString();
					 btnSend.setEnabled(true);
					 upload();
				}
			}
		});
		btnNewButton_1.setBounds(10, 484, 78, 36);
		contentPane.add(btnNewButton_1);
		
		 btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					cout = new PrintStream(client[sendto].getOutputStream());
				} catch (IOException e1) {e1.printStackTrace();}
				System.out.println(fileToSend);
				cout.println(fileToSend+"isafile");
				btnSend.setEnabled(false);
				//cout.close();
			}
			
		});
		btnSend.setEnabled(false);
		btnSend.setBounds(98, 484, 78, 36);
		contentPane.add(btnSend);
		this.setVisible(true);

		
		/*for(int i=0;i<10;i++) {
		 btnNewButton[i] = new JButton("New button");
		btnNewButton[i].setBounds(10, 11+(i*25), 89, 23);
		contentPane.add(btnNewButton[i]);
		btnNewButton[i].setVisible(false);
		}*/
	}
	
	public void upload() {
		StringBuffer buf = new StringBuffer();
		Reader r;
		try {
			r = new FileReader(FILEPATH);

			int octet = 0;
			while (octet != -1) {
				octet = r.read();
				if (octet == -1)
					break;
				buf.append((char) octet);
				System.out.print((char) (octet));
			}

			r.close();
			fileToSend=buf.toString();
			
			btnSend.setEnabled(true);
			//textfi.setText(buf.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
