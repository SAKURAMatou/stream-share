package com.dl.person;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private String name;  // 姓名

    private int salary; // 薪资

    private int age; // 年龄

    private String sex; //性别

    private String area;  // 地区

    public Person() {
    }

    public Person(String name, int salary, int age, String sex, String area) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.area = area;
    }

    public List<Person> getPerssonList() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Tom", 8900, 20, "male", "北京"));
        personList.add(new Person("Lily", 7800, 20, "female", "北京"));
        personList.add(new Person("Anni", 8200, 21, "female", "上海"));
        personList.add(new Person("Owen", 9500, 23, "male", "南京"));
        personList.add(new Person("Alisa", 7900, 22, "female", "苏州"));
        return personList;
    }

    @Override
    public String toString() {
        return this.getName() + "-" + this.getSalary() + "-" + this.getAge();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
