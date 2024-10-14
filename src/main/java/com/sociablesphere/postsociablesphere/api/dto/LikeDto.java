package com.sociablesphere.postsociablesphere.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDto {

    @NotBlank(message = "El id del like no debe estar vacio")
    private Long id;

    @NotBlank(message = "El id el del usuario no debe estar vacio")
    private Long userId;

    @NotBlank(message = "El id el del post no debe estar vacio")
    private Long postId;
}
