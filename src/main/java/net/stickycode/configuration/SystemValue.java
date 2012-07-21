package net.stickycode.configuration;

public class SystemValue
    implements Value {

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
  public boolean hasPrecedence(Value v) {
    return v instanceof EnvironmentValue;
  }

}
