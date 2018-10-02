package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimeEntryRepository {

public TimeEntry create(TimeEntry timeEntry);

    TimeEntry find(long l);

    ResponseEntity<List<TimeEntry>> list();

    TimeEntry update(long eq, TimeEntry any);

    List<TimeEntry> delete(long l);
}
