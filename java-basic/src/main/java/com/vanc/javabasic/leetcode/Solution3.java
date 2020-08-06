package com.vanc.javabasic.leetcode;

import java.util.Stack;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-08-06 10:25
 */
public class Solution3 {
	/**
	 * 思想：将一棵树t2，合并到另一棵树t1
	 * 递归公式：合并当前节点 + 合并左子树 + 合并右子树
	 * 终止条件：t1或t1的节点为空
	 */
	public TreeNode mergeTreeNode(TreeNode t1, TreeNode t2) {
		//1 合并当前节点
		//t1 没有,而t2有节点,直接返回t2
		if (t1 == null) {
			return t2;
		}
		
		//t2 没有,而t1有节点,直接返回t1
		if (t2 == null) {
			return t1;
		}
		
		//t1和t2都有的节点，节点值相加，更新t1的值
		t1.val = t1.val + t2.val;
		
		
		//2 递归合并左子树和右子树
		t1.left = mergeTreeNode(t1.left, t2.left);
		t1.right = mergeTreeNode(t1.right, t2.right);
		return t1;
	}
	
	
	/**
	 * 思想：将一棵树t2，合并到另一棵树t1
	 * 递归公式：合并当前节点 + 合并左子树 + 合并右子树
	 * 终止条件：t1或t1的节点为空
	 */
	public TreeNode mergeTreeNode1(TreeNode t1, TreeNode t2) {
		//1 合并当前节点
		//t1 没有,而t2有节点,直接返回t2
		if (t1 == null) {
			return t2;
		}
		
		//t2 没有,而t1有节点,直接返回t1
		if (t2 == null) {
			return t1;
		}
		
		//t1和t2都有的节点，节点值比较，更新t1的值
		t1.val = t1.val > t2.val ? t1.val : t2.val;
		
		
		//2 递归合并左子树和右子树
		t1.left = mergeTreeNode(t1.left, t2.left);
		t1.right = mergeTreeNode(t1.right, t2.right);
		return t1;
	}
	
	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null)
			return t2;
		Stack<TreeNode[]> stack = new Stack < > ();
		stack.push(new TreeNode[] {t1, t2});
		while (!stack.isEmpty()) {
			TreeNode[] t = stack.pop();
			if (t[0] == null || t[1] == null) {
				continue;
			}
			t[0].val += t[1].val;
			if (t[0].left == null) {
				t[0].left = t[1].left;
			} else {
				stack.push(new TreeNode[] {t[0].left, t[1].left});
			}
			if (t[0].right == null) {
				t[0].right = t[1].right;
			} else {
				stack.push(new TreeNode[] {t[0].right, t[1].right});
			}
		}
		return t1;
	}
}
