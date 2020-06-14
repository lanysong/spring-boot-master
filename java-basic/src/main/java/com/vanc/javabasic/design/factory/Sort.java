package com.vanc.javabasic.design.factory;

import com.vanc.javabasic.common.SortType;

public interface Sort {
    /**
     * 获取排序类型
     * @return
     */
    SortType getType();

    /**
     * 排序
     * @param sortArray
     * @return
     */
    int[] sort(int[] sortArray);
}
