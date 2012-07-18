package net.stickycode.configuration;

import java.util.Iterator;

public class BeforeIterator<T>
    implements Iterator<T> {

  private Iterator<T> after;

  private T[] before;

  private int index = 0;

  public BeforeIterator(Iterable<T> iterable) {
    this.after = iterable.iterator();
  }

  @Override
  public boolean hasNext() {
    if (index < before.length)
      return true;

    return after.hasNext();
  }

  @Override
  public T next() {
    if (index < before.length)
      return before[index++];

    return after.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove does not make sense for a before iterator that is really a view");
  }

  public BeforeIterator<T> withBefore(T... before) {
    this.before = before;
    return this;
  }

}
