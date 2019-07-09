package com.xiamu.demo.datastructure;

import lombok.Data;

/**
 * @author XiaMu
 * 链表demo
 */
public class LinkedDemo {

    /**
     * 链表反转解法一
     * @param node 当前链表
     * @return 反转后链表
     */
    private static ListNode reserve(ListNode node) {
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
    private ListNode recursiveReserve(ListNode node) {
        if (null == node || null == node.next) {
            return node;
        }
        ListNode res= recursiveReserve(node.next);
        node.next.next = node;
        node.next = null;
        return res;
    }

    @Data
    public static class ListNode{
        private int data;
        private ListNode next;
        ListNode(int x) { data = x; }
    }

}
