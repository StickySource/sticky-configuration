package net.stickycode.configuration;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import net.stickycode.configuration.placeholder.ConfigurationLookup;
import net.stickycode.configuration.placeholder.PlaceholderResolver;
import net.stickycode.configuration.placeholder.ResolvedValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StickyConfiguration
    implements ConfigurationResolver, ConfigurationLookup {

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

  public ConfigurationResolutions resolve(Iterable<ConfigurationKey> configurations) {
    StickyConfigurationResolutions resolutions = new StickyConfigurationResolutions();
    PlaceholderResolver resolver = new PlaceholderResolver(this);
    for (ConfigurationKey key : configurations) {
        LookupValues seed = lookupValue(key);
        log.debug("resolving key '{}' with seed '{}'", key, seed);
        resolutions.put(key, resolver.resolve(seed, new ResolvedValue(key, seed)));
      }

    log.debug("resolutions {}", resolutions);
    return resolutions;
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
