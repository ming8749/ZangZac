package com.kh.zangzac.jaeyoung.eventBoard.model.vo;

import com.kh.zangzac.common.photo.model.vo.Photo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventBoard {
	
	private int ebNo;
	private String ebLocation;
	private String ebName;
	private String ebIntroduce;
	private String ebPrice;
	private String ebTime;
	private String ebDate;
	private String ebInformation;
	private Photo photo;
	private double ebScore;
	private String ebHost;
}
