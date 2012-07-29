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

import static org.fest.assertions.Assertions.assertThat;

import net.stickycode.configuration.CompoundConfigurationKey;
import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationSource;
import net.stickycode.configuration.LookupValues;
import net.stickycode.configuration.PlainConfigurationKey;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.configuration.source.EnvironmentConfigurationSource;

import org.junit.Test;

public class EnvironmentConfigurationSourceTest {

  @Test
  public void unknownValue() {
    assertThat(apply(key("UNKNOWN_VALUE")).hasValue()).isFalse();
  }
  @Test
  public void knownValue() {
    assertThat(apply(key("KNOWN_VALUE")).getValue()).isEqualTo("goodstuff");
  }

  @Test
  public void knownValueWithDifferingCase() {
    assertThat(apply(key("known_value")).getValue()).isEqualTo("goodstuff");
  }

  @Test
  public void compoundKeyWithKnowValue() {
    assertThat(apply(key("KNOWN", "VALUE")).getValue()).isEqualTo("goodstuff");
  }

  @Test
  public void compoundKeyWithKnowValueAndDifferingCase() {
    assertThat(apply(key("Known", "vaLue")).getValue()).isEqualTo("goodstuff");
  }

  private ResolvedConfiguration apply(ConfigurationKey key) {
    LookupValues values = new LookupValues();
    source().apply(key, values);
    return values;
  }

  private ConfigurationKey key(String key) {
    return new PlainConfigurationKey(key);
  }

  private ConfigurationKey key(String... key) {
    return new CompoundConfigurationKey(key);
  }

  private ConfigurationSource source() {
    return new EnvironmentConfigurationSource() {

      @Override
      protected String lookupValue(String environmentKey) {
        if (environmentKey.equals("KNOWN_VALUE"))
          return "goodstuff";

        return null;
      }
    };
  }

}
