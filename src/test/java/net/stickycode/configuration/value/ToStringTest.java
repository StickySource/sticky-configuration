package net.stickycode.configuration.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ToStringTest {

  @Test
  public void defaultToString() {
    assertThat(new DefaultValue("blah").toString()).contains("blah");
  }

  @Test
  public void systemToString() {
    assertThat(new SystemValue("blah").toString()).contains("blah");
  }

  @Test
  public void applicationToString() {
    assertThat(new ApplicationValue("blah").toString()).contains("blah");
  }

  @Test
  public void environmentValueToString() {
    assertThat(new EnvironmentValue("blah").toString()).contains("blah");
  }

}
