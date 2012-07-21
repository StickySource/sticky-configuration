package net.stickycode.configuration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ConfigurationManfestTest {

  public void before() throws SecurityException, NoSuchFieldException {
//    source = mock(ConfigurationSource.class);
//    manifest = new ConfigurationManifest();
//    Field f = ConfigurationManifest.class.getDeclaredField("sources");
//    Fields.set(manifest, f, Collections.singleton(source));
//
//    Field fs = ConfigurationManifest.class.getDeclaredField("systemProperties");
//    Fields.set(manifest, fs, mock(SystemPropertiesConfigurationSource.class));
//    
//    Field fp = ConfigurationManifest.class.getDeclaredField("applicationConfiguration");
//    Fields.set(manifest, fp, mock(StickyApplicationConfigurationSource.class));
  }
  
  @Test
  public void noEnv() throws SecurityException, NoSuchFieldException {
//    System.clearProperty("env");
//    before();
//    manifest.lookupValue("a");
//    verify(source).hasValue("a");
  }
  
  @Test
  public void env() throws SecurityException, NoSuchFieldException {
//    System.setProperty("env", "qa");
//    before();
//    manifest.lookupValue("a");
//    verify(source).hasValue("qa.a");
//    verify(source).hasValue("a");
    System.clearProperty("env");
  }
}
