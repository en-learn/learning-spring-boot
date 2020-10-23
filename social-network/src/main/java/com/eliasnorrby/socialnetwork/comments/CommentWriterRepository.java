package com.eliasnorrby.socialnetwork.comments;

import org.springframework.data.repository.Repository;
import reactor.core.publisher.Mono;

public interface CommentWriterRepository extends Repository<Comment, String> {
  Mono<Comment> save(Comment comment);

  // Needed to support save()
  Mono<Comment> findById(String id);
}
