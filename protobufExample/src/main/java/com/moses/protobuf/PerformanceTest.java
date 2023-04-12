package com.moses.protobuf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Int32Value;
import com.google.protobuf.InvalidProtocolBufferException;
import com.moses.json.JPerson;
import com.moses.models.Person;

public class PerformanceTest {

    public static void main(String[] args) {

        //json
        JPerson jPerson = new JPerson();
        jPerson.setAge(10);
        jPerson.setName("sam");
        ObjectMapper mapper = new ObjectMapper();
        Runnable json = () -> {
            try {
                byte[] bytes = mapper.writeValueAsBytes(jPerson);
                System.out.println(bytes.length);
                JPerson jPerson1 = mapper.readValue(bytes, JPerson.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        //protobuf

        Person sam = Person.newBuilder()
                .setAge(Int32Value.of(10))
                .setName("sam")
                .build();

        Runnable protobuf = () -> {
            try {
                byte[] byteArray = sam.toByteArray();
                System.out.println(byteArray.length);
                Person sam1 = Person.parseFrom(byteArray);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        for (int i = 0; i <1 ; i++) {
            runPerformanceTest(json, "JSON");
            runPerformanceTest(protobuf, "PROTO");
        }



    }
    
    private static void runPerformanceTest(Runnable runnable, String method){
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            runnable.run();
        }
        long time2 = System.currentTimeMillis();

        System.out.println(method + ":" +  (time2 - time1) + " ms");
    }
}
