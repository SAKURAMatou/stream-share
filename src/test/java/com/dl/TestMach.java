package com.dl;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author : DML
 * @create :2021-2021-27   13:31
 */
public class TestMach {
    @Test
    public void match1() {
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
    }

    @Test
    public void match2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Integer findAny1 = list.stream().findAny().get();
        Integer findAny2 = list.stream().findAny().get();
        Integer findFirst = list.stream().findFirst().get();
        Integer findAny3 = list.parallelStream().findAny().get();
        Integer findAny4 = list.parallelStream().findAny().get();
        System.out.println("查找任意：" + findAny1 + "," + findAny2);
        System.out.println("查找任意并行流：" + findAny3 + "," + findAny4);
        System.out.println("查找第一个" + findFirst);
    }

    @Test
    public void match3() {
        List<Integer> list = Collections.emptyList();
        Optional<Integer> first = list.stream().findFirst();
        Optional<Integer> any = list.stream().findAny();
        System.out.println("空对象的stream：" + first.isPresent() + "," + any.isPresent());
        Stream<Object> empty = Stream.empty();
        Optional<Object> first1 = empty.findFirst();
        empty = Stream.empty();
        Optional<Object> any1 = empty.findAny();
        System.out.println("空的stream：" + first1.isPresent() + "," + any1.isPresent());
    }
}
