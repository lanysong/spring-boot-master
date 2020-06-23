package com.vanc.javabasic.design.factory;

import com.vanc.javabasic.common.SortType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 * @author : vanc.song@wetax.com.cn
 * @date: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
 */
@Component
public class SortFactory implements ApplicationContextAware {
    private Map<SortType,Sort> sortTypeSortMap = new ConcurrentHashMap<>();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Sort> beansOfType = applicationContext.getBeansOfType(Sort.class);
        beansOfType.forEach((k,v)->{
            sortTypeSortMap.put(v.getType(),v);
        });
    }

    public int[] sort(SortType sortType,int[] arr){
        Sort sort = sortTypeSortMap.get(sortType);
        return sort.sort(arr);
    }
}
