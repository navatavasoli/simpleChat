// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import ocsf.client.*;

import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
    
    
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
    	if(message.startsWith("#")) {
    		handleCommand(message);
    	} else {
    		sendToServer(message);
    	}
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  
  
  
  /**
   * Handles commands from the user. 
   * @param command the command prompt the user enters into the console.
   */
  private void handleCommand(String command) {
	  if(command.equals("#quit")) {
		  quit();
	  } else if(command.equals("#logoff")) {
		  // override and implement the closeConnection() method in AbstractClient
		  connectionClosed();
		  try {
			closeConnection();
		} catch (IOException e) { }
		  clientUI.display("Logoff complete.");
	  } else if(command.equals("#sethost")) {
		  if(!isConnected()) {
			  String[] parts = command.split(" ");
			  String h = parts[1];
			  setHost(h);
		  } else {
			  System.out.println("Error - cannot set host when there is an active connection");
		  }
		  
	  } else if(command.equals("#setport")) {
		  if(!isConnected()) {
			  String[] parts = command.split(" ");
			  int p = Integer.parseInt(parts[1]);
			  setPort(p);
			  clientUI.display("Port successfully set to " + p);
		  } else {
			  System.out.println("Error - cannot set port when there is an active connection");
		  }
		  
	  } else if(command.equals("#login")) {
		  // check first if the client is disconnected
		  // !isConnected() -> connect
		  
		  // openConnection() from AbstractClient will already check if it is connected
		  try {
			  openConnection();
			  clientUI.display("Login successful.");
		  } catch (IOException ex) { }
		  
	  } else if(command.equals("#getHost")) {
		  clientUI.display(getHost());
	  } else if(command.equals("#getPort")) {
		  clientUI.display(String.valueOf(getPort()));
	  }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  /**
	 * Implements the hook method called each time an exception is thrown by the client's
	 * thread that is waiting for messages from the server. 
	 * 
	 * 
	 * @param exception
	 *            the exception raised.
	 */
  	@Override
	protected void connectionException(Exception exception) {
  		clientUI.display("The server has been shut down.");
  		quit(); // quit after displaying the message to the client 
	}
  	
  	/**
	 * Implements the hook method called after the connection has been closed. The default
	 * implementation does nothing. 
	 */
  	@Override
	protected void connectionClosed() {
  		clientUI.display("Connection closed.");
	}
}
//End of ChatClient class
