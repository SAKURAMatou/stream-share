package com.dl;

import com.dl.person.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author : DML
 * @create :2021-2021-27   12:46
 */
public class TestReduce {

    /**
     * 求整数数组的元素的和，最大值
     */
    @Test
    public void reduce1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //无初值时返回optional对象
        Integer total = list.stream().reduce(0, Integer::sum);
        Integer total2 = list.stream().reduce(Integer::sum).get();
        Integer reduced1 = list.stream().reduce((x, y) -> x * y).get();
        Integer max1 = list.stream().reduce(Integer::max).get();
        Integer max2 = list.stream().reduce((x, y) -> x > y ? x : y).get();
        System.out.println("求和有初值：" + total);
        System.out.println("求和无初值：" + total2);
        System.out.println("乘积：" + reduced1);
        System.out.println("最大值" + max1);
    }

    /**
     * 求所有人薪资和，以及薪资最高的人
     */
    @Test
    public void reduce2() {
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        Person maxSalaryPerson = personList.stream().reduce((p1, p2) -> {
            return p1.getSalary() > p2.getSalary() ? p1 : p2;
        }).get();
        Integer totalSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum).get();
        Integer integer = personList.stream().map(Person::getSalary).max(Integer::compareTo).get();
        System.out.println("薪资之和：" + totalSalary);
        System.out.println("薪资最高的人：" + maxSalaryPerson.toString());
        System.out.println("最高薪资：" + integer);
    }
    @Test
    public void reduce3(){
        Person person = new Person();
        List<Person> personList = person.getPerssonList();
        Integer maxSalary = personList.stream().map(Person::getSalary).max(Integer::compareTo).get();
        Person person1 = personList.stream().max(Comparator.comparing(Person::getSalary)).get();
        Integer minSalary = personList.stream().map(Person::getSalary).min(Integer::compareTo).get();
        Person person2 = personList.stream().min(Comparator.comparing(Person::getSalary)).get();
        System.out.println("最高薪资：" + maxSalary);
        System.out.println("薪资最高的人：" + person1.toString());
        System.out.println("最低薪资：" + minSalary);
        System.out.println("薪资最低的人：" + person2.toString());
    }
}
