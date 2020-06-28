package com.vanc.javabasic.leetcode;

/**
 * 链表反转
 * @author : vanc
 * @date: 2020-06-28 17:38
 */
class Solution1 {
	/**
	 * 递归
	 * @param head
	 * @return
	 */
	public ListNode reverseList(ListNode head){
		if (head == null || null == head.next){
			return head;
		}
		
		ListNode newHead = reverseList(head);
		head.next.next = head;
		head.next = null;
		return newHead;
	}
	
	/**
	 * 迭代
	 * @param head
	 * @return
	 */
	public ListNode reverseList1(ListNode head) {
		ListNode prev = null;
		ListNode curr = head;
		
		while(curr != null) {
			ListNode nxt = curr.next;
			curr.next = prev; // 翻转箭头
			prev = curr; //三人行
			curr = nxt; //三人行
		}
		
		return prev;
	}

}
