package net.stickycode.configuration;

import java.util.LinkedList;
import java.util.ListIterator;

public class LookupValues
    implements ConfigurationValues {

  private LinkedList<Value> values = new LinkedList<Value>();

  @Override
  public String getValue() {
    return values.getFirst().get();
  }

  public boolean isEmpty() {
    return false;
  }

  public void add(Value value) {
    ListIterator<Value> i = values.listIterator();
    while (i.hasNext()) {
      Value v = i.next();
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

  public LookupValues with(Value... applicationValue) {
    for (Value value : applicationValue) {
      add(value);
    }
    return this;
  }

}
