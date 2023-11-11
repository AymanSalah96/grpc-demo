package com.example.grpc.chat;

import io.grpc.stub.StreamObserver;

import java.util.LinkedHashSet;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    private static final LinkedHashSet<StreamObserver<ChatMessageFromServer>> OBSERVERS = new LinkedHashSet<>();

    @Override
    public StreamObserver<ChatMessage> chat(StreamObserver<ChatMessageFromServer> responseObserver) {
        OBSERVERS.add(responseObserver);

        return new StreamObserver<ChatMessage>() {
            @Override
            public void onNext(ChatMessage value) {
                // receiving data from client

                ChatMessageFromServer message = ChatMessageFromServer.newBuilder()
                        .setMessage(value)
                        .build();

                // send this message to all connected clients to this server
                OBSERVERS.forEach(o -> o.onNext(message));
            }

            @Override
            public void onError(Throwable t) {
                OBSERVERS.remove(responseObserver);
            }

            @Override
            public void onCompleted() {
                OBSERVERS.remove(responseObserver);
            }
        };
    }
}
