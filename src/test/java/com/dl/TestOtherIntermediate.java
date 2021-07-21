package com.dl;

import com.dl.person.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : DML
 * @create :2021-2021-26   21:28
 */
public class TestOtherIntermediate {
    @Test
    public void distinct() {
        List<String> mzc = Stream.of("ma", "zhi", "chu", "zhi", "shuo", "ma")
                .distinct()
                .collect(Collectors.toList());
        System.out.println(mzc);
    }

    @Test
    public void sorted1() {
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        List<Person> collect1 =
                personList.stream().sorted(Comparator.comparing(Person::getSalary)).collect(Collectors.toList());
        System.out.println(collect1);
        System.out.println("------分割线-----");
        List<Person> collect2 =
                personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed()).collect(Collectors.toList());
        System.out.println(collect2);
    }

    @Test
    public void sorted2() {
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        List<Person> collect =
                personList.stream().sorted(Comparator.comparing(Person::getSalary)
                        .thenComparing(Person::getAge))
                        .collect(Collectors.toList());
        System.out.println(collect);
    }
    @Test
    public void sorted3(){
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        List<Person> collect =
                personList.stream().sorted((p1,p2)->p1.getAge()-p2.getAge()).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void limitSkip() {
        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> collect1 = intList.stream().limit(5).collect(Collectors.toList());
        List<Integer> collect2 = intList.stream().skip(3).collect(Collectors.toList());
        System.out.println(collect1.toString());
        System.out.println("------分割线-----");
        System.out.println(collect2.toString());
    }
}
