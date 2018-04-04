package com.mobileappdev.teamone.meetup.DbRepository;

import android.os.AsyncTask;
import android.os.Build;

import com.mobileappdev.teamone.meetup.MapModels.MapEventAttendee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {
    private class Event {
        private Integer event_id;
        private String event_name;
    }

    private class NonResultQuery extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean executed = false;
            Connection con = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(
                        "jdbc:mysql://frankencluster.com:3306/mdevteam1",
                        "mdevteam1user",
                        "sjw.yq97b.H2n"
                );

                stmt = con.createStatement();
                String sql = strings[0];
                executed = stmt.execute(sql);
                stmt.close();
                con.close();
            } catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
            }finally{
                //finally block used to close resources
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException se2){
                }// nothing we can do
                try{
                    if(con!=null)
                        con.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }//end finally try
            }//end try

            return executed;
        }
    }

    private class GetMapAttendeesList extends AsyncTask<String, Void, List<MapEventAttendee>> {

        @Override
        protected List<MapEventAttendee> doInBackground(String... strings) {
            List<MapEventAttendee> attendeeList = new ArrayList<>();
            Connection con = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(
                        "jdbc:mysql://frankencluster.com:3306/mdevteam1",
                        "mdevteam1user",
                        "sjw.yq97b.H2n"
                );

                stmt = con.createStatement();
                String sql = "SELECT `user_id`, `user_name`, `user_centerLatitude`, `user_centerLongitude` FROM `user`";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    MapEventAttendee attendee = new MapEventAttendee(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getDouble(4)
                    );
                    attendeeList.add(attendee);
                }
                rs.close();
                stmt.close();
                con.close();
            } catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
            }finally{
                //finally block used to close resources
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException se2){
                }// nothing we can do
                try{
                    if(con!=null)
                        con.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }//end finally try
            }//end try

            return attendeeList;
        }
    }

    public List<MapEventAttendee> GetEventAttendees() {
        try {
            return new GetMapAttendeesList().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean UpdateUserPosition(int user_id, double lat, double lng) {
        String sql = "UPDATE `user` SET `user`.`user_centerLatitude`=" + lat + ", `user`.`user_centerLongitude`=" + lng + " WHERE `user`.`user_id`=" + user_id;
        try {
            return (new NonResultQuery()).execute(sql).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class InsertQuery extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            Integer id = null;
            Connection con = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(
                        "jdbc:mysql://frankencluster.com:3306/mdevteam1",
                        "mdevteam1user",
                        "sjw.yq97b.H2n"
                );

                stmt = con.createStatement();
                String sql = strings[0];
                id = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.close();
                con.close();
            } catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
            }finally{
                //finally block used to close resources
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException se2){
                }// nothing we can do
                try{
                    if(con!=null)
                        con.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }//end finally try
            }//end try

            return id;
        }
    }

    public Boolean InsertEvent(String name, double lat, double lng, java.util.Date start, java.util.Date end, Integer radius) {
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO `event` " +
                "(`event_name`, `event_dateCreated`, `event_startTime`, `event_endTime`, `event_centerLatitude`, `event_centerLongitude`, `event_radius`) " +
                "VALUES " +
                "('" + name + "','" + sdf.format(new java.util.Date()) + "','" + sdf.format(start) + "','" + sdf.format(end) + "'," + lat + "," + lng + "," + radius + ")";


        try {
            return (new NonResultQuery()).execute(sql).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

        /*
        `event_name` varchar(500),
        `event_dateCreated` datetime,
        `event_startTime` datetime,
        `event_endTime` datetime,
        `event_centerLatitude` double not null default 0.0,
        `event_centerLongitude` double not null default 0.0,
        `event_radius` int not null default 0,
         */
    }
}
