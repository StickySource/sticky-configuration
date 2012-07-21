package net.stickycode.configuration.placeholder;

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationValues;
import net.stickycode.configuration.Value;

public class ResolvedValue implements ConfigurationValues {

  private final ConfigurationKey key;

  private final ConfigurationValues seed;

  private String value;

  public ResolvedValue(ConfigurationKey key, ConfigurationValues seed) {
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
  public void add(Value value) {
  }

  @Override
  public boolean hasValue() {
    return isResolved();
  }

}
