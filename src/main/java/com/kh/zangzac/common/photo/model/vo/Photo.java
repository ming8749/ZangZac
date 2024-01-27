package com.kh.zangzac.common.photo.model.vo;

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
public class Photo {
   private int photoNo;
   private String photoRename;
   private String photoPath;
   private int photoLevel;
   private int boardType;
   private int boardNo;
   private String photoStatus;

}
