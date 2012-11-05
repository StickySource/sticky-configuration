package net.stickycode.configuration.value;

import net.stickycode.configuration.ConfigurationValue;

public class EnvironmentValue
    implements ConfigurationValue {

  private String value;

  public EnvironmentValue(String value) {
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }

  @Override
  public boolean hasPrecedence(ConfigurationValue v) {
    return DefaultValue.class.isAssignableFrom(v.getClass());
  }

  @Override
  public String toString() {
    return "Environment{" + value + "}";
  }

}
