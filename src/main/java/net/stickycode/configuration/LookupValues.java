package net.stickycode.configuration;

public class LookupValues
    implements ConfigurationValues, CharSequence {

  private String value;

  @Override
  public String getValue() {
    return value;
  }

  public boolean isEmpty() {
    return false;
  }

  @Override
  public int length() {
    return value.length();
  }

  @Override
  public char charAt(int index) {
    return value.charAt(index);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return value.subSequence(start, end);
  }

  @Override
  public void add(Value value) {
    // FIXME
    this.value = value.get();
  }

  @Override
  public boolean hasValue() {
    // FIXME
    return value != null;
  }

}
