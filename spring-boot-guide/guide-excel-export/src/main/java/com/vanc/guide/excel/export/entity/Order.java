package com.vanc.guide.excel.export.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @description: Order
 * @date: 2020/7/12 1:46 上午
 * @author: lanysong
 * @version: 1.0
 */
@Data
public class Order {

	private Long id;
	private String creator;
	private String editor;

	private OffsetDateTime createTime;
	private OffsetDateTime editTime;
	private Long version;
	private Integer deleted;

	private String orderId;
	private BigDecimal amount;
	private OffsetDateTime paymentTime;

	private Integer orderStatus;
}
