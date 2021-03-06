package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

public TimeEntry create(TimeEntry timeEntry);

    TimeEntry find(long l);

    List<TimeEntry> list();

    TimeEntry update(long eq, TimeEntry any);

    void delete(long l);
}
