package com.example.LoginDemoProject.repository;


import com.example.LoginDemoProject.model.Approve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Map;

@Repository
public class ApproveRepo<time> implements  ApproveRepository{


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Approve get(int id) throws SQLException {
        System.err.println("user id " + id);

        String sql = ("select * from approve where id = ?  ;");
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        Approve approve = new Approve();

        approve.setId((int)result.get("id"));
        approve.setUser_id((int) result.get("user_id"));
        approve.setRandomId((String) result.get("randomId"));
        approve.setTimeExpires((Timestamp)result.get("timeExpires"));

        Integer user_id = result.get("id") != null ? ((Long) result.get("id")).intValue() : null;
        approve.setUser_id(user_id);
        return approve;

    }

    @Override
    public int insert(Approve approve) throws SQLException {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("insert into approve ( user_id,randomId,timeExpires) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, approve.getUser_id());
                statement.setString(2, approve.getRandomId());
                statement.setTimestamp(3, approve.getTimeExpires());

                return statement;
            }
        }, holder);

        System.out.println( holder.getKey()+"kkkkkkk");
        return ((Long) holder.getKey().longValue()).intValue();
    }

    @Override
    public Approve delete(int id) throws SQLException {
        return null;
    }

    @Override
    public int update(Approve approve) throws SQLException {
        return 0;
    }
}
