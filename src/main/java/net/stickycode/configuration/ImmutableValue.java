package net.stickycode.configuration;

public class ImmutableValue
    implements Value {

  private String value;

  public ImmutableValue(String value) {
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }

}
