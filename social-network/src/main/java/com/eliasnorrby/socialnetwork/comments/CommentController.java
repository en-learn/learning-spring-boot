package com.eliasnorrby.socialnetwork.comments;

import com.eliasnorrby.socialnetwork.images.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class CommentController {

  private final RabbitTemplate rabbitTemplate;

  @PostMapping("/comments")
  public Mono<String> addComment(Mono<Comment> newComment) {
    return newComment.flatMap(
        comment ->
            Mono.fromRunnable(
                    () ->
                        rabbitTemplate.convertAndSend(
                            "learning-spring-boot", "comments.new", comment)))
                .log("commentService-publish")
                .then(Mono.just("redirect:/"));
  }
}
