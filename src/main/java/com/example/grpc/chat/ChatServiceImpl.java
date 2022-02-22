package com.example.grpc.chat;

import io.grpc.stub.StreamObserver;

import java.util.LinkedHashSet;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    private static LinkedHashSet<StreamObserver<ChatMessageFromServer>> observers = new LinkedHashSet<>();

    @Override
    public StreamObserver<ChatMessage> chat(StreamObserver<ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<ChatMessage>() {
            @Override
            public void onNext(ChatMessage value) {
                // receiving data from client

                ChatMessageFromServer message = ChatMessageFromServer.newBuilder()
                        .setMessage(value)
                        .build();

                // send this message to all connected clients to this server
                observers.stream().forEach(o -> o.onNext(message));
            }

            @Override
            public void onError(Throwable t) {
                observers.remove(responseObserver);
            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
            }
        };
    }
}
