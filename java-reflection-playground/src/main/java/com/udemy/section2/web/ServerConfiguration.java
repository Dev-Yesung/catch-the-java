package com.udemy.section2.web;

import java.net.InetSocketAddress;

public class ServerConfiguration {
	private static ServerConfiguration serverConfigurationInstance;
	private final InetSocketAddress serverAddress;
	private final String greetingMessage;

	private ServerConfiguration(int port, String greetingMessage) {
		this.greetingMessage = greetingMessage;
		this.serverAddress = new InetSocketAddress("localhost", port);

		if (serverConfigurationInstance == null) {
			serverConfigurationInstance = this;
		}
	}

	public static ServerConfiguration getServerConfigurationInstance() {
		return serverConfigurationInstance;
	}

	public InetSocketAddress getServerAddress() {
		return serverAddress;
	}

	public String getGreetingMessage() {
		return greetingMessage;
	}
}
