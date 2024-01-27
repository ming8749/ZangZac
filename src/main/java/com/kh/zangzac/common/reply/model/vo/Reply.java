package com.kh.zangzac.common.reply.model.vo;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reply {
   private int replyNo;
   private String replyContent;
   private LocalDateTime replyCreateDate;
   private String formatDate;
   private LocalDateTime replyModifyDate;
   private int boardType;
   private int boardNo;
   private String replyStatus;
   private String memberId;
   private String memberName;
   private String memberNickname;
   private String memberProfilePath;
}