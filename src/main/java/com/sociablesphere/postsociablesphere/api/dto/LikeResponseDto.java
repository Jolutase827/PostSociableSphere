package com.sociablesphere.postsociablesphere.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LikeResponseDto {

    @EqualsAndHashCode.Include
    private Long userId;
    private LocalDateTime createdAt;
}
