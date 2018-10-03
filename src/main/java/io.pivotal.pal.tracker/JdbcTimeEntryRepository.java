package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

 //   private DataSource dataSource;
    private JdbcTemplate template;

    private static final String insertSql =
            "INSERT INTO time_entries (project_id, " +
                    "    user_id, " +
                    "    date, " +
                    "    hours) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;


    public JdbcTimeEntryRepository(DataSource dataSource) {
//        this.dataSource = dataSource;
        this.template = new JdbcTemplate(dataSource);

    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();


        template.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        timeEntry.setId(generatedKeyHolder.getKey().longValue());
        return timeEntry;


/*
        Object[] params = new Object[] {
                timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        };

        int[] types = new int[] {
                Types.BIGINT,
                Types.BIGINT,
                Types.DATE,
                Types.INTEGER
        };

        int row = template.
                .update(insertSql, params, types);
*/
        //return  ;
    }

    @Override
    public TimeEntry find(long id) {
        return template.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                new Object[]{id},
                extractor);
    }



    @Override
    public List<TimeEntry> list() {
        return template.query("SELECT id, project_id, user_id, date, hours FROM time_entries", mapper);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        template.update("UPDATE time_entries " +
                        "SET project_id = ?, user_id = ?, date = ?,  hours = ? " +
                        "WHERE id = ?",
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                Date.valueOf(timeEntry.getDate()),
                timeEntry.getHours(),
                id);

        return find(id);
    }

    @Override
    public void delete(long id) {
        template.update("DELETE FROM time_entries WHERE id = ?", id);
    }
}
