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

import net.stickycode.configuration.LookupValues;
import net.stickycode.configuration.PlainConfigurationKey;
import net.stickycode.configuration.source.SystemPropertiesConfigurationSource;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class SystemPropertiesConfigurationSourceTest {

  @Test
  public void has() {
    assertThat(apply("java.home").hasValue()).isTrue();
    assertThat(apply("user.dir").hasValue()).isTrue();
    assertThat(apply("no.such.property").hasValue()).isFalse();
  }

  private LookupValues apply(String key) {
    LookupValues values = new LookupValues();
    source().apply(new PlainConfigurationKey(key), values);
    return values;
  }

  @Test
  public void get() {
    assertThat(apply("java.home").getValue()).isNotNull();
  }

  private SystemPropertiesConfigurationSource source() {
    return new SystemPropertiesConfigurationSource();
  }

}
