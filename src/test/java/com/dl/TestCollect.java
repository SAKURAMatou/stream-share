package com.dl;

import com.dl.person.Person;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : DML
 * @create :2021-2021-27   15:16
 */
public class TestCollect {

    @Test
    public void collect1() {
        Stream.of(1, 2, 3, 4, 3, 4).collect(Collectors.toList()).forEach(System.out::print);
        System.out.println("\n");
        Stream.of(1, 2, 3, 4, 3, 4).collect(Collectors.toSet()).forEach(System.out::print);
        System.out.println("\n");
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        List<Person> collect = personList.stream().filter(p -> p.getSalary() > 8500).collect(Collectors.toList());
        collect.forEach(System.out::println);
        System.out.println("-------toSet()------------");
        Set<Person> collect1 = personList.stream().filter(p -> p.getSalary() > 8500).collect(Collectors.toSet());
        collect1.forEach(System.out::println);
    }

    /**
     * 将person对象转化为姓名-薪资的map
     */
    @Test
    public void collect2() {
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
//        Map<String, Person> personMap = personList.stream().collect(() -> new HashMap<>(),
//                (map, p) -> map.put(p.getName(), p), (m, n) -> m.putAll(n));
//        System.out.println(personMap.toString());
        Map<String, Integer> personMap2 = personList.stream().collect(Collectors.toMap(Person::getName,
                Person::getSalary));
        System.out.println(personMap2.toString());
    }

    @Test
    public void collect3() {
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        //流中元素总数
        Long count = personList.stream().collect(Collectors.counting());
        //平均值
        Double avgSalsry = personList.stream().collect(Collectors.averagingInt(p -> p.getSalary()));
        //最值
        Person maxAge = personList.stream().collect(Collectors.maxBy(Comparator.comparing(Person::getSalary))).get();
        //求和
        Integer totalSalary = personList.stream().collect(Collectors.summingInt(Person::getSalary));
        //对一个量求平均值、最值
        IntSummaryStatistics collect = personList.stream().collect(Collectors.summarizingInt(Person::getSalary));
        System.out.println("元素总数" + count);
        System.out.println("薪资平均值" + avgSalsry);
        System.out.println("薪资最值" + maxAge);
        System.out.println("薪资总和" + totalSalary);
        System.out.println("薪资总综合分析" + collect);
    }

    @Test
    public void collect4() {
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        //按照薪资是否高于8000分为高底薪
        Map<Boolean, List<Person>> salaryMap =
                personList.stream().collect(Collectors.partitioningBy(person1 -> person1.getSalary() > 8000));
        //按照地区分组
        Map<String, List<Person>> areaGroup = personList.stream().collect(Collectors.groupingBy(Person::getArea));
        System.out.println("高低薪" + salaryMap.toString());
        System.out.println("地区分组" + areaGroup.toString());
        Map<String, Map<String, List<Person>>> gruop2 =
                personList.stream().collect(Collectors.groupingBy(Person::getSex,
                        Collectors.groupingBy(Person::getArea)));
        System.out.println("性别+地区分组" + gruop2.toString());
    }

    @Test
    public void collect5() {
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        //统计薪资高于，低于8000的人数
        Map<Boolean, Long> count =
                personList.stream().collect(Collectors.partitioningBy(person1 -> person1.getSalary() > 8000,
                        Collectors.counting()));
        //求每个地区的人数
        Map<String, Long> areaCount = personList.stream().collect(Collectors.groupingBy(Person::getArea,
                Collectors.counting()));
        System.out.println("薪资统计" + count);
        System.out.println("地区统计" + areaCount);
        Map<String, Map<String, Long>> collect = personList.stream().collect(Collectors.groupingBy(Person::getSex,
                Collectors.groupingBy(Person::getArea,
                        Collectors.counting())));
        System.out.println("性别+地区分组" + collect);
    }

    @Test
    public void collect() {
        String join1 =
                Stream.of('x', 'c', 'f', 'r', 'q').map(String::valueOf).collect(Collectors.joining("-"));
        String join2 = Stream.of('x', 'c', 'f', 'r', 'q').map(String::valueOf).collect(Collectors.joining("-", "[",
                "]"));
        System.out.println("拼接1入参：" + join1);
        System.out.println("拼接3入参：" + join2);
    }

}
