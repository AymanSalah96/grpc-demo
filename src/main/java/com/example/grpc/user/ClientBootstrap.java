package com.example.grpc.user;

import com.example.grpc.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

public class ClientBootstrap {

    public static void main(String[] args) {
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 8086).usePlaintext().build();

        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

        for (int userId = 1; userId <= 10; userId++) {
            User user = User.newBuilder()
                    .setId(userId)
                    .setEmail("user" + userId + "@gamil.com")
                    .setUsername("incorta" + userId)
                    .build();

            AddUserResponse response = stub.addUser(AddUserRequest.newBuilder().setUser(user).build());
            System.out.println(response);
        }

        System.out.println("Getting all users list");
        System.out.println(stub.listUsers(ListUsersRequest.newBuilder().build()));
    }
}
