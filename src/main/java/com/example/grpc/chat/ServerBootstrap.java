package com.example.grpc.chat;

import com.example.grpc.user.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServerBootstrap {

    private static final int PORT = 8086;

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(PORT).addService(new ChatServiceImpl()).build();

        server.start();
        System.out.println("GRPC Server started on port " + PORT);
        server.awaitTermination();
    }
}
