package net.stickycode.configuration;

import java.util.Collections;
import java.util.List;

/**
 * A key that has no components
 */
public class PlainConfigurationKey
    implements ConfigurationKey {

  private String key;

  public PlainConfigurationKey(String key) {
    this.key = key;
  }

  @Override
  public List<String> join(String string) {
    return Collections.singletonList(key);
  }

}
