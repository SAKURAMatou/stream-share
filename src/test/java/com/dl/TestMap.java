package com.dl;

import com.dl.person.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : DML
 * @create :2021-2021-26   20:32
 */
public class TestMap {
    /**
     * 将字符串数组的元素转为大写
     */
    @Test
    public void map1() {
        String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
        List<String> collect = Stream.of(strArr).map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("转化大写" + collect.toString());
    }

    /**
     * 将所有数都*10
     */
    @Test
    public void map2() {
        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> collect = intList.stream().map(x -> x * 10).collect(Collectors.toList());
        System.out.println(collect.toString());
    }

    /**
     * 涨薪,薪资低于8500的人涨薪500
     */
    @Test
    public void map3() {
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        List<Person> collect = personList.stream().filter(p -> p.getSalary() < 8500).map(p -> {
            p.setSalary(p.getSalary() + 500);
            return p;
        }).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    @Test
    public void map4() {
        List<Integer> num1 = Arrays.asList(1, 2, 3);
        List<Integer> num2 = Arrays.asList(4, 5, 6);
        List<Integer> num3 = Arrays.asList(7, 8, 9);
        List<List<Integer>> numArr = Arrays.asList(num1, num2, num3);
        List<Integer> collect =
                numArr.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
        System.out.println(collect.toString());
    }

    /**
     * 两个部门，找出所有薪资低于8500的人涨薪500
     */
    @Test
    public void map5() {
        Person person = new Person();
        List<Person> ou1 = person.getPerssonList();
        List<Person> ou2 = Arrays.asList(
                new Person("小明", 8500, 20, "female", "Washington"),
                new Person("张飞", 9100, 20, "female", "Washington"),
                new Person("天迹", 7700, 20, "man", "Washington"));
        List<List<Person>> ouList = Arrays.asList(ou1, ou2);
        List<Person> collect = ouList.stream().flatMap(ou -> ou.stream().filter(p -> p.getSalary() < 8500)).map(p -> {
            p.setSalary(p.getSalary() + 500);
            return p;
        }).collect(Collectors.toList());
        System.out.println(collect.toString());
    }
}

