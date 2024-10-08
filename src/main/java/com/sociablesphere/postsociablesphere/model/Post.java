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
@Table("posts")
@PostValidationConstraint
public class Post {

    @Id
    private Long id;

    private String content;
    private String type;

    @NotNull
    private Boolean isPaid; //is true cost no puede estar nulo o negativo o cero y is ad no puede ser true
    private BigDecimal cost;

    @NotNull
    private Boolean isAd; //is true maxviews no puede ser negativo no nulo o cero
    private Integer maxViews;
    private Integer viewsRemaining;
    private String footer;
    private Long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
