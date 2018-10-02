package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Collection;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private DataSource dataSource;

    private static final String insertSql =
            "INSERT INTO time_entries (project_id, " +
                    "    user_id, " +
                    "    date, " +
                    "    hours) " +
                    "VALUES (?, ?, ?, ?, ?)";


    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        JdbcTemplate template = new JdbcTemplate(this.dataSource);
        Object[] params = new Object[] {
                timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        };

        int[] types = new int[] {
                Types.BIGINT,
                Types.BIGINT,
                Types.DATE,
                Types.INTEGER
        };

        int row = template.update(insertSql, params, types);

        //return  ;
    }

    @Override
    public TimeEntry find(long l) {
        return null;
    }

    @Override
    public Collection list() {
        return null;
    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {
        return null;
    }

    @Override
    public void delete(long l) {

    }
}
