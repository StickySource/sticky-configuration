package net.stickycode.configuration;

public class SystemValue
    implements ConfigurationValue {

  private String value;

  public SystemValue(String value) {
    super();
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }

  @Override
  public boolean hasPrecedence(ConfigurationValue v) {
    return v instanceof EnvironmentValue;
  }

}
