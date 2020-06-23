package com.vanc.javabasic.design.factory;

import com.vanc.javabasic.common.SortType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BubbleSort implements Sort {
    @Override
    public SortType getType() {
        return SortType.BUBBLE;
    }

    @Override
    public int[] sort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        return arr;
    }
}

