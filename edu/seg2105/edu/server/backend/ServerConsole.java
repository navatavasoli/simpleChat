package edu.seg2105.edu.server.backend;
import edu.seg2105.client.common.ChatIF;
import java.io.*;
// added this to access AbstractServer methods 
import ocsf.server.*;
import java.util.Scanner; // to read from the server console same as in ClientConsole

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
	
	public void accept() {
		java.util.Scanner in = new java.util.Scanner(System.in);
		while(true) { // read continuously 
		String msg = in.nextLine();
		if(msg.startsWith("#")) {
			server.handleCommand(msg);
		} else {
			display(msg); // if its not  a command treat it like any other broadcast server message 
		}
	}
	}
	
	 
}

