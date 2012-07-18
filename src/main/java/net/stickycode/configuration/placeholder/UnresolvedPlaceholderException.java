package net.stickycode.configuration.placeholder;

import net.stickycode.configuration.LookupValues;
import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class UnresolvedPlaceholderException
    extends PermanentException {

  public UnresolvedPlaceholderException(LookupValues seed, Placeholder placeholder) {
    super("A value for key {} could not be found when trying to resolve {}", placeholder.getKey(), seed);
  }

}
