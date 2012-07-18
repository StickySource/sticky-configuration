package net.stickycode.configuration;

public class CompoundConfigurationKey
    implements ConfigurationKey {

  private String[] components;

  public CompoundConfigurationKey(String[] components) {
    this.components = components;
  }

  @Override
  public String join(String separator) {
    StringBuilder b = new StringBuilder(components[0]);

    for (int i = 1; i < components.length; i++) {
      b.append(separator).append(components[i]);
    }

    return b.toString();
  }

}
