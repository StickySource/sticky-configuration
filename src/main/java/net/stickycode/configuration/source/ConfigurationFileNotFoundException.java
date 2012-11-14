package net.stickycode.configuration.source;

import java.io.File;
import java.net.MalformedURLException;

import net.stickycode.exception.TransientException;

@SuppressWarnings("serial")
public class ConfigurationFileNotFoundException
    extends TransientException {

  public ConfigurationFileNotFoundException(File file) {
    super("File '' could not be found or was not readable", file);
  }

  public ConfigurationFileNotFoundException(MalformedURLException e, File file) {
    super(e, "File '' could not be found as the path was invalidly specified", file);
  }

}
