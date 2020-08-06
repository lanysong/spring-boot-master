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
	
	
	public static ListNode reverseList2(ListNode head) {
		if(head == null || head.next == null)
			return head;
		
		ListNode pre = null;
		ListNode cur = head;
		
		ListNode next = head;
		
		while(cur!=null){
			next = next.next;
			cur.next = pre;
			pre = cur;
			cur = next;
			
		}
		return pre;
	}
	
	public static void main(String[] args) {
		ListNode listNode = new ListNode(1);
		ListNode listNode1 = new ListNode(2);
		ListNode listNode2 = new ListNode(3);
		ListNode listNode3 = new ListNode(4);
		
		
		listNode.next = listNode1;
		listNode.next.next = listNode2;
		listNode.next.next.next = listNode3;
		
		ListNode listNode4 = reverseList2(listNode);
		while (listNode4!=null){
			System.out.println(listNode4.value);
			listNode4 = listNode4.next;
		}
		
	}

}
