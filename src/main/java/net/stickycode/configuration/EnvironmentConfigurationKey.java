package net.stickycode.configuration;

import java.util.List;

public class EnvironmentConfigurationKey
    implements ConfigurationKey {

  private String environmentPrefix;

  private ConfigurationKey key;

  public EnvironmentConfigurationKey(String environmentPrefix, ConfigurationKey key) {
    this.environmentPrefix = environmentPrefix;
    this.key = key;
  }

  @Override
  public List<String> join(String separator) {
    return environmentPrefix + separator + key.join(separator);
  }

}
