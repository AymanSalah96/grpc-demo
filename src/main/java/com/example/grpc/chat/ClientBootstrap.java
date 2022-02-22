package com.example.grpc.chat;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;

public class ClientBootstrap {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter you name: ");
        String name = in.next();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8086).usePlaintext().build();

        ChatServiceGrpc.ChatServiceStub stub = ChatServiceGrpc.newStub(channel);

        StreamObserver<ChatMessage> toServer = stub.chat(new StreamObserver<ChatMessageFromServer>() {
            @Override
            public void onNext(ChatMessageFromServer value) {
                System.out.println(String.format("%s: %s", value.getMessage().getFrom(), value.getMessage().getMessage()));
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });


        while (true) {
            String message = in.next();
            toServer.onNext(ChatMessage.newBuilder()
                    .setFrom(name)
                    .setMessage(message)
                    .build());
        }
    }
}
