package com.kflorence.portManager.javadsl;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/** An implementation of {@link PortManager} for a static set of ports. */
public class StaticPortManager implements PortManager {
  private final AtomicReference<Set<Integer>> available;
  private final Set<Integer> reserved;

  public StaticPortManager(Set<Integer> reserved) {
    this.available = new AtomicReference<>(reserved);
    this.reserved = reserved;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized Set<Integer> claim(Set<Integer> ports) {
    Set<Integer> claimed = getUnclaimed(ports);
    if (!claimed.isEmpty()) {
      Set<Integer> updated = new java.util.HashSet<>(available.get());
      updated.removeAll(claimed);
      available.set(updated);
    }
    return claimed;
  }

  /** {@inheritDoc} */
  @Override
  public Set<Integer> getReserved() {
    return reserved;
  }

  /** {@inheritDoc} */
  @Override
  public Set<Integer> unclaim(Set<Integer> ports) {
    Set<Integer> unclaimed = getClaimed(ports);
    if (!unclaimed.isEmpty()) {
      Set<Integer> updated = new java.util.HashSet<>(available.get());
      updated.addAll(unclaimed);
      available.set(updated);
    }
    return unclaimed;
  }

  /** {@inheritDoc} */
  @Override
  public Set<Integer> getUnclaimed() {
    return available.get();
  }
}
