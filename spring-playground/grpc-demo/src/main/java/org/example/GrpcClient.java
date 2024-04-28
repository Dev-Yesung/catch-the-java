package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.baeldung.grpc.HelloRequest;
import org.baeldung.grpc.HelloResponse;
import org.baeldung.grpc.HelloServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		ManagedChannel channel = ManagedChannelBuilder
			.forAddress("localhost", 8080)
			.usePlaintext()
			.build();

		boolean isExit = false;
		do {
			System.out.println("Input your first-name and last-name. Ex) James King");
			System.out.println("(if you want to exit program, input \"exit\")");
			System.out.print(": ");
			String[] params = br.readLine()
				.split(" ");

			if ("exit".equals(params[0])) {
				isExit = true;
				continue;
			}
			if (params.length != 2) {
				System.out.println("[ERROR] Not supported operation\n\n");
				continue;
			}

			HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);
			HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
				.setFirstName(params[0])
				.setLastName(params[1])
				.build());
			System.out.println(helloResponse.getGreeting() + "\n\n");
		} while (!isExit);

		System.out.println("\n\nGood bye :)");
		channel.shutdown();
	}

}
