package com.linkedin.datastream.server;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang.Validate;


/**
 * Envelope of a Datastream event to be sent via Kafka.
 */
public class DatastreamProducerRecord {
  private final Optional<Integer> _partition;
  private final String _checkpoint;
  private final List<Pair<byte[], byte[]>> _events;
  private final long _eventsTimestamp;

  DatastreamProducerRecord(List<Pair<byte[], byte[]>> events, Optional<Integer> partition, String checkpoint,
      long eventsTimestamp) {
    Validate.notNull(events, "null event");
    events.forEach((e) -> Validate.notNull(e, "null event"));
    Validate.isTrue(eventsTimestamp > 0, "events timestamp is invalid");

    _events = events;
    _partition = partition;
    _checkpoint = checkpoint;
    _eventsTimestamp = eventsTimestamp;
  }

  /**
   * @return all events in the event record
   */
  public List<Pair<byte[], byte[]>> getEvents() {
    return Collections.unmodifiableList(_events);
  }

  /**
   * @return timestamp in Epoch-millis when the events were created
   */
  public long getEventsTimestamp() {
    return _eventsTimestamp;
  }

  /**
   * @return destination partition within the destination
   */
  public Optional<Integer> getPartition() {
    return _partition;
  }

  @Override
  public String toString() {
    return String.format("%s @ partition=%s", _events, _partition);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatastreamProducerRecord record = (DatastreamProducerRecord) o;
    return Objects.equals(_partition, record._partition) && Objects.equals(_events, record._events) && Objects
        .equals(_checkpoint, record._checkpoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_partition, _events, _checkpoint);
  }

  /**
   * Checkpoint of the event w.r.t source consumption.
   * This is required for checkpoint feature of the
   * event producer.
   *
   * @return string representation of source checkpoint
   */
  public String getCheckpoint() {
    return _checkpoint;
  }

}