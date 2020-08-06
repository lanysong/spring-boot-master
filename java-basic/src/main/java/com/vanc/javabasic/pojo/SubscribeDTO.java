package com.vanc.javabasic.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-08-06 17:28
 */
@Data
@Builder
public class SubscribeDTO {
	private Integer id;
	private Date startTime;
	private Date endTime;
	private Integer roomId;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SubscribeDTO that = (SubscribeDTO) o;
		return id.equals(that.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
