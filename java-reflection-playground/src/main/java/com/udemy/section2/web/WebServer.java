package com.udemy.section2.web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class WebServer {

	public void startServer() throws IOException {
		ServerConfiguration serverConfigurationInstance = ServerConfiguration.getServerConfigurationInstance();
		HttpServer httpServer =
			HttpServer.create(serverConfigurationInstance.getServerAddress(), 0);
		httpServer.createContext("/greeting")
			.setHandler(exchange -> {
				String responseMessage = serverConfigurationInstance.getGreetingMessage();
				exchange.sendResponseHeaders(200, responseMessage.length());
				OutputStream responseBody = exchange.getResponseBody();
				responseBody.write(responseMessage.getBytes());
				responseBody.flush();
				responseBody.close();
			});

		InetSocketAddress serverAddress = serverConfigurationInstance.getServerAddress();
		System.out.printf("Starting server on address %s:%d%n",
			serverAddress.getHostName(), serverAddress.getPort());
		httpServer.start();
	}
}
