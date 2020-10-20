package com.eliasnorrby.socialnetwork;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class ImageTests {
  @Test
  public void imagesManagedByLombokShouldWork() {
    var image = new Image("id", "file-name");
    assertThat(image.getId()).isEqualTo("id");
    assertThat(image.getName()).isEqualTo("file-name");
  }

}
