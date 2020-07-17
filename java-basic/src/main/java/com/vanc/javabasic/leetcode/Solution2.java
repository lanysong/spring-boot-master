package com.vanc.javabasic.leetcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-17 11:26
 */
public class Solution2 {
	
	
	/**
	 * 暴力法
	 *
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring(String s) {
		int maxLength = 0;
		for (int i = 0; i < s.length(); i++) {
			List<Character> characters = new ArrayList<>();
			for (int j = i; j < s.length(); j++) {
				if (characters.contains(s.charAt(j))) {
					break;
				}
				characters.add(s.charAt(j));
				maxLength = Math.max(characters.size(), maxLength);
			}
		}
		return maxLength;
	}
	
	/**
	 *
	 * 滑动窗口
	 * 通过观察，可以发现：其实不需要对每个字符算出以他开头的不重复最长子串，每一个字符的最长子串可以通过上一个字符的最长字段变化出来。
	 * <p>
	 * 定义一个队列，使用滑动窗口的思想，可以减少很多重复的计算
	 *
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring1(String s) {
		if (s.length() <= 1) {
			return s.length();
		}
		int maxLength = 0;
		int lIndex = 0;
		int rIndex = 1;
		//使用队列保存当前的最长不重复子串
		Deque<Character> characters = new LinkedList<>();
		characters.add(s.charAt(lIndex));
		while (lIndex <= rIndex && rIndex < s.length()) {
			if (characters.contains(s.charAt(rIndex))) {
				lIndex++;
				characters.poll();
				continue;
			}
			characters.offer(s.charAt(rIndex));
			maxLength = Math.max(maxLength, characters.size());
			rIndex++;
		}
		return maxLength;
	}
	
	/**
	 * 利用ASCII码表的原理，所有的字符都可以表示为一个ASCII表中的数字。
	 * <p>
	 * 通过定义一个128个长度的数组，下标为字符所代表的ASCII码值，数组中存储的元素为字符在字符串中最新的位置。
	 *
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring2(String s) {
		if (s.length() <= 1) {
			return s.length();
		}
		int result = 0;
		//使用数组替代哈希表，下标为字符所代表的ASCII码值，数组中存储的元素为字符在字符串中最新的位置。
		Integer[] charIndexArray = new Integer[128];
		
		//快慢指针：快指针指向最新的下标，慢指针指向不重复的最小下标
		int fast = 0;
		int slow = 0;
		while (fast < s.length()) {
			char currValue = s.charAt(fast);
			if (charIndexArray[currValue] != null) {
				slow = Math.max(slow, charIndexArray[currValue] + 1);
			}
			charIndexArray[currValue] = fast;
			result = Math.max(result, fast - slow + 1);
			fast++;
		}
		
		return result;
		
	}
	
	
	public static void main(String[] args) {
		Solution2 solution2 = new Solution2();
		System.out.println(solution2.lengthOfLongestSubstring2("pwwkew"));
	}
}
