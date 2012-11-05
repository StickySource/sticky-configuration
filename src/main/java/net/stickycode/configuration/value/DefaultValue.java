package net.stickycode.configuration.value;

import net.stickycode.configuration.ConfigurationValue;

public class DefaultValue
    implements ConfigurationValue {

  private String value;

  public DefaultValue(String value) {
    super();
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }

  @Override
  public boolean hasPrecedence(ConfigurationValue arg0) {
    return false;
  }

  @Override
  public String toString() {
    return "Default{" + value + "}";
  }

}
