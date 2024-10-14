package com.sociablesphere.postsociablesphere.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUser {

    @Id
    private Long postId;
    private Long userId;

}
