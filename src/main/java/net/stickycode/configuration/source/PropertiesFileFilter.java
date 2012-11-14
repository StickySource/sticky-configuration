package net.stickycode.configuration.source;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Filter out all files that don't end with .properties
 */
public class PropertiesFileFilter
    implements FilenameFilter {

  @Override
  public boolean accept(File dir, String name) {
    return name.endsWith(".properties");
  }

}
