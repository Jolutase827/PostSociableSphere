package com.sociablesphere.postsociablesphere.constraints;

import com.sociablesphere.postsociablesphere.model.Post;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class PostValidator implements ConstraintValidator<PostValidationConstraint, Post> {

    @Override
    public boolean isValid(Post post, ConstraintValidatorContext context) {
        if (post == null) {
            return true;
        }


        if (Boolean.TRUE.equals(post.getIsPaid())) {
            if (post.getCost() == null || post.getCost().compareTo(BigDecimal.ZERO) <= 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Cost must be greater than zero when isPaid is true.")
                        .addPropertyNode("cost")
                        .addConstraintViolation();
                return false;
            }
        }

        if (Boolean.TRUE.equals(post.getIsAd()) && post.getCost() != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Cost must be null when isAd is true.")
                    .addPropertyNode("cost")
                    .addConstraintViolation();
            return false;
        }


        if (Boolean.TRUE.equals(post.getIsPaid()) && Boolean.TRUE.equals(post.getIsAd())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("isPaid and isAd cannot both be true.")
                    .addPropertyNode("isPaid")
                    .addConstraintViolation();
            return false;
        }

        if (Boolean.TRUE.equals(post.getIsAd())) {
            if (post.getMaxViews() == null || post.getMaxViews() <= 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("maxViews must be greater than zero when isAd is true.")
                        .addPropertyNode("maxViews")
                        .addConstraintViolation();
                return false;
            }
        }

        String type = post.getType();
        String content = post.getContent();

        if (type != null && content != null) {
            switch (type) {
                case "text":
                    break;

                case "image":
                    if (!content.endsWith(".jpg") && !content.endsWith(".png")) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate("Content must end with .jpg or .png when type is 'image'.")
                                .addPropertyNode("content")
                                .addConstraintViolation();
                        return false;
                    }
                    break;

                case "video":
                    if (!content.endsWith(".mp4")) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate("Content must end with .mp4 when type is 'video'.")
                                .addPropertyNode("content")
                                .addConstraintViolation();
                        return false;
                    }
                    break;

                default:
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Invalid type. Must be 'text', 'image', or 'video'.")
                            .addPropertyNode("type")
                            .addConstraintViolation();
                    return false;
            }
        }

        return true;
    }
}