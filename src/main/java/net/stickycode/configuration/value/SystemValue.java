package net.stickycode.configuration.value;

import net.stickycode.configuration.ConfigurationValue;

public class SystemValue
    implements ConfigurationValue {

  private String value;

  public SystemValue(String value) {
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }

  @Override
  public boolean hasPrecedence(ConfigurationValue v) {
    if (ApplicationValue.class.isAssignableFrom(v.getClass()))
      return false;

    if (SystemValue.class.isAssignableFrom(v.getClass()))
      return false;

    return true;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + value + "}";
  }
}
