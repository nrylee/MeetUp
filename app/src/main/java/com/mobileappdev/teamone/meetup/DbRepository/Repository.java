package com.mobileappdev.teamone.meetup.DbRepository;

import java.sql.*;

public class Repository {
    private class Event {
        private Integer event_id;
        private String event_name;
    }

    public void QueryEvents() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://frankencluster.com:3306/mdevteam1",
                    "mdevteam1user",
                    "sjw.yq97b.H2n"
            );

            Statement stmt = con.createStatement();
        } catch(Exception ex) {

        }
    }
}
