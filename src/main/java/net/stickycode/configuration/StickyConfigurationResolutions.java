package net.stickycode.configuration;

import java.util.HashMap;

import net.stickycode.configuration.placeholder.ResolvedValue;

public class StickyConfigurationResolutions
    implements ConfigurationResolutions {
  
  HashMap<String, ConfigurationValues> resolutions = new HashMap<String, ConfigurationValues>();

  public void put(ConfigurationKey key, ResolvedValue resolve) {
    resolutions.put(key.join("."), resolve);
  }

  @Override
  public ConfigurationValues find(ConfigurationKey key) {
    return resolutions.get(key.join("."));
  }

}
