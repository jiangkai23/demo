package com.xiamu.demo.datastructure;

import java.util.Arrays;

/**
 * @author XiaMu
 * @date 2019-10-10
 * 排序算法
 */
public class SortDemo {


    /**
     * 冒泡排序
     * 时间复杂度 最好：O(n)、最差：O(n^2)、平均：O(n^2)
     * 特点：原地排序、稳定排序
     */
    private void bubbleSort(int[] a, int n) {
        for (int i = 0; i < n; i++) {
            boolean exchange = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    exchange = true;
                }
            }
            if (!exchange) {
                break;
            }
        }
    }


    /**
     * 插入排序
     * 时间复杂度 最好：O(n)、最差：O(n^2)、平均：O(n^2)
     * 特点：原地排序、稳定排序
     */
    private static void insertSort(int a[], int n) {
        for (int i = 1; i < n; i++) {
            int value = a[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (value < a[j]) {
                    a[i] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = value;
        }
    }


    /**
     * 选择排序
     * 时间复杂度 最好：O(n^2)、最差：O(n^2)、平均：O(n^2)
     * 特点：原地排序
     */
    private static void chooseSort(int a[], int n) {
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            if (min != i) {
                int temp = a[i];
                a[i] = a[min];
                a[min] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int a[] = {3, 1, 7, 6, 6};
        chooseSort(a, 5);
        System.out.println(Arrays.toString(a));
    }

}
