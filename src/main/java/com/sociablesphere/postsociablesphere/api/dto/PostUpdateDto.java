package com.sociablesphere.postsociablesphere.api.dto;

import lombok.*;

import java.math.BigDecimal;
@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUpdateDto {
    private String content;
    private Boolean isPaid;
    private BigDecimal cost;
    private Boolean isAd;
    private Integer maxViews;
    private String footer;
}
