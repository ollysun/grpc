package com.moses.server;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountDatabase {

    /*
     THis DB
      1 -> 10
      2 -> 20
     */

    private static final Map<Integer, Integer> map = IntStream.rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toMap(
                    Function.identity(),
                    v -> 100)
            );


    public static int getBalance(int accountId){
        return map.get(accountId);
    }

    public static Integer addBalance(int accountId, int amount){
        return map.computeIfPresent(accountId,(k,v) -> v + amount);
    }

    public static Integer deductBalance(int accountId, int amount){
        return map.computeIfPresent(accountId, (k,v) -> v - amount);
    }

    public static void printAccountDetails() {
        System.out.println(
                map
        );
    }

}
