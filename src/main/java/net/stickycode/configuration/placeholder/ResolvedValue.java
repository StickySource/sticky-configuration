package net.stickycode.configuration.placeholder;

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationValues;
import net.stickycode.configuration.LookupValues;

public class ResolvedValue {

  private final ConfigurationKey key;

  private final ConfigurationValues seed;

  private LookupValues value;

  public ResolvedValue(ConfigurationKey key, ConfigurationValues seed) {
    super();
    this.key = key;
    this.seed = seed;
  }

  public boolean isResolved() {
    return value != null;
  }

  public String getValue() {
    return value.getValue();
  }

  @Override
  public String toString() {
    if (!isResolved())
      return seed + " (unresolved)";

    if (seed.equals(value))
      return seed.getValue();

    return seed + "->" + value;
  }

  public ResolvedValue withValue(LookupValues value) {
    this.value = value;
    return this;
  }

}
