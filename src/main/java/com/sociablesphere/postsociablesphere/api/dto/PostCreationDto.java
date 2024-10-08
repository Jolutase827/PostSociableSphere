package com.sociablesphere.postsociablesphere.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreationDto {


    private String content;
    private String type;
    private Boolean isPaid;
    private BigDecimal cost;
    private Boolean isAd;
    private Integer maxViews;
    private String footer;
    private Long parentId;
    private Long userId;

}
