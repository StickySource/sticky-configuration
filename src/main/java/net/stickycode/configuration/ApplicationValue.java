package net.stickycode.configuration;

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
    return true;
  }

}
