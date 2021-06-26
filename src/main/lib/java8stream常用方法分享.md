# 一、Stream概述

Java 8 是一个非常成功的版本，这个版本新增的`Stream`，配合同版本出现的 `Lambda` ，给我们操作集合（Collection）提供了极大的便利。

那么什么是`Stream`？

> `Stream`将要处理的元素集合看作一种流，在流的过程中，借助`Stream API`对流中的元素进行操作，比如：筛选、排序、聚合等。

`Stream`可以由数组或集合创建，对流的操作分为两种：

1、中间操作，返回一个stream对象，如：filter、map、flatMap、sorted等

2、终端操作，终端操作会产生一个新的集合或值，每个流只能执行一次，执行终端操作的流1会产生新的集合或者值。如：forEach、toArray、reduce、collect、max、min、anyMatch、findAny等；

`Stream`有几个特性：

1. stream不存储数据，而是按照特定的规则对数据进行计算，一般会输出结果。
2. stream不会改变数据源，通常情况下会产生一个新的集合或一个值。
3. stream具有延迟执行特性，只有调用终端操作时，中间操作才会执行。

# 二、Stream创建

## 集合.stream()方式

```java
List<String> arr = Arrays.asList("a", "b", "c", "d");
        boolean isExist = arr.stream().anyMatch(str -> "a".equals(str));
        boolean isExist = arr.parallelStream().anyMatch(str -> "a".equals(str));
```

## 数组创建Arrays.stream()

```java
        int[] intArr={1,2,3,4,5,6};
        IntStream intStream = Arrays.stream(intArr);
        System.out.println(intStream.max().getAsInt());
```

## Stream接口的静态工厂方法

1、Stream.of()方法生成有限长度的stream对象；

```java
Stream<Integer> intStream =  Stream.of(1, 2, 3, 4, 5, 6);
        System.out.println(intStream.min(Integer::compare).get());
        System.out.println(intStream.max(Integer::compare).get());
```

2、generator生成不限长度的对象，*无序*，一般需要通过limit方法限制长度否则将一直生成。

```java
  Random r = new Random(50);
        Stream<Integer> stream2 = Stream.generate(() -> (int) (Math.random() * 10)).limit(10);
        List<Integer> integers = Stream.generate(() -> r.nextInt(10)).limit(10).collect(Collectors.toList());
```

3、iterate 生成不限长度的对象，通过函数f迭代对给指定的元素种子而产生无限连续有序Stream，其中包含的元素可以认为是：seed，f(seed),f(f(seed))无限循环。seed可以理解为产生的stream中的第一个元素，后续元素都是通过seed和函数产生的。

```java
Stream.iterate(0, item -> item + 1).limit(10)
                .forEach(System.out::println);
```

# 中间操作（Intermediate Operations）

## lambda[表达式](https://www.runoob.com/java/java8-lambda-expressions.html)

```java
  (parameters) -> expression 
或 (parameters) ->{ statements; }
// 1. 不需要参数,返回值为 5  
() -> 5   
// 2. 接收一个参数(数字类型),返回其2倍的值  
x -> 2 * x 
    // 3. 接受2个参数(数字),并返回他们的差值  
(x, y) -> x – y  
  
// 4. 接收2个int型整数,返回他们的和  
(int x, int y) -> x + y  
  
// 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)  
(String s) -> System.out.print(s)
```



## filter

筛选（filter）需要传一个lambda表达式作为参数，lambda表达式需要返回Boolean类型，其中筛选会保留结果为True的元素。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201109144706541.jpg)

#### 示例一：随机生成长度为10的整数流，并筛选出值大于5的元素

```java
Stream<Integer> stream2 = Stream.generate(() -> (int) (Math.random() * 10)).limit(10);
        stream2.filter(x -> x > 5).forEach(System.out::println);
```

#### 示例二：找出工资高于8000的人，并形成新的集合

```java
 Person person = new Person();
        List<Person> personList = person.getPerssonList();
        List<Person> highSalary =
                personList.stream().filter(x -> x.getSalary() > 8000).collect(Collectors.toList());
        highSalary.forEach(System.out::println);
```

结果：

```
Tom-8900-male
Anni-8200-female
Owen-9500-male
```

## 映射map/flatMap

对流中的每一个元素进行指定的操作，并生成一个新的流；映射需要传一个lambda表达式作为参数，也需要返回一个结果，可以是任意类型

### map

#### 示例一：将字符串数组的元素转为大写

```java
 String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
        List<String> collect = Stream.of(strArr).map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("转化大写"+collect.toString());
```

输出：转化大写[ABCD, BCDD, DEFDE, FTR]

#### 示例二：将所有数都*10

```java
 List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> collect = intList.stream().map(x -> x * 10).collect(Collectors.toList());
        System.out.println(collect.toString());
```

输出：[10, 30, 50, 70, 90, 110]

#### 示例三：薪资低于8500的人涨薪500

```java
Person person = new Person();
        List<Person> personList = person.getPerssonList();
        List<Person> collect = personList.stream().filter(p -> p.getSalary() < 8500).map(p -> {
            p.setSalary(p.getSalary() + 500);
            return p;
        }).collect(Collectors.toList());
        collect.forEach(System.out::println);
```

### flatMap

flatMap和map类似，map返回的是对象，flatMap返回的是一个stream；flatMap将入参进过映射操作之后返回一个stream，并将所有stream合成一个行的stream。

#### 示例二：两个部门，找出所有薪资低于8500的人涨薪500

```java
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
```

输出：[Lily-8300-female, Anni-8700-female, Alisa-8400-female, 天迹-8200-man]

## distinct

去重，将流中重复的元素去除

```
List<String> mzc = Stream.of("ma", "zhi", "chu", "zhi", "shuo", "ma")
        .distinct()
        .collect(Collectors.toList());
```

输出：[ma, zhi, chu, shuo]

## sort

排序，按照薪资排序

```java
Person person = new Person();
List<Person> personList = person.getPerssonList();
List<Person> collect1 =
        personList.stream().sorted(Comparator.comparing(Person::getSalary)).collect(Collectors.toList());
System.out.println(collect1);
System.out.println("------分割线-----");
List<Person> collect2 =
        personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed()).collect(Collectors.toList());
System.out.println(collect2);
```

输出：

[Lily-7800-20, Alisa-7900-22, Anni-8200-21, Tom-8900-20, Owen-9500-23]
------分割线-----
[Owen-9500-23, Tom-8900-20, Anni-8200-21, Alisa-7900-22, Lily-7800-20]

## limit/skip

limit 取前n个元素，skip跳过前n个元素，如果流中的元素小于或者等于n，就会返回一个空的流。

```
List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
List<Integer> collect1 = intList.stream().limit(5).collect(Collectors.toList());
List<Integer> collect2 = intList.stream().skip(3).collect(Collectors.toList());
System.out.println(collect1.toString());
System.out.println("------分割线-----");
System.out.println(collect2.toString());
```

输出：

[1, 3, 5, 7, 9]
------分割线-----
[7, 9, 11]