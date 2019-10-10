package com.xiamu.demo.datastructure;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


/**
 * @author XiaMu
 * 链表demo
 */
public class LinkedDemo {

    // 单链表反转
    // 链表中环的检测
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
     * 检测链表中有没有环
     */
    private static boolean checkCycle(ListNode head) {
        Set<ListNode> nodesSeen = new HashSet<>();
        while (head != null) {
            if (nodesSeen.contains(head)) {
                return true;
            } else {
                nodesSeen.add(head);
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 检测链表中有没有环(快慢指针法)
     */
    private static boolean checkCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 检测链表中有没有环，并返回环的入口
     */
    private static ListNode checkAndReturnCycle(ListNode head) {
        Set<ListNode> nodesSeen = new HashSet<>();
        while (head != null) {
            if (nodesSeen.contains(head)) {
                return head;
            } else {
                nodesSeen.add(head);
            }
            head = head.next;
        }
        return null;
    }

    /**
     * 快慢指针法,先找出相交点，然后使用两个指针从头和相交节点开始移动,相交的点即环入口
     */
    private static ListNode checkAndReturnCycle2(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode intersect = getIntersect(head);
        if (null == intersect) {
            return null;
        }

        ListNode a = head;
        ListNode b = intersect;
        while (a != b) {
            a = a.next;
            b = b.next;
        }
        return a;
    }

    private static ListNode getIntersect(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return slow;
            }
        }
        return null;
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




    /**
     * 单链表
     */
    @Getter
    @Setter
    public static class ListNode{
        private int data;
        private ListNode next;
        ListNode(int x) { data = x; }
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(0);
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        node.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node2;
        System.out.println(checkAndReturnCycle2(node).data);
    }

}
