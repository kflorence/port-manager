package com.kflorence.portManager.javadsl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** A domain-specific language for port managers. */
public interface PortManager {
  /** Alias for {@link #claimNext()}.
   * @return The next unclaimed port.
   * @throws NoSuchElementException If there are no unclaimed ports.
   */
  default Integer claim() throws NoSuchElementException {
    return claimNext();
  }

  /** Attempts to claim and return the next unclaimed port.
   * @return The next unclaimed port.
   * @throws NoSuchElementException If there are no unclaimed ports.
   */
  default Integer claimNext() throws NoSuchElementException {
    return claim(getUnclaimed().stream().findFirst().map(Stream::of).orElse(Stream.empty()).collect(Collectors.toSet()))
      .stream().findFirst().orElseThrow(() -> new NoSuchElementException("There are no unclaimed ports."));
  }

  /** Claim a port.
   * @param port The port to claim.
   * @return True if the port was successfully claimed, false otherwise.
   */
  default Boolean claim(Integer port) {
    Set<Integer> ports = new HashSet<>(1);
    ports.add(port);
    return !claim(ports).isEmpty();
  }

  /** Given a set of ports to claim, returns those which were successfully claimed.
   * @param ports The set of ports to claim.
   * @return The ports that were successfully claimed.
   */
  Set<Integer> claim(Set<Integer> ports);

  /** All claimed ports. */
  default Set<Integer> getClaimed() {
    Set<Integer> diff = new java.util.HashSet<>(getReserved());
    diff.removeAll(getUnclaimed());
    return diff;
  }

  /** Given a set of ports, returns those which are claimed.
   * @param ports The ports to check.
   * @return The ports that are claimed.
   */
  default Set<Integer> getClaimed(Set<Integer> ports) {
    Set<Integer> intersection = new java.util.HashSet<>(ports);
    intersection.retainAll(getClaimed());
    return intersection;
  }

  /** Check if a port is claimed.
   * @param port The port to check.
   * @return True if the port is claimed, false otherwise.
   */
  default Boolean isClaimed(Integer port) {
    Set<Integer> ports = new HashSet<>(1);
    ports.add(port);
    return !getClaimed(ports).isEmpty();
  }

  /** Check if a port is reserved.
   * @param port The port to check.
   * @return True if the port is reserved, false otherwise.
   */
  default Boolean isReserved(Integer port) {
    Set<Integer> ports = new HashSet<>(1);
    ports.add(port);
    return !getReserved(ports).isEmpty();
  }

  /** Check if a port is unclaimed.
   * @param port The port to check.
   * @return True if the port is unclaimed, false otherwise.
   */
  default Boolean isUnclaimed(Integer port) {
    return !isClaimed(port);
  }

  /** All reserved ports. */
  Set<Integer> getReserved();

  /** Given a set of ports, returns those which are reserved.
   * @param ports The ports to check.
   * @return The ports that are reserved.
   */
  default Set<Integer> getReserved(Set<Integer> ports) {
    Set<Integer> intersection = new java.util.HashSet<>(ports);
    intersection.retainAll(getReserved());
    return intersection;
  }

  /** Unclaim a port.
   * @param port The port to unclaim.
   * @return True if the port was successfully unclaimed, false otherwise.
   */
  default Boolean unclaim(Integer port) {
    Set<Integer> ports = new HashSet<>(1);
    ports.add(port);
    return !unclaim(ports).isEmpty();
  }

  /** Given a set of ports to unclaim, returns those which were successfully unclaimed.
   * @param ports The ports to unclaim.
   * @return The ports that were successfully unclaimed.
   */
  Set<Integer> unclaim(Set<Integer> ports);

  /** All unclaimed ports. */
  Set<Integer> getUnclaimed();

  /** Given a set of ports, returns those which are unclaimed.
   * @param ports The ports to check.
   * @return From the given ports, those which are unclaimed.
   */
  default Set<Integer> getUnclaimed(Set<Integer> ports) {
    Set<Integer> intersection = new HashSet<>(ports);
    intersection.retainAll(getUnclaimed());
    return intersection;
  }
}
