package com.vanc.javabasic.leetcode;

import java.util.*;

/**
 * 测试洗牌算法
 */
public class Shuffle {
    public static void main(String[] args) {
        Random rand=new Random();
        Integer[] ia={0,1,2,3,4,5,6,7,8,9};
        List<Integer> list=new ArrayList<Integer>(Arrays.asList(ia));
        System.out.println("Before shufflig: "+list);
        Collections.shuffle(list,rand);
        System.out.println("After shuffling: "+list);
        System.out.println("array: "+Arrays.toString(ia));
        List<Integer> list1=Arrays.asList(ia);
        System.out.println("Before shuffling: "+list1);
        Collections.shuffle(list1,rand);
        System.out.println("After shuffling: "+list1);
        System.out.println("array: "+Arrays.toString(ia));

    }
}
