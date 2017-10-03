package net.stickycode.configuration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    return key.join(separator).stream()
      .map(x -> environmentPrefix + separator + x)
      .collect(Collectors.toList());
  }

}
