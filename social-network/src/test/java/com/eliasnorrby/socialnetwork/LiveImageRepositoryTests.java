package com.eliasnorrby.socialnetwork;

import static org.assertj.core.api.Assertions.assertThat;

import com.eliasnorrby.socialnetwork.images.Image;
import com.eliasnorrby.socialnetwork.images.ImageRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class LiveImageRepositoryTests {

  @Autowired
  ImageRepository repository;

  @Autowired MongoOperations operations;

  @BeforeEach
  public void setUp() {
    operations.dropCollection(Image.class);

    operations.insert(new Image("1", "learning-spring-boot-cover.jpg"));
    operations.insert(new Image("2", "learning-spring-boot-2nd-edition-cover.jpg"));
    operations.insert(new Image("3", "bazinga.png"));

    operations.findAll(Image.class).forEach(System.out::println);
  }

  @Test
  public void findAllShouldWork() {
    Flux<Image> images = repository.findAll();
    StepVerifier.create(images)
      .recordWith(ArrayList::new)
      .expectNextCount(3)
      .consumeRecordedWith(
        results -> {
          assertThat(results).hasSize(3);
          assertThat(results)
            .extracting(Image::getName)
            .contains(
              "learning-spring-boot-cover.jpg",
              "learning-spring-boot-2nd-edition-cover.jpg",
              "bazinga.png");
        })
      .expectComplete()
      .verify();
  }

  @Test
  public void findByNameShouldWork() {
    Mono<Image> image = repository.findByName("bazinga.png");
    StepVerifier.create(image)
      .expectNextMatches(
        results -> {
          assertThat(results.getName()).isEqualTo("bazinga.png");
          assertThat(results.getId()).isEqualTo("3");
          return true;
        })
      .expectComplete()
      .verify();
  }
}
