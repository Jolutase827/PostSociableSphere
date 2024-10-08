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

        return true;

    }
}
