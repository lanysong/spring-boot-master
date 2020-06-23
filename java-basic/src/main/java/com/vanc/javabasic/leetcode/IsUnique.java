package com.vanc.javabasic.leetcode;

import java.util.HashSet;

public class IsUnique {
    public static void main(String[] args) {
        IsUnique isUnique = new IsUnique();
        System.out.println(isUnique.isUnique("abc"));
    }


    public boolean isUnique(String str) {
        char[] chars = str.toCharArray();
        HashSet<Character> set = new HashSet<Character>();
        for (char aChar : chars) {
            set.add(aChar);
        }
        return chars.length==set.size();

    }
}
