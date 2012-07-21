package net.stickycode.configuration.placeholder;

import net.stickycode.configuration.LookupValues;

public interface Placeholder {

  boolean notFound();

  String getKey();
  
  int getStart();
  
  int getEnd();

  String replace(String lookup);

  boolean contains(Placeholder placeholder);

}