package com.dl;

import org.junit.Test;

import java.util.stream.Stream;

/**
 * @author : DML
 * @create :2021-2021-27   12:34
 */
public class TestForeach {
    @Test
    public void foreach1() {
        Stream.of(1, 2, 3, 5, 6, 4, 9).forEach(System.out::print);
        System.out.println("\n");
        Stream.of(1, 2, 3, 5, 6, 4, 9).parallel().forEach(System.out::print);
    }
    @Test
    public void foreach2() {
        Stream.of(1, 2, 3, 5, 6, 4, 9).forEachOrdered(System.out::print);
        System.out.println("\n");
        Stream.of(1, 2, 3, 5, 6, 4, 9).parallel().forEachOrdered(System.out::print);
    }
}
