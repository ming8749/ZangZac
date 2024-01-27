package com.kh.zangzac.sohwa.product.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Cart {
	
	private int cartKeyNo;
	private int productNo;
	private String memberId;
	private int productEno;
	private String buyOption;
	private int buyPrice;

}
