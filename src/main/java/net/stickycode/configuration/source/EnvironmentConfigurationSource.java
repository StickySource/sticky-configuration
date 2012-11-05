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

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationSource;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.configuration.value.EnvironmentValue;
import net.stickycode.stereotype.StickyPlugin;

@StickyPlugin
public class EnvironmentConfigurationSource
    implements ConfigurationSource {

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  @Override
  public void apply(ConfigurationKey key, ResolvedConfiguration values) {
    String environmentKey = deriveKey(key);
    String value = lookupValue(environmentKey);
    if (value != null)
      values.add(new EnvironmentValue(value));
  }

  protected String lookupValue(String environmentKey) {
    return System.getenv(environmentKey);
  }

  protected String deriveKey(ConfigurationKey key) {
    return key.join("_").toUpperCase();
  }

}
