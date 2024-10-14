package com.sociablesphere.postsociablesphere.api.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "El id del like no debe estar vacio")
    private Long id;

    @NotBlank(message = "El id el del usuario no debe estar vacio")
    private Long userId;

    @NotBlank(message = "La fecha del post no debe estar vacia")
    private LocalDateTime createdAt;
}
