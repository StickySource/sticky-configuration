package net.stickycode.configuration.placeholder;

import java.util.ArrayDeque;
import java.util.Deque;

import net.stickycode.configuration.LookupValues;


public class PlaceholderResolver {

  private ConfigurationLookup manifest;

  public PlaceholderResolver(ConfigurationLookup manifest) {
    super();
    this.manifest = manifest;
  }

  public ResolvedValue resolve(LookupValues seed, ResolvedValue resolution) {
    if (seed.isEmpty())
      return resolution;

    Placeholder placeholder = find(seed.getValue());
    if (placeholder.notFound())
      return resolution.withValue(seed.getValue());

    LookupValues lookup = manifest.lookupValue(placeholder.getKey());
    if (lookup == null)
      throw new UnresolvedPlaceholderException(seed, placeholder);

    ArrayDeque<Placeholder> seen = new ArrayDeque<Placeholder>();
    seen.push(placeholder);
    return resolve(placeholder.replace(lookup.getValue()), resolution, seen);
  }

  ResolvedValue resolve(String value, ResolvedValue resolution, Deque<Placeholder> seen) {
    if (value == null)
      return resolution;

    Placeholder placeholder = find(value);
    if (placeholder.notFound())
      return resolution.withValue(value);

    for (Placeholder p : seen) {
      if (p.contains(placeholder))
        throw new KeyAlreadySeenDuringPlaceholderResolutionException(placeholder, resolution);
    }

    LookupValues lookup = manifest.lookupValue(placeholder.getKey());

    seen.push(placeholder);
    ResolvedValue resolve = resolve(placeholder.replace(lookup.getValue()), resolution, seen);
    seen.pop();
    return resolve;
  }

  private Placeholder find(String value) {
    int indexOfClose = value.indexOf('}');
    if (indexOfClose == -1)
      return new NoPlaceholder();

    int indexOfStart = value.lastIndexOf("${", indexOfClose);
    if (indexOfStart == -1)
      return new NoPlaceholder();

    return new FoundPlaceholder(value, indexOfStart, indexOfClose);
  }
}
