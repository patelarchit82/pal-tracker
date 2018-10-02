package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map timeEntries = new HashMap<>();
    
public TimeEntry create(TimeEntry timeEntry) {
    return timeEntry;
}

    @Override
    public TimeEntry find(long l) {

        return null;
    }

    @Override
    public ResponseEntity<List<TimeEntry>> list() {
        return null;
    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {
        return null;
    }

    @Override
    public List<TimeEntry> delete(long l) {
        return null;
    }
}
