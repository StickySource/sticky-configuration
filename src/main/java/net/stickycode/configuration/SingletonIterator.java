package net.stickycode.configuration;

import java.util.Iterator;

public class SingletonIterator<T>
    implements Iterator<T> {

  private T value;

  public SingletonIterator(T key) {
    this.value = key;
  }

  @Override
  public boolean hasNext() {
    return value != null;
  }

  @Override
  public T next() {
    try {
      return value;
    }
    finally {
      value = null;
    }
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
