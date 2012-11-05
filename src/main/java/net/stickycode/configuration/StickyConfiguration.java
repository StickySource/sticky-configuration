package net.stickycode.configuration;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import net.stickycode.configuration.placeholder.ConfigurationLookup;
import net.stickycode.configuration.placeholder.PlaceholderResolver;
import net.stickycode.configuration.placeholder.ResolvedValue;
import net.stickycode.stereotype.StickyComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyComponent
public class StickyConfiguration
    implements ConfigurationTargetResolver, ConfigurationLookup {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private Set<ConfigurationSource> sources;

  private String environment = System.getProperty("env");

  @PostConstruct
  public void startup() {
    if (environment != null)
      log.info("Using environment {} when resolving configuration", environment);
    else
      log.debug("No environment is specified for resolving configuration");
  }

  public void resolve(ConfigurationTarget configuration) {
    PlaceholderResolver resolver = new PlaceholderResolver(this);
    LookupValues seed = lookupValue(configuration);
    log.debug("resolving key '{}' with seed '{}'", configuration, seed);
    ResolvedValue resolved = resolver.resolve(seed, new ResolvedValue(configuration, seed));
    configuration.resolvedWith(resolved);
  }

  /**
   * @return the value to use or null if one is not defined in any configuration source
   */
  public LookupValues lookupValue(ConfigurationKey key) {
    return findValueInSources(key);
  }

  LookupValues findValueInSources(ConfigurationKey key) {
    LookupValues values = new LookupValues();

    if (environment != null)
      applySources(new EnvironmentConfigurationKey(environment, key), values);

    applySources(key, values);

    if (values.isEmpty())
      log.debug("value not found for key '{}'", key);

    return values;
  }

  private void applySources(ConfigurationKey key, LookupValues values) {
    for (ConfigurationSource s : sources)
      s.apply(key, values);
  }

  @Override
  public String toString() {
    if (sources == null)
      return getClass().getSimpleName();

    return sources.toString();
  }

  public LookupValues lookupValue(String key) {
    return lookupValue(new PlainConfigurationKey(key));
  }

}
