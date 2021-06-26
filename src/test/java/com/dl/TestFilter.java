package com.dl;

import com.dl.person.Person;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : DML
 * @create :2021-2021-26   20:01
 */
public class TestFilter {

    /**
     * 找出值大于5的所有元素
     */
    @Test
    public void filter1() {
        Stream<Integer> stream2 = Stream.generate(() -> (int) (Math.random() * 10)).limit(10);
        stream2.filter(x -> x > 5).forEach(System.out::println);
    }

    /**
     * 找出工资高于8000的人
     */
    @Test
    public void filter2() {
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        List<Person> highSalary =
                personList.stream().filter(x -> x.getSalary() > 8000).collect(Collectors.toList());
        highSalary.forEach(System.out::println);
    }
}
