package net.stickycode.configuration.placeholder;

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.configuration.ConfigurationValue;

public class ResolvedValue implements ResolvedConfiguration {

  private final ConfigurationKey key;

  private final ResolvedConfiguration seed;

  private String value;

  public ResolvedValue(ConfigurationKey key, ResolvedConfiguration seed) {
    super();
    this.key = key;
    this.seed = seed;
  }

  public boolean isResolved() {
    return value != null;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    if (!isResolved())
      return seed + " (unresolved)";

    if (seed.equals(value))
      return seed.getValue();

    return seed + "->" + value;
  }

  public ResolvedValue withValue(String value) {
    this.value = value;
    return this;
  }

  @Override
  public void add(ConfigurationValue value) {
  }

  @Override
  public boolean hasValue() {
    return isResolved();
  }

}
