package net.stickycode.configuration;

public class EnvironmentConfigurationKey
    implements ConfigurationKey {

  private String environmentPrefix;

  private ConfigurationKey key;

  public EnvironmentConfigurationKey(String environmentPrefix, ConfigurationKey key) {
    this.environmentPrefix = environmentPrefix;
    this.key = key;
  }

  @Override
  public String join(String separator) {
    return environmentPrefix + separator + key.join(separator);
  }

}
