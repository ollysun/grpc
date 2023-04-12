package com.moses.protobuf;

import com.moses.models.BodyStyle;
import com.moses.models.Car;
import com.moses.models.Dealer;

import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {


        Car honda = Car.newBuilder()
                .setMake("honda")
                .setModel("Accord")
                .setBodyStyle(BodyStyle.COUPE)
                .setYear(2010)
                .build();

        Car civic = Car.newBuilder()
                .setMake("honda")
                .setModel("civic")
                .setBodyStyle(BodyStyle.SEDAN)
                .setYear(2010)
                .build();

        Dealer dealer = Dealer.newBuilder()
                .putAllModel(Map.of(2005, civic, 2010, honda))
                .build();

        System.out.println(dealer.getModelOrDefault(2019, honda));

        System.out.println(dealer.getModelOrThrow(2005).getBodyStyle());

    }
}
