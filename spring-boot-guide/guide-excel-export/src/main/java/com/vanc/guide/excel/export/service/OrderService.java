package com.vanc.guide.excel.export.service;

import com.vanc.guide.excel.export.common.OrderStatus;
import com.vanc.guide.excel.export.dao.OrderDao;
import com.vanc.guide.excel.export.service.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: OrderService
 * @date: 2020/7/12 1:56 上午
 * @author: lanysong
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderDao orderDao;

	private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public List<OrderDTO> queryByScrollingPagination(String paymentDateTimeStart,
	                                                 String paymentDateTimeEnd,
	                                                 long lastBatchMaxId,
	                                                 int limit) {
		LocalDateTime start = LocalDateTime.parse(paymentDateTimeStart, F);
		LocalDateTime end = LocalDateTime.parse(paymentDateTimeEnd, F);
		return orderDao.queryByScrollingPagination(lastBatchMaxId, limit, start, end).stream().map(order -> {
			OrderDTO dto = new OrderDTO();
			dto.setId(order.getId());
			dto.setAmount(order.getAmount());
			dto.setOrderId(order.getOrderId());
			dto.setPaymentTime(order.getPaymentTime().format(F));
			dto.setOrderStatus(OrderStatus.fromStatus(order.getOrderStatus()).getDescription());
			return dto;
		}).collect(Collectors.toList());
	}
}
