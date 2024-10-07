package com.sociablesphere.postsociablesphere.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("posts")
public class Posts {

    @Id
    private Long id;

    private String content;
    private String type;
    private Boolean isPaid = false;
    private BigDecimal cost;
    private Boolean isAd = false;
    private Integer maxViews;
    private Integer viewsRemaining;
    private String footer;
    private Long parentId;
    private Integer likes = 0;
}
