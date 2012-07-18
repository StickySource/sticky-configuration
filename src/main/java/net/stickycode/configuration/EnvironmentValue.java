package net.stickycode.configuration;

public class EnvironmentValue
    implements Value {

  private String value;

  public EnvironmentValue(String value) {
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }

}
