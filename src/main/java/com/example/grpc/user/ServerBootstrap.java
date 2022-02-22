package com.example.grpc.user;

import com.example.grpc.user.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServerBootstrap {

    private static final int PORT = 8086;

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(PORT).addService(new UserServiceImpl()).build();

        server.start();
        System.out.println("GRPC Server started on port " + PORT);
        server.awaitTermination();
    }
}
