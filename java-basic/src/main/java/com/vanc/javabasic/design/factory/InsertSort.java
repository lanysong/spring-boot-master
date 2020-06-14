package com.vanc.javabasic.design.factory;

import com.vanc.javabasic.common.SortType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InsertSort implements Sort {
    @Override
    public SortType getType() {
        return SortType.INSERT;
    }

    @Override
    public int[] sort(int[] sourceArray) {
        // 7, 2, 4, 6, 3, 9, 1
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        printArr(arr);

        // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        for (int i = 1; i < arr.length; i++) {
            // 记录要插入的数据
            int temp = arr[i], j = i;
            // 从已经排序的序列最右边的开始比较，找到比其小的数
            while (j > 0 && temp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }

            // 存在比其小的数，插入
            if (j != i) {
                arr[j] = temp;
            }

            printArr(arr);
        }

        return arr;
    }

    private void printArr(int[] arr) {
        for (int x = 0; x < arr.length; x++) {
            if (x != arr.length - 1) {
                System.out.print(arr[x] + ", ");
            } else {
                System.out.println(arr[x]);
            }
        }
    }
}
