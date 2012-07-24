package net.stickycode.configuration;

import java.util.LinkedList;
import java.util.ListIterator;

public class LookupValues
    implements ResolvedConfiguration {

  private LinkedList<ConfigurationValue> values = new LinkedList<ConfigurationValue>();

  @Override
  public String getValue() {
    return values.getFirst().get();
  }

  public boolean isEmpty() {
    return values.isEmpty();
  }

  public void add(ConfigurationValue value) {
    ListIterator<ConfigurationValue> i = values.listIterator();
    while (i.hasNext()) {
      ConfigurationValue v = i.next();
      if (value.hasPrecedence(v)) {
        i.set(value);
        i.add(v);
        return;
      }
    }

    i.add(value);
  }

  @Override
  public boolean hasValue() {
    return !values.isEmpty();
  }

  public LookupValues with(ConfigurationValue... applicationValue) {
    for (ConfigurationValue value : applicationValue) {
      add(value);
    }
    return this;
  }

  @Override
  public String toString() {
    return values.toString();
  }

}
