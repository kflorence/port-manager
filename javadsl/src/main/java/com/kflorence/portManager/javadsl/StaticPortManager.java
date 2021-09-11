/*
 * Copyright 2021 Kyle Florence
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
  public synchronized Set<Integer> getReserved() {
    return reserved;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized Set<Integer> unclaim(Set<Integer> ports) {
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
  public synchronized Set<Integer> getUnclaimed() {
    return available.get();
  }
}
