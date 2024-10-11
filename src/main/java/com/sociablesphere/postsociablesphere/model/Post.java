package com.sociablesphere.postsociablesphere.model;

import com.sociablesphere.postsociablesphere.constraints.PostValidationConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
