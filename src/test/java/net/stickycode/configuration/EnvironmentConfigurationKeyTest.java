package net.stickycode.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EnvironmentConfigurationKeyTest {

  @Test
  public void simple() {
    assertThat(new EnvironmentConfigurationKey("prefix", new PlainConfigurationKey("value")).join(".")).containsExactly("prefix.value");
  }

}
