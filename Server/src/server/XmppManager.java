package server;

import java.io.*;
import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
 
public class XmppManager implements MessageListener{
 
    XMPPConnection connection;
 
    public void login(String userName, String password) throws XMPPException
    {
    ConnectionConfiguration config = new ConnectionConfiguration("192.168.110.160");
    connection = new XMPPConnection(config);
 
    connection.connect();
    connection.login(userName, password);
    }
    public void roster(){
    Roster roster = connection.getRoster();
    Collection<RosterEntry> entries = roster.getEntries();

        for(RosterEntry entry1 : entries)
        {
            System.out.println("UserID:- " + entry1.getUser());
            System.out.println("Name:- " + entry1.getName());
            System.out.println("Status:- " + entry1.getStatus());
            System.out.println("type:- " + entry1.getType());   
        }
    }
    public void sendMessage(String message, String to) throws XMPPException
    {
    Chat chat = connection.getChatManager().createChat(to, this);
    chat.sendMessage(message);
    try {
    	FileWriter fw = new FileWriter("home.txt");
		PrintWriter writer= new PrintWriter(fw);
		writer.write(" ");
		writer.flush();
		writer.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    public void processMessage(Chat chat, Message message)
    {
     
    	System.out.println(chat.getParticipant() + " says: " + message.getBody());
    	try {
			File file = new File("home.txt");
			FileWriter filewriter = new FileWriter(file.getName(),true);
			BufferedWriter bufferwriter = new BufferedWriter(filewriter);
			bufferwriter.write(message.getBody());
			bufferwriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
 
    public static void main(String args[]) throws XMPPException, IOException
    {   
    XmppManager c = new XmppManager();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String msg; 
    c.login("admin", "admin");
    c.roster();
    boolean run = true; 
    while(run)
    {
    System.out.println("-----"); 
    System.out.println("Who do you want to talk to? - Type contacts full email address(***@ganya):");
    String talkTo = br.readLine(); 
    System.out.println("-----");
    c.sendMessage("hello", talkTo);
    System.out.println("All messages will be sent to " + talkTo);
    System.out.println("Enter your message in the console:");
    System.out.println("-----\n");
 
    while( !(msg=br.readLine()).equals("bye"))
    {
        c.sendMessage(msg, talkTo);
    }
    
   
    }
    }    
}