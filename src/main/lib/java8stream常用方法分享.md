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

# 三、中间操作（Intermediate Operations）

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

## sort排序

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
lambda表达式排序，返回值为数字
 Person person = new Person();
        List<Person> personList = person.getPerssonList();
        List<Person> collect =
                personList.stream().sorted((p1,p2)->p1.getAge()-p2.getAge()).collect(Collectors.toList());
        System.out.println(collect);
```

输出：

[Lily-7800-20, Alisa-7900-22, Anni-8200-21, Tom-8900-20, Owen-9500-23]
------分割线-----
[Owen-9500-23, Tom-8900-20, Anni-8200-21, Alisa-7900-22, Lily-7800-20]

## limit/skip

limit 取前n个元素，skip跳过前n个元素，如果流中的元素小于或者等于n，就会返回一个空的流。

```java
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

# 四、终端操作（Terminal Operations）

### 先理解一个概念：Optional 。

> `Optional`类是一个可以为`null`的容器对象。如果值存在则`isPresent()`方法会返回`true`，调用`get()`方法会返回该对象。
> 更详细说明请见：[菜鸟教程Java 8 Optional类](https://www.runoob.com/java/java8-optional-class.html)

### foreach、forEachOrdered

遍历流中的元素。foreach和forEachOrdered区别在于并行流时，forEachOrdered按照流的顺序，foreach不一定按照流的顺序

#### foreach

```java
Stream.of(1, 2, 3, 5, 6, 4, 9).forEach(System.out::print);
System.out.println("\n");
Stream.of(1, 2, 3, 5, 6, 4, 9).parallel().forEach(System.out::print);
```

输出：

1235649

6549312

#### forEachOrdered

```java
Stream.of(1, 2, 3, 5, 6, 4, 9).forEachOrdered(System.out::print);
System.out.println("\n");
Stream.of(1, 2, 3, 5, 6, 4, 9).parallel().forEachOrdered(System.out::print);
```

输出：

1235649

1235649

### 规约（reduce）

规约：将流缩减成一个值，可以用于求最值，求和等；reduce操作需要提供一个初值（seed），将初值和流中元素进行指定的操作。没有初值时返回option对象，有初值时直接返回结果

#### 示例一：求整数数组的元素的和，最大值

```java
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
```

输出：

求和有初值：55
求和无初值：55
乘积：3628800
最大值10

#### 示例二：求所有人薪资和，以及薪资最高的人

```java
Person person = new Person();
List<Person> personList = person.getPerssonList();
Person maxSalary = personList.stream().reduce((p1, p2) -> {
    return p1.getSalary() > p2.getSalary() ? p1 : p2;
}).get();
Integer totalSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum).get();
Integer integer = personList.stream().map(Person::getSalary).max(Integer::compareTo).get();
System.out.println("薪资之和：" + totalSalary);
System.out.println("薪资最高的人：" + maxSalary.toString());
System.out.println("最高薪资：" + integer);
```

输出：

薪资之和：42300
薪资最高的人：Owen-9500-23
最高薪资：9500

### max/ min/ count

返回流中的最值和元素数量

#### 示例：找出薪资最高/低的人以及最高/低薪资，

```java
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
```

输出：

最高薪资：9500
薪资最高的人：Owen-9500-23
最低薪资：7800
薪资最低的人：Lily-7800-20

### 匹配(anyMatch,allMatch,findFirst)

匹配的操作有：

anyMatch：Stream 中只要有一个元素符合传入的断言，就返回 true，否则返回false

allMatch：Stream 中所有元素都符合传入的断言时返回 true，否则返回false，流为空时总是返回true。

noneMatch：Stream 中所有元素都不满足传入的断言时返回 true，否则返回false

```java
List<Integer> ins = Arrays.asList(1, 2, 3, 4, 5);
//是否有大于3的数据
boolean b = ins.stream().anyMatch(x -> x > 3);
//是否全部都大于3
boolean b1 = ins.stream().allMatch(x -> x > 3);
//全部都不大于5
boolean b3 = ins.stream().noneMatch(x -> x > 5);
System.out.println("是否有大于3的数据：" + b);
System.out.println("是否全部都大于3：" + b1);
System.out.println("是否全部都不大于5：" + b3);
//空对象
Stream<Integer> emptyStream = Stream.empty();
boolean empty1 = emptyStream.anyMatch(x -> x > 3);
emptyStream = Stream.empty();
boolean empty2 = emptyStream.anyMatch(x -> x == 3);
emptyStream = Stream.empty();
boolean empty3 = emptyStream.anyMatch(x -> x < 3);
System.out.println("空stream的anyMatch:" + empty1 + "," + empty2 + "," + empty3);
List<Integer> numist = Collections.emptyList();
boolean c1 = numist.stream().allMatch(e -> e > 1);
boolean c2 = numist.stream().allMatch(e -> e == 1);
boolean c3 = numist.stream().allMatch(e -> e < 1);
System.out.println("空对象创建的stream的anyMatch" + c1 + "," + c2 + "," + c3);
```

findFirst：总是返回流中的第一个元素，如果流为空，返回一个空的Optional.

findAny：返回流中的任意一个元素即可，如果流为空，返回一个空的Optional.

并行流findAny的结果每次不一样

```java
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Integer findAny1 = list.stream().findAny().get();
        Integer findAny2 = list.stream().findAny().get();
        Integer findFirst = list.stream().findFirst().get();
        Integer findAny3 = list.parallelStream().findAny().get();
        Integer findAny4 = list.parallelStream().findAny().get();
        System.out.println("查找任意：" + findAny1 + "," + findAny2);
        System.out.println("查找任意并行流：" + findAny3 + "," + findAny4);
        System.out.println("查找第一个" + findFirst);
```

```java
List<Integer> list = Collections.emptyList();
Optional<Integer> first = list.stream().findFirst();
Optional<Integer> any = list.stream().findAny();
System.out.println("空对象的stream：" + first.isPresent() + "," + any.isPresent());
Stream<Object> empty = Stream.empty();
Optional<Object> first1 = empty.findFirst();
empty = Stream.empty();
Optional<Object> any1 = empty.findAny();
System.out.println("空的stream：" + first1.isPresent() + "," + any1.isPresent());
```

## 收集collect

将操作之后的流转化为一个新的集合，流不保存数据。主要通过`java.util.stream.Collectors`的内置静态方法实现。

### collect()的入参：

```java
Collector<? super T, A, R> collector //Collectors的内置静态方法

Supplier<R> supplier,//生成目标类型实例的方法,确定目标容器是什么
BiConsumer<R, ? super T> accumulator,//将数据填充到目标容器中的方法，生成实例
BiConsumer<R, R> combiner //将accumulator方法生成的多个对象整合成一个
```

### toList、toSet

区别在于list和set的区别；list和set均继承Collection。

list是有序的，ArrayList和LinkedList实现list接口

set是无序的，HashSet，treeSet实现了set接口

示例：找出薪资高于8500的人

```java
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
```

### toMap

由于Map中有Key和Value这两个值，故该方法与toSet、toList等的处理方式是不一样的，最少应接受两个参数，一个用来生成key，另外一个用来生成value；Collectors.toMap(genereteKey,generateValue)

将person对象转化为姓名-薪资的map

```java
Map<String, Integer> personMap2 = personList.stream().collect(Collectors.toMap(Person::getName,
        Person::getSalary));
System.out.println(personMap2.toString());
```

### collect操作还可以将流转化为一个值

`Collectors`提供了一系列用于数据统计的静态方法：

- 计数：`counting`
- 平均值：`averagingInt`、`averagingLong`、`averagingDouble`
- 最值：`maxBy`、`minBy`
- 求和：`summingInt`、`summingLong`、`summingDouble`
- 统计以上所有：`summarizingInt`、`summarizingLong`、`summarizingDouble`

```java
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
```

### 分组

#### 将一个流按照条件拆分成多个，返回map对象

![](https://img-blog.csdn.net/20170209093620262?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvSU9fRmllbGQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

partitioningBy()传入一个判断的函数，将结果分为False和True两组；

groupingBy()传入分组依据，可以分为多个组（map）

```java
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
```

输出

高低薪{false=[Lily-7800-20, Alisa-7900-22], true=[Tom-8900-20, Anni-8200-21, Owen-9500-23]}
地区分组{上海=[Anni-8200-21], 苏州=[Alisa-7900-22], 南京=[Owen-9500-23], 北京=[Tom-8900-20, Lily-7800-20]}
性别+地区分组{female={上海=[Anni-8200-21], 苏州=[Alisa-7900-22], 北京=[Lily-7800-20]}, male={南京=[Owen-9500-23], 北京=[Tom-8900-20]}}

#### 分组并统计数量

```java
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
System.out.println("性别+地区分组"+collect);
```

输出：

薪资统计{false=2, true=3}
地区统计{上海=1, 苏州=1, 南京=1, 北京=2}
性别+地区分组{female={上海=1, 苏州=1, 北京=1}, male={南京=1, 北京=1}}

### 拼接（joining）

将stream中的元素用特定的连接符，入参可以有三个。

joining(m,l,r)。m：拼接元素的字符，两个元素之间

​                         l：最左边的字符，即第一个元素左侧字符，

​                         r：最右边的字符，即最后一个元素右侧的字符；

```java
String join1 =
        Stream.of('x', 'c', 'f', 'r', 'q').map(String::valueOf).collect(Collectors.joining("-"));
String join2 = Stream.of('x', 'c', 'f', 'r', 'q').map(String::valueOf).collect(Collectors.joining("-", "[",
        "]"));
System.out.println("拼接1入参：" + join1);
System.out.println("拼接3入参：" + join2);
```

输出：

拼接1入参：x-c-f-r-q
拼接3入参：[x-c-f-r-q]