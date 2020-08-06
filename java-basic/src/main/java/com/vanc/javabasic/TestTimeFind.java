package com.vanc.javabasic;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vanc.javabasic.pojo.SubscribeDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-08-06 17:00
 */
public class TestTimeFind {
	private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	private final static Set<SubscribeDTO> result = Sets.newHashSet();
	public static void main(String[] args) throws ParseException {
		ArrayList<SubscribeDTO> objects = Lists.newArrayList();
		
		SubscribeDTO dto1 = SubscribeDTO.builder()
				.id(1)
				.roomId(1)
				.startTime(simpleDateFormat.parse("2019-10-01 10:11:23"))
				.endTime(simpleDateFormat.parse("2020-10-01 10:11:23"))
				.build();
		
		SubscribeDTO dto2 = SubscribeDTO.builder()
				.id(2)
				.roomId(2)
				.startTime(simpleDateFormat.parse("2019-09-01 10:11:23"))
				.endTime(simpleDateFormat.parse("2019-10-02 10:11:23"))
				.build();
		
		
		
		SubscribeDTO dto3 = SubscribeDTO.builder()
				.id(3)
				.roomId(2)
				.startTime(simpleDateFormat.parse("2019-01-01 10:11:23"))
				.endTime(simpleDateFormat.parse("2019-02-01 10:11:23"))
				.build();
		
		
		SubscribeDTO dto4 = SubscribeDTO.builder()
				.id(4)
				.roomId(2)
				.startTime(simpleDateFormat.parse("2020-11-01 10:11:23"))
				.endTime(simpleDateFormat.parse("2020-12-01 10:11:23"))
				.build();
		
		SubscribeDTO dto5 = SubscribeDTO.builder()
				.id(5)
				.roomId(2)
				.startTime(simpleDateFormat.parse("2021-09-01 10:11:23"))
				.endTime(simpleDateFormat.parse("2021-10-01 10:11:23"))
				.build();
		
		objects.add(dto1);
		objects.add(dto2);
		objects.add(dto3);
		objects.add(dto4);
		objects.add(dto5);
		
		objects.stream().forEach(item -> {
		
		
		
		filter(objects,item);
		
		
		
		
		});
		
		System.out.println(result.size());
	}
	
	private static void filter(ArrayList<SubscribeDTO> list , SubscribeDTO dto){
		List<SubscribeDTO> subscribeDTOList = list.stream().filter(v -> !v.getId().equals(dto.getId())).collect(Collectors.toList());
		AtomicInteger count = new AtomicInteger();
		subscribeDTOList.stream().forEach(item -> {
			if (item.getEndTime().after(item.getStartTime())){
				
				boolean flag = dto.getRoomId().equals(item.getRoomId()) && (
						dto.getEndTime().before(item.getStartTime()) || dto.getStartTime().after(item.getEndTime()));
				
				
				if (!flag){
					return;
				}
				count.getAndIncrement();
				
			}
		});
		
		if (count.get() ==4 ){
			result.add(dto);
		}
		
	}
}
