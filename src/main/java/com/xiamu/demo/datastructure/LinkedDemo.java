package com.xiamu.demo.datastructure;

import lombok.Data;


/**
 * @author XiaMu
 * 链表demo
 */
public class LinkedDemo {

    // 单链表反转
    //todo 链表中环的检测
    //todo 两个有序的链表合并
    //todo 删除链表倒数第n个结点
    //todo 求链表的中间结点

    /**
     * 链表反转解法一
     * @param node 当前链表
     * @return 反转后链表
     */
    private static ListNode reverse(ListNode node) {
        if (null == node || null == node.next) {
            return node;
        }
        ListNode pre = null;
        ListNode current = node;
        ListNode next;
        while (current != null) {
            next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        return pre;
    }

    /**
     * 递归链表反转
     * @param node 当前链表
     * @return 反转后链表
     */
    private ListNode recursiveReverse(ListNode node) {
        if (null == node || null == node.next) {
            return node;
        }
        ListNode res= recursiveReverse(node.next);
        node.next.next = node;
        node.next = null;
        return res;
    }

    /**
     * 两个链表逆序相加逆序存储
     * 1->2->3,4->5->6 = 5->7->9
     */
    private static ListNode addTwoNumbers(ListNode one, ListNode two) {
        ListNode sumNode = new ListNode(0);
        ListNode current = sumNode;
        // 进位
        int overflow = 0;
        while (null != one || null != two) {
            int x = null != one ? one.data : 0;
            int y = null != two ? two.data : 0;
            int sum = x + y + overflow;
            // 获取进位
            overflow = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
            if (one != null) { one = one.next; }
            if (two != null) { two = two.next; }
        }
        return sumNode.next;
    }

    @Data
    public static class ListNode{
        private int data;
        private ListNode next;
        ListNode(int x) { data = x; }
    }

}
