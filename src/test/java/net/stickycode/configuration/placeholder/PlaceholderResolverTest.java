package net.stickycode.configuration.placeholder;

import static org.assertj.core.api.StrictAssertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import net.stickycode.configuration.LookupValues;
import net.stickycode.configuration.PlainConfigurationKey;
import net.stickycode.configuration.value.ApplicationValue;

public class PlaceholderResolverTest {

  @Mocked
  ConfigurationLookup manifest;

  @Before
  public void expectKeys() {
    LookupValues values = new LookupValues();
    values.add(new ApplicationValue("value"));
    new NonStrictExpectations() {
      {
        manifest.lookupValue("key");
        result = values;
        maxTimes = -1;
      }
    };
  }

  @Test
  public void identity() {
    assertThat(resolve("abcde")).isEqualTo("abcde");
    assertThat(resolve("abc}de")).isEqualTo("abc}de");
    assertThat(resolve("ab$c}de")).isEqualTo("ab$c}de");
    assertThat(resolve("ab${cde")).isEqualTo("ab${cde");
    assertThat(resolve("ab}${cde")).isEqualTo("ab}${cde");
  }

  @Test
  public void onePlaceholder() {
    assertThat(resolve("${key}")).isEqualTo("value");
    assertThat(resolve("${key} ")).isEqualTo("value ");
    assertThat(resolve(" ${key}")).isEqualTo(" value");
    assertThat(resolve(" ${key} ")).isEqualTo(" value ");
    assertThat(resolve("key ${key} key value")).isEqualTo("key value key value");
  }

  @Test
  public void twoPlaceholders() {
    assertThat(resolve("${key}${key}")).isEqualTo("valuevalue");
    assertThat(resolve("${key} ${key}")).isEqualTo("value value");
    assertThat(resolve(" ${key}${key}")).isEqualTo(" valuevalue");
    assertThat(resolve(" ${key} ${key} ")).isEqualTo(" value value ");
    assertThat(resolve("key ${key} key ${key}value")).isEqualTo("key value key valuevalue");
  }

  @Test
  public void manyPlaceholders() {
    assertThat(resolve("${key}${key}${key}")).isEqualTo("valuevaluevalue");
    assertThat(resolve("${key} ${key} ${key}")).isEqualTo("value value value");
    assertThat(resolve(" ${key}${key}${key}")).isEqualTo(" valuevaluevalue");
    assertThat(resolve(" ${key} ${key} ${key} ")).isEqualTo(" value value value ");
  }

  @Test(expected = UnresolvedPlaceholderException.class)
  public void noValueForPlaceholder() {
    new Expectations() {
      {
        manifest.lookupValue("XXX");
        result = new LookupValues();
      }
    };
    resolve("${XXX}");
  }

  @Test
  public void nestedPlaceholders() {
    new Expectations() {
      {
        manifest.lookupValue("nested");
        result = new LookupValues().with(new ApplicationValue("key"));

        manifest.lookupValue("keykey");
        result = new LookupValues().with(new ApplicationValue("value"));
      }
    };

    assertThat(resolve("${${nested}}")).isEqualTo("value");
    assertThat(resolve("${${nested}${nested}}")).isEqualTo("value");
  }

  @Test(expected = KeyAlreadySeenDuringPlaceholderResolutionException.class)
  public void nestedPlaceholdersWithCycle() {
    new Expectations() {
      {
        manifest.lookupValue("loop");
        result = new LookupValues().with(new ApplicationValue("${loop}"));
      }
    };
    resolve("${${loop}}");
  }

  @Test(expected = KeyAlreadySeenDuringPlaceholderResolutionException.class)
  public void nestedPlaceholdersWithDeepCycle() {
    new Expectations() {
      {
        manifest.lookupValue("loop");
        result = new LookupValues().with(new ApplicationValue("${loop}"));
        manifest.lookupValue("deeploop");
        result = new LookupValues().with(new ApplicationValue("${loop}"));
      }
    };
    resolve("${deeploop}");
  }

  @Test(expected = KeyAlreadySeenDuringPlaceholderResolutionException.class, timeout = 100)
  public void nestedPlaceholdersWithOffsetLoop() {
    new Expectations() {
      {
        manifest.lookupValue("offsetloop");
        result = new LookupValues().with(new ApplicationValue(" ${offsetloop}"));
      }
    };
    resolve("${offsetloop}");
  }

  @Test
  public void nestedPlaceholdersWithCycle2() {
    new Expectations() {
      {
        manifest.lookupValue("cycle");
        result = new LookupValues().with(new ApplicationValue("cycle"));
        maxTimes = -1;
      }
    };
    assertThat(resolve("${cycle}")).isEqualTo("cycle");
    assertThat(resolve("${${cycle}}")).isEqualTo("cycle");
  }

  private String resolve(String string) {
    LookupValues lookup = new LookupValues().with(new ApplicationValue(string));
    ResolvedValue resolution = new ResolvedValue(new PlainConfigurationKey("key"), lookup);
    return new PlaceholderResolver(manifest).resolve(lookup, resolution).getValue();
  }

}
