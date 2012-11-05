package net.stickycode.configuration.value;

import net.stickycode.configuration.ConfigurationValue;

public class ApplicationValue
    implements ConfigurationValue {

  private String value;

  public ApplicationValue(String value) {
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }

  @Override
  public boolean hasPrecedence(ConfigurationValue v) {
    // precedence of application values should be stable so it does not have precedence over itself
    return !ApplicationValue.class.isAssignableFrom(v.getClass());
  }

  @Override
  public String toString() {
    return "Application{" + value + "}";
  }

}
