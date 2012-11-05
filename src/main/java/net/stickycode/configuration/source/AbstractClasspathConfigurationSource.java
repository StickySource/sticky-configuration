/**
 * Copyright (c) 2010 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.configuration.source;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import javax.annotation.PostConstruct;

import net.stickycode.configuration.ThereCanBeOnlyOneApplicationConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration source for properties files on the classpath
 */
public abstract class AbstractClasspathConfigurationSource
    extends AbstractPropertiesConfigurationSource
{

  private Logger log = LoggerFactory.getLogger(getClass());

  @PostConstruct
  public void loadApplicationConfiguration() {
    Enumeration<URL> urls = findUrls();
    if (urls.hasMoreElements())
      loadOnlyOneUrl(urls);
    else
      log.warn("not loading configuration file {} as it was not found", this);
  }

  protected void loadOnlyOneUrl(Enumeration<URL> urls) {
    URL url = urls.nextElement();
    if (urls.hasMoreElements())
      throw new ThereCanBeOnlyOneApplicationConfigurationException(url, urls.nextElement());

    loadUrl(url);
  }

  @Override
  abstract protected String getConfigurationPath();

  protected Enumeration<URL> findUrls() {
    try {
      return getClassLoader().getResources(getConfigurationPath());
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected ClassLoader getClassLoader() {
    return getClass().getClassLoader();
  }

}
