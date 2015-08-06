package net.stickycode.configuration.value;

import static org.assertj.core.api.Assertions.assertThat;
import net.stickycode.configuration.ConfigurationValue;

import org.junit.Test;

public class PrecedenceTest {

  @Test
  public void defaultsAreLast() {
    assertThat(defaultValue().hasPrecedence(applicationValue())).isFalse();
    assertThat(defaultValue().hasPrecedence(systemValue())).isFalse();
    assertThat(defaultValue().hasPrecedence(environmentValue())).isFalse();
    assertThat(defaultValue().hasPrecedence(defaultValue())).isFalse();
  }

  @Test
  public void applicationValuesAreFirst() {
    assertThat(applicationValue().hasPrecedence(applicationValue())).isFalse();
    assertThat(applicationValue().hasPrecedence(systemValue())).isTrue();
    assertThat(applicationValue().hasPrecedence(environmentValue())).isTrue();
    assertThat(applicationValue().hasPrecedence(defaultValue())).isTrue();
  }

  @Test
  public void systemAfterApplicationValues() {
    assertThat(systemValue().hasPrecedence(applicationValue())).isFalse();
    assertThat(systemValue().hasPrecedence(systemValue())).isFalse();
    assertThat(systemValue().hasPrecedence(environmentValue())).isTrue();
    assertThat(systemValue().hasPrecedence(defaultValue())).isTrue();
  }
  @Test
  public void environmentAfterSystemBeforeDefault() {
    assertThat(environmentValue().hasPrecedence(applicationValue())).isFalse();
    assertThat(environmentValue().hasPrecedence(systemValue())).isFalse();
    assertThat(environmentValue().hasPrecedence(environmentValue())).isFalse();
    assertThat(environmentValue().hasPrecedence(defaultValue())).isTrue();
  }

  private ConfigurationValue environmentValue() {
    return new EnvironmentValue(null);
  }

  private ApplicationValue applicationValue() {
    return new ApplicationValue(null);
  }

  private DefaultValue defaultValue() {
    return new DefaultValue(null);
  }

  private SystemValue systemValue() {
    return new SystemValue(null);
  }

}
