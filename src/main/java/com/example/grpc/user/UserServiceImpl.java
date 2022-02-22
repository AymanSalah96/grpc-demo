package com.example.grpc.user;

import com.example.grpc.*;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.LinkedBlockingQueue;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private static final LinkedBlockingQueue<User> USERS = new LinkedBlockingQueue<>();

    @Override
    public void addUser(AddUserRequest request, StreamObserver<AddUserResponse> responseObserver) {
        User user = request.getUser();
        USERS.add(user);

        responseObserver.onNext(AddUserResponse.newBuilder().setStatus("User with id # " + user.getId() + " added successfully").build());
        responseObserver.onCompleted();
    }

    @Override
    public void listUsers(ListUsersRequest request, StreamObserver<ListUsersResponse> responseObserver) {
        responseObserver.onNext(ListUsersResponse.newBuilder().addAllUsers(USERS).build());
        responseObserver.onCompleted();
    }
}
