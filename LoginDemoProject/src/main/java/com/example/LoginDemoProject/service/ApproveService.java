package com.example.LoginDemoProject.service;


import com.example.LoginDemoProject.model.Approve;
import com.example.LoginDemoProject.repository.ApproveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class ApproveService  {


   @Autowired
    ApproveRepo approveRepo;

    @Autowired
    JdbcTemplate jdbcTemplate;


    public int insert(Approve approve) throws SQLException {

        int approveID = this.approveRepo.insert(approve);
        {
            System.err.println("add " + approveID);
        }
        return approveID;
    }


    public Approve get(String id) throws SQLException {

        System.err.println("approve_id" + id);

        String sql = ("select * from approve where id = ?  ;");
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        Approve approve = new Approve();

        approve.setId((int) result.get("id"));
        approve.setUser_id((int) result.get("user_id"));
        approve.setRandomId((String) result.get("randomId"));
        approve.setTimeExpires((Timestamp) result.get("timeExpires"));


        approve.setUser_id((int)result.get("id"));
        return approve;
    }

    public int update(Approve  approve) throws SQLException {
        int approveID = this.approveRepo.update(approve);
        {
            System.err.println("add " + approveID);
        }
        return approveID;
    }

    public Approve delete(int id) throws SQLException {
        return this.approveRepo.delete(id);

    }


}
