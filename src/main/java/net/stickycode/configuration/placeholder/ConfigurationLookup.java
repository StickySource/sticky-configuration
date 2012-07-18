package net.stickycode.configuration.placeholder;

import net.stickycode.configuration.LookupValues;


public interface ConfigurationLookup {

  public LookupValues lookupValue(String key);

}
