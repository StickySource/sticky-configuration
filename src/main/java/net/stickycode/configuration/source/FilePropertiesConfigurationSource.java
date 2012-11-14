package net.stickycode.configuration.source;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import net.stickycode.configuration.ConfigurationValue;
import net.stickycode.configuration.value.SystemValue;
import net.stickycode.stereotype.StickyPlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyPlugin
public class FilePropertiesConfigurationSource
    extends AbstractPropertiesConfigurationSource {

  private Logger log = LoggerFactory.getLogger(getClass());

  @PostConstruct
  public void loadApplicationConfiguration() {
    String configurationFiles = System.getProperty("configuration.path");
    if (configurationFiles == null) {
      log.info("configuration.path not set, not loading configuration from files");
      return;
    }

    for (String path : configurationFiles.split(",")) {
      File file = new File(path);
      if (file.canRead())
        if (file.isFile())
          loadFile(file);
        else
          loadDirectory(file);
      else
        throw new ConfigurationFileNotFoundException(file);
    }
  }

  private void loadFile(File file) {
    try {
      loadUrl(file.toURI().toURL());
    }
    catch (MalformedURLException e) {
      throw new ConfigurationFileNotFoundException(e, file);
    }
  }

  protected void loadDirectory(File file) {
    if (!file.getName().endsWith(".d"))
      log.warn("The linux convention is for config directories to end in '.d', but I have {}", file.getName());

    List<File> files = Arrays.asList(file.listFiles(new PropertiesFileFilter()));
    Collections.sort(files, new Comparator<File>() {

      @Override
      public int compare(File o1, File o2) {
        return o2.getName().compareTo(o1.getName());
      }
    });

    if (!files.isEmpty())
      log.debug("loading {}, most important last", files);

    for (File configurations : files) {
      loadFile(configurations);
    }
  }
  
  /**
   * Template method for situations where a property has been overwritten i.e. occurs more than once in a collection of property
   * files.
   */
  protected void propertyReplacedDuringLoad(URL url, String key, String property, String previous) {
    log.debug("Override '{}={}', was '{}' before loading {}", new Object[] { key, property, previous, url });
  }
  
  @Override
  protected ConfigurationValue createValue(String value) {
    return new SystemValue(value);
  }

}
