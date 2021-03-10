package client;

import java.io.IOException;
import java.util.logging.Logger;

import client.controllers.ClientMessages;
import common.controllers.Message;
import common.ocsf.client.AbstractClient;
/**
 * The ViewStarter class that extends application represent the start view
 * @author  Shahar Ronen
 * @author Dorin Segal
 * @author Amit Sinter
 * @author Remez David
 * @author Itamar Gino
 */
public class Client extends AbstractClient {
	private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
	/** all of the below are objects of each client controllers that we created */
	

	 /**
   	 * Client is execute the connection to the server
   	 * @param host is the host ip number
   	 * @param port is the port number
   	 * @throws IOException if IO problems occurs
   	 */
	public Client(String host, int port) throws IOException {
		super(host, port);
		openConnection();
	}
	 /**
   	 * handleMessageFromServer is handeling the respond from the server due to client request
   	 * @param msg is the message that recieved from the server
   	 */
	@Override
	protected void handleMessageFromServer(Object msg) {
		try {
			if(((Message) msg).getObject()!=null)
				System.out.println("handleMessageFromServer " + ((Message) msg));
			ClientMessages.messageFromServer(msg);
			
		}
		
		catch (Exception e) {

			LOGGER.severe("Could not handle message from server. " + e);
		}

	}
	 /**
   	 * handleMessageFromClientUI is chenneling the client's request to the server
   	 * @param message is the message that need to be sent to the server
   	 */
	public void handleMessageFromClientUI(Object message) {
		try {
			sendToServer(message);
		} catch (IOException e) {

			LOGGER.severe("Could not send message to server.  Terminating client." + e);
		}
	}

}