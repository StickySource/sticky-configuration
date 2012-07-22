package net.stickycode.configuration;

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
    return false;
  }

}
