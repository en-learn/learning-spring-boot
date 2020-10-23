package com.eliasnorrby.socialnetwork.images;

import com.eliasnorrby.socialnetwork.images.Image;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ImageRepository extends ReactiveCrudRepository<Image, String> {
  Mono<Image> findByName(String name);
}