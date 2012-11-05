package net.stickycode.configuration.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationSource;
import net.stickycode.configuration.ConfigurationValue;
import net.stickycode.configuration.ResolvedConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A source of configuration from properties.
 */
public abstract class AbstractPropertiesConfigurationSource
    implements ConfigurationSource
{

  private Logger log = LoggerFactory.getLogger(getClass());

  /**
   * Factory for creating values that a defined in the given properties file
   */
  protected abstract ConfigurationValue createValue(String value);

  /**
   * Paths to the properties files
   */
  protected abstract String getConfigurationPath();

  /**
   * The sanitised configurations.
   */
  private Map<String, String> map;

  protected boolean hasValue(String key) {
    return map.containsKey(key);
  }

  protected String getValue(String key) {
    return map.get(key);
  }

  protected void loadUrl(URL url) {
    Properties p = load(url);

    this.map = new HashMap<String, String>();
    for (String key : p.stringPropertyNames()) {
      String property = p.getProperty(key);
      String previous = map.put(key, property);
      if (previous != null)
        propertyReplacedDuringLoad(url, key, property, previous);
    }
  }

  /**
   * Template method for situations where a property has been overwritten i.e. occurs more than once in a collection of property
   * files.
   */
  protected void propertyReplacedDuringLoad(URL url, String key, String property, String previous) {
    log.warn("Replaced {} value {} with {} when loading {}", new Object[] { key, property, previous, url });
  }

  protected Properties load(URL url) {
    try {
      InputStream i = url.openStream();
      try {
        Properties p = new Properties();
        p.load(i);
        return p;
      }
      finally {
        i.close();
      }
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "@" + getConfigurationPath();
  }

  @Override
  public void apply(ConfigurationKey key, ResolvedConfiguration values) {
    if (map == null)
      return;

    String lookup = key.join(".");
    String value = map.get(lookup);
    if (value != null)
      values.add(createValue(value));

  }

}
