package io.pivotal.pal.tracker;

import jdk.net.SocketFlow;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);


        ResponseEntity<TimeEntry> response =  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(timeEntry);

        return response;
    }

    @GetMapping("/time-entries/{l}")
    public ResponseEntity<TimeEntry> read(@PathVariable long l) {
        TimeEntry timeEntry = timeEntryRepository.find(l);
        ResponseEntity<TimeEntry> response;

        if(timeEntry == null) {
            response =  ResponseEntity.status(HttpStatus.NOT_FOUND).body(timeEntry);
        }
        else {
            response = ResponseEntity
                    .ok()
                    .body(timeEntry);
        }
        return response;
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

       List<TimeEntry> entries = new ArrayList<TimeEntry>(timeEntryRepository.list());
       ResponseEntity response = ResponseEntity
                .ok()
                .body(entries);

        return response;
    }

    @PutMapping("/time-entries/{l}")
    public ResponseEntity update(@PathVariable long l, @RequestBody  TimeEntry expected) {

        ResponseEntity response;
        TimeEntry updatedTimeEntry = timeEntryRepository.update(l,expected);
        if (updatedTimeEntry != null) {
            response = ResponseEntity.ok().body(updatedTimeEntry);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedTimeEntry);
        }
        return response;
    }

    @DeleteMapping("/time-entries/{l}")
    public ResponseEntity delete(@PathVariable long l) {
        timeEntryRepository.delete(l);
        ResponseEntity response = new ResponseEntity(HttpStatus.NO_CONTENT);
        return response;
    }
}

