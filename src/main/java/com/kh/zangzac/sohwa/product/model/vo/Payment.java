package com.kh.zangzac.sohwa.product.model.vo;

import java.sql.Date;

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
public class Payment {
   
   private int orderKeyNo;
   private int orderPrice;
   private Date orderDate;
   private String orderContent;
   private int orderNo;
   private String orderMethod;
   private String orderCard;
   private String orderStatus;
   private String memberId;
   private String orderAddress;
   private int orderProductEno;
   private String deliveryStatus;
   private String productOption;
   private int productNo;
   private String orderPhone;
   private String orderName;
   private Date deliveryDate;
   private String paymentKey;

}