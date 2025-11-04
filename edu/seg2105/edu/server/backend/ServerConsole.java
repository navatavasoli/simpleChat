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
}

