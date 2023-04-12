package com.moses.protobuf;

import com.moses.models.Credentials;
import com.moses.models.EmailCredentials;
import com.moses.models.PhoneOTP;

public class OneOfDemo {

    public static void main(String[] args) {

        EmailCredentials emailCredentials = EmailCredentials.newBuilder()
                .setEmail("ollysun@gmail.com")
                .setPassword("admin123")
                .build();

        PhoneOTP phoneOtp = PhoneOTP.newBuilder()
                .setCode(1234)
                .setNumber(988712234)
                .build();

        Credentials credentials = Credentials.newBuilder()
                .setEmail(emailCredentials)
                .setPhone(phoneOtp)
                .build();
        login(credentials);
    }


    private static void login(Credentials credentials){

        switch (credentials.getModeCase()){
            case EMAIL:
                System.out.println(credentials.getEmail());
                break;
            case PHONE:
                System.out.println(credentials.getPhone());
                break;
        }

    }
}
