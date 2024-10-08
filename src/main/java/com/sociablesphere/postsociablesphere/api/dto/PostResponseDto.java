package com.sociablesphere.postsociablesphere.api.dto;


import lombok.*;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Set;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private Long id;
    private String content;
    private String type;
    private Boolean isPaid;
    private BigDecimal cost;
    private Boolean isAd;
    private Integer maxViews;
    private Integer viewsRemaining;
    private String footer;
    private Long parentId;
    private Set<Long> likes;
    private Set<UserResponseDto> postOwners;
}
