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
     * 特点：原地排序、稳定排序(指大小相同的元素在排序后位置不变)
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
    private static void insertSort(int[] a, int n) {
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
    private static void chooseSort(int[] a, int n) {
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


    /**
     * 归并排序
     * 时间复杂度 最好：O(nlogn)、最差：O(nlogn)、平均：O(nlogn)
     * 空间复杂度：O(n)
     * 特点：稳定排序、非原地排序
     */
    private static void mergeSort(int[] a, int n) {
        splitSort(a, 0, n - 1);
    }

    private static void splitSort(int[] a, int m, int n) {
        if (m >= n) {
            return;
        }
        int middle = (m + n) / 2;
        splitSort(a, m, middle);
        splitSort(a, middle + 1, n);
        mergeSort(a, m, middle, n);
    }

    /**
     * 合并两个有序数组,创建一个临时数组,遍历两个有序数组小的先放入临时数组,一个数组遍历完之后另一个直接放入临时数组
     * [] [1, 2, 5],[3, 4, 6]
     * [1] [2, 5] [3, 4, 6]
     * [1, 2] [5] [3, 4, 6]
     * [1, 2, 3] [5] [4, 6]
     * [1, 2, 3, 4] [5] [6]
     * [1, 2, 3, 4, 5] [6]
     * [1, 2, 3, 4, 5, 6]
     */
    private static void mergeSort(int[] a, int m, int middle, int n) {
        int i = m;
        int j = middle + 1;
        int k = 0;
        // 临时数组
        int[] temp = new int[n - m + 1];
        while(i <= middle && j <= n) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        //判断有剩余元素的数组
        int s = i;
        int e = middle;
        if (j <= n) {
            s = j;
            e = n;
        }

        while (s <= e) {
            temp[k++] = a[s++];
        }

        for (i = 0; i < n - m; i++) {
            a[m + i] = temp[i];
        }
    }


    /**
     * 快速排序
     * 时间复杂度 最好：O(nlogn)、最差：O(n2)、平均：O(nlogn)
     * 特点：非稳定排序、原地排序
     */
    private static void quickSort(int[] a, int n) {
        quickSortRecursive(a, 0, n - 1);
    }

    private static void quickSortRecursive(int[] a, int m, int n) {
        if (m >= n) {
            return;
        }
        int mid = partition(a, m, n);
        // mid已位于正确的位置
        quickSortRecursive(a, m, mid -1);
        quickSortRecursive(a, mid + 1, n);
    }

    /**
     * 选取一个点,将小于这个点的数放在左边,大于的放在右边
     */
    private static int partition(int[] a, int m, int n) {
        // 选取一个点
        int random = a[n];
        int i = m;
        for (int j = i; j < n; j++) {
            if (a[j] < random) {
                if (i == j) {
                    i++;
                } else {
                    int temp = a[i];
                    a[i++] = a[j];
                    a[j] = temp;
                }
            }
        }

        // 将选取的点换到自己的位置
        int temp = a[i];
        a[i] = a[n];
        a[n] = temp;
        return i;
    }


    public static void main(String[] args) {
        int a[] = {3, 1, 7, 6, 6};
        chooseSort(a, 5);
        System.out.println(Arrays.toString(a));
    }

}
