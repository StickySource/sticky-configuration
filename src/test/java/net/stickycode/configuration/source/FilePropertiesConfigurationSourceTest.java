package net.stickycode.configuration.source;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class FilePropertiesConfigurationSourceTest {

  @Test
  public void pathNotSet() {
    System.clearProperty("configuration.path");
    new FilePropertiesConfigurationSource().loadApplicationConfiguration();
  }
  
  @Test
  public void pathSetToMultipleFilesLoadedInOrderGiven() {
    System.setProperty("configuration.path", "src/test/resources/config.d/01first.properties,src/test/resources/config.d/02second.properties");
    FilePropertiesConfigurationSource source = new FilePropertiesConfigurationSource();
    source.loadApplicationConfiguration();
    assertThat(source.getValue("first.is")).isEqualTo("second");
    assertThat(source.getValue("second.is")).isEqualTo("second");
  }

  @Test(expected = ConfigurationFileNotFoundException.class)
  public void pathNotFound() {
    System.setProperty("configuration.path", "src/test/notresources");
    new FilePropertiesConfigurationSource().loadApplicationConfiguration();
  }

  @Test
  public void directoryWithNoConfigFiles() {
    System.setProperty("configuration.path", "src/test/resources");
    FilePropertiesConfigurationSource source = new FilePropertiesConfigurationSource();
    source.loadApplicationConfiguration();
    assertThat(source.size()).isEqualTo(0);
  }

  @Test
  public void directoryWithConfigFiles() {
    System.setProperty("configuration.path", "src/test/resources/config.d");
    FilePropertiesConfigurationSource source = new FilePropertiesConfigurationSource();
    source.loadApplicationConfiguration();
    assertThat(source.size()).isEqualTo(3);

    assertThat(source.getValue("first.is")).isEqualTo("first");
    assertThat(source.getValue("second.is")).isEqualTo("second");
    assertThat(source.getValue("third.is")).isEqualTo("third");
  }
}
