package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  //final public static int DEFAULT_PORT = 5555;
  final public static int DEFAULT_PORT = 5566; // Changed because port 5555 is in use by Android on my device 
  // source used to select the port: https://www.gasmi.net/tcp.php
  // String loginKey = "loginID";
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	  // if the message is login, then dont do this format
	  // else, it is, and do the regular message 
    System.out.println("Message received: " + msg + " from " + client);
   // String msgStr = (String) msg;
   // if(msgStr.startsWith("#login")) {
    	// get login ID
    	// store the login ID as a String 
    	//client.setInfo(loginKey, loginID);
    //client.getInfo(loginKey if you want to read it, but don't implement this here 
   // }
    
    
    
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  @Override
  protected void clientConnected(ConnectionToClient client) {
	System.out.println("Client has connected to the server.");
  }
  
  
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
		// Since we don't track which ID belongs to this client directly,
		// remove by value.
		//clientConnections.values().remove(client);
	System.out.println("Client has disconnected from the server.");
	}
  
  /**
   * Overrides the method to handle commands from the user. 
   * @param command the command prompt the user enters into the console.
   */
  private void handleCommand(String command) {
	  if(command.equals("#quit")) {
		  // quit gracefully
		  try {
			close();
			//display("The server has closed.");
			System.out.println("The server has closed.");
		  } catch (IOException e) {  }	
	  } else if(command.equals("#stop")) {
		  // stop listening for new clients
		  stopListening();
	  } else if(command.equals("#close")) {
		  // stop listening for new clients and disconnect existing clients
		  stopListening();
		  // for every client connected: clientDisconnected();
		  // serverClosed();
	  } else if(command.equals("#setport <port>")) {
		// if server is closed, stop listening for new clients
		  String[] parts = command.split(" ");
		  int p = Integer.parseInt(parts[1]);
		  setPort(p);
		  
	  } else if(command.equals("#start")) {
		  // server starts listening for new clients if server is stopped
		  // if server stopped, listen for new clients
		  // else -> output an error message
		  
		  
	  } else if(command.equals("#getport")) {
		  getPort(); 
		  
	  }
  
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    ServerConsole cs = new ServerConsole(sv);
    
    cs.display("Server starting on port " + port);
    
   try 
    {
     sv.listen(); //Start listening for connections
    } 
   catch (Exception ex) 
   {
     System.out.println("ERROR - Could not listen for clients!" + ex);
    }
    
  }
}
//End of EchoServer class
