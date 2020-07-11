package com.vanc.javabasic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : vanc
 * @date: 2020-07-07 09:51
 */
public class DemoTest {
	
	
	/**
	 * 分组求和
	 * {"1":400,"2":200,"3":300}
	 */
	public static void testStream() throws JsonProcessingException {
		List<Map<Long, BigDecimal>> mapList = Lists.newArrayList();
		Map<Long, BigDecimal> longBigDecimalMap = Maps.newHashMap();
		longBigDecimalMap.put(1L,new BigDecimal(100));
		longBigDecimalMap.put(2L,new BigDecimal(200));
		
		
		
		Map<Long, BigDecimal> longBigDecimalMap1 = Maps.newHashMap();
		longBigDecimalMap1.put(3L,new BigDecimal(300));
		longBigDecimalMap1.put(1L,new BigDecimal(300));
		
		mapList.add(longBigDecimalMap);
		mapList.add(longBigDecimalMap1);
		
		Map<Long, BigDecimal> collect = mapList.stream().flatMap(eachMap ->
				eachMap.entrySet().stream()).collect(
						Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
				BigDecimal::add));
		
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(collect));
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		testStream();
	}
	
	
	public static void testMap() {
		List<String> list = Arrays.asList("a1","a2","a3");
		//一对一的处理，在每个字符串后面加上test输出
		list.stream().map(s->s+"test").forEach(System.out::println);
		//一对多的处理，把每个字符串拆成一个个字符，输出，这点map就无法做到。
		list.stream().flatMap(s -> Stream.of(s.split(""))).forEach(System.out::println);
	}
	
	private static List<StudentScore> buildATestList() {
		List<StudentScore> studentScoreList = new ArrayList<>();
		StudentScore studentScore1 = new StudentScore() {{
			setStuName("张三");
			setSubject("语文");
			setScore(70);
		}};
		
		StudentScore studentScore2 = new StudentScore() {{
			setStuName("张三");
			setSubject("数学");
			setScore(80);
		}};
		StudentScore studentScore3 = new StudentScore() {{
			setStuName("张三");
			setSubject("英语");
			setScore(65);
		}};
		StudentScore studentScore4 = new StudentScore() {{
			setStuName("李四");
			setSubject("语文");
			setScore(68);
		}};
		StudentScore studentScore5 = new StudentScore() {{
			setStuName("李四");
			setSubject("数学");
			setScore(70);
		}};
		StudentScore studentScore6 = new StudentScore() {{
			setStuName("李四");
			setSubject("英语");
			setScore(90);
		}};
		StudentScore studentScore7 = new StudentScore() {{
			setStuName("王五");
			setSubject("语文");
			setScore(80);
		}};
		StudentScore studentScore8 = new StudentScore() {{
			setStuName("王五");
			setSubject("数学");
			setScore(85);
		}};
		StudentScore studentScore9 = new StudentScore() {{
			setStuName("王五");
			setSubject("英语");
			setScore(70);
		}};
		
		studentScoreList.add(studentScore1);
		studentScoreList.add(studentScore2);
		studentScoreList.add(studentScore3);
		studentScoreList.add(studentScore4);
		studentScoreList.add(studentScore5);
		studentScoreList.add(studentScore6);
		studentScoreList.add(studentScore7);
		studentScoreList.add(studentScore8);
		studentScoreList.add(studentScore9);
		
		return studentScoreList;
	}
	
	
	/**
	 * 常规写法
	 * 结果{"李四":228,"张三":215,"王五":235}
	 *
	 */
	private static void testMerge() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<StudentScore> studentScoreList = buildATestList();
		
		Map<String, Integer> studentScoreMap = new HashMap<>();
		studentScoreList.forEach(studentScore -> {
			if (studentScoreMap.containsKey(studentScore.getStuName())) {
				studentScoreMap.put(studentScore.getStuName(),
						studentScoreMap.get(studentScore.getStuName()) + studentScore.getScore());
			} else {
				studentScoreMap.put(studentScore.getStuName(), studentScore.getScore());
			}
		});
		
		System.out.println(objectMapper.writeValueAsString(studentScoreMap));
		
	}
	
	
	/**
	 * java8 合并
	 * 返回结果{"李四":228,"张三":215,"王五":235}
	 *
	 */
	private static void testMerge1() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<StudentScore> studentScoreList = buildATestList();
		
		Map<String, Integer> studentScoreMap2 = new HashMap<>();
		studentScoreList.forEach(studentScore -> studentScoreMap2.merge(
				studentScore.getStuName(),
				studentScore.getScore(),
				Integer::sum));
		
		System.out.println(objectMapper.writeValueAsString(studentScoreMap2));
		
	}
	
}
