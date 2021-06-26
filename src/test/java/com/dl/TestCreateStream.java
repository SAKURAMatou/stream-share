package com.dl;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author : DML
 * @create :2021-2021-26   18:54
 */
public class TestCreateStream {

    @Test
    public void method1() {
        /*
         * 集合创建stream
         */
        List<String> arr = Arrays.asList("a", "b", "c", "d");
//        boolean isExist = arr.stream().anyMatch(str -> "a".equals(str));
        boolean isExist = arr.parallelStream().anyMatch(str -> "a".equals(str));
        System.out.println(isExist);
    }

    @Test
    public void method2() {
        /*
         * 数组创建
         */
        int[] intArr = {1, 2, 3, 4, 5, 6};
        IntStream intStream = Arrays.stream(intArr);
        System.out.println(intStream.max().getAsInt());
    }

    @Test
    public void method3() {
        Stream<Integer> intStream = Stream.of(1, 2, 3, 4, 5, 6);
        System.out.println(intStream.min(Integer::compare).get());
        System.out.println(intStream.max(Integer::compare).get());
    }

    @Test
    public void method4() {
        Random r = new Random(50);
        Stream<Integer> stream2 = Stream.generate(() -> (int) (Math.random() * 10)).limit(10);
        List<Integer> integers = Stream.generate(() -> r.nextInt(10)).limit(10).collect(Collectors.toList());
        stream2.forEach(System.out::println);
        System.out.println(integers.toString());
    }

    @Test
    public void method5() {
        Stream.iterate(0, item -> item + 1).limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void method6() {
        Stream<Object> empty = Stream.empty();
    }
}
