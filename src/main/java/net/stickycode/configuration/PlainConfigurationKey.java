package net.stickycode.configuration;


public class PlainConfigurationKey
    implements ConfigurationKey {

  private String key;

  public PlainConfigurationKey(String key) {
    this.key = key;
  }

  @Override
  public String join(String string) {
    return key;
  }

}
