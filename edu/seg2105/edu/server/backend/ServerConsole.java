package edu.seg2105.edu.server.backend;
import edu.seg2105.client.common.ChatIF;

public class ServerConsole implements ChatIF {
	
	private final EchoServer server;

	public ServerConsole(EchoServer server) {
		this.server = server;
	}

	@Override
	public void display(String message) {
		System.out.println("SERVER MSG>" + message);
		server.sendToAllClients("SERVER MSG>" + message);
	}
	
	  /**
	   * Handles commands from the user. 
	   * @param command the command prompt the user enters into the console.
	   */
	  private void handleCommand(String command) {
		  if(command.equals("#quit")) {
			  // quit gracefully
		  } else if(command.equals("#stop")) {
			  // stop listening for new clients
			  stopListening();
		  } else if(command.equals("#close")) {
			  // stop listening for new clients and disconnect existing clients
		  } else if(command.equals("#setport <port>")) {
			// if server is closed, stop listening for new clients
			  
		  } else if(command.equals("#start")) {
			  // server starts listening for new clients if server is stopped
			  
		  } else if(command.equals("#getport")) {
			  // display the current port number 
			  
		  }
	  
	  }
}

