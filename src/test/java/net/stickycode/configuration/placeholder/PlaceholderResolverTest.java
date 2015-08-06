package net.stickycode.configuration.placeholder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import net.stickycode.configuration.LookupValues;
import net.stickycode.configuration.PlainConfigurationKey;
import net.stickycode.configuration.value.ApplicationValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlaceholderResolverTest {

  @Mock
  ConfigurationLookup manifest;

  @Before
  public void before() {
    LookupValues values = new LookupValues();
    values.add(new ApplicationValue("value"));
    when(manifest.lookupValue("key")).thenReturn(values);
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
    resolve("${XXX}");
  }

  @Test
  public void nestedPlaceholders() {
    when(manifest.lookupValue("nested")).thenReturn(new LookupValues().with(new ApplicationValue("key")));
    when(manifest.lookupValue("keykey")).thenReturn(new LookupValues().with(new ApplicationValue("value")));

    assertThat(resolve("${${nested}}")).isEqualTo("value");
    assertThat(resolve("${${nested}${nested}}")).isEqualTo("value");
  }

  @Test(expected = KeyAlreadySeenDuringPlaceholderResolutionException.class)
  public void nestedPlaceholdersWithCycle() {
    when(manifest.lookupValue("loop")).thenReturn(new LookupValues().with(new ApplicationValue("${loop}")));
    resolve("${${loop}}");
  }

  @Test(expected = KeyAlreadySeenDuringPlaceholderResolutionException.class)
  public void nestedPlaceholdersWithDeepCycle() {
    when(manifest.lookupValue("loop")).thenReturn(new LookupValues().with(new ApplicationValue("${loop}")));
    when(manifest.lookupValue("deeploop")).thenReturn(new LookupValues().with(new ApplicationValue("${loop}")));
    resolve("${deeploop}");
  }

  @Test(expected = KeyAlreadySeenDuringPlaceholderResolutionException.class, timeout = 100)
  public void nestedPlaceholdersWithOffsetLoop() {
    when(manifest.lookupValue("offsetloop")).thenReturn(new LookupValues().with(new ApplicationValue(" ${offsetloop}")));
    resolve("${offsetloop}");
  }

  @Test
  public void nestedPlaceholdersWithCycle2() {
    when(manifest.lookupValue("cycle")).thenReturn(new LookupValues().with(new ApplicationValue("cycle")));
    assertThat(resolve("${cycle}")).isEqualTo("cycle");
    assertThat(resolve("${${cycle}}")).isEqualTo("cycle");
  }

  private String resolve(String string) {
    LookupValues lookup = new LookupValues().with(new ApplicationValue(string));
    ResolvedValue resolution = new ResolvedValue(new PlainConfigurationKey("key"), lookup);
    return new PlaceholderResolver(manifest).resolve(lookup, resolution).getValue();
  }

}
