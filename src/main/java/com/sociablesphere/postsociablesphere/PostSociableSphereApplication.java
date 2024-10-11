package com.sociablesphere.postsociablesphere;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Generated
@EnableR2dbcRepositories
@SpringBootApplication
public class PostSociableSphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostSociableSphereApplication.class, args);
	}

}
