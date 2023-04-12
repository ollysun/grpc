package com.moses.protobuf;

import com.moses.models.Television;
import com.moses.models.Type;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VersionCompatibilityTest {

    public static void main(String[] args) throws Exception {


//        Television sony = Television.newBuilder()
//                                     .setModel(2016)
//                                    .setBrand("sony")
//                                    .setType(Type.OLED)
//                                    .build();
//
        Path pathv1 = Paths.get("tv-v1");
        Path pathv2 = Paths.get("tv-v2");
//        Files.write(pathv2, sony.toByteArray());


        byte[] bytes = Files.readAllBytes(pathv1);
        System.out.println(Television.parseFrom(bytes));
    }
}
