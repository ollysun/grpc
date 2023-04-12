package com.moses.protobuf;

import com.google.protobuf.Int32Value;
import com.moses.models.Address;
import com.moses.models.Car;
import com.moses.models.Person;

import java.util.List;

public class CompositionDemo {

    public static void main(String[] args) {
        Address address = Address.newBuilder()
                .setStreet("main")
                .setCity("atlanta")
                .setPostBox(123)
                .build();

        Car honda = Car.newBuilder()
                .setMake("honda")
                .setModel("Accord")
                .setYear(2010)
                .build();

        Car civic = Car.newBuilder()
                .setMake("honda")
                .setModel("civic")
                .setYear(2010)
                .build();

        Person sam = Person.newBuilder()
                    .setName("sam")
                    .setAge(Int32Value.of(25))
                    .addAllCar(List.of(honda,civic))
                    .setAddress(address)
                    .build();

        System.out.println(sam);

    }
}
