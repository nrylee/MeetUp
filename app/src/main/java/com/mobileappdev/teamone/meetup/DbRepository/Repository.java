package com.mobileappdev.teamone.meetup.DbRepository;

import android.os.AsyncTask;
import android.os.Build;

import com.mobileappdev.teamone.meetup.MapModels.MapEventAttendee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {
    public class Event {
        public Integer event_id;
        public String event_name;
        public Date event_created;
        public Date event_start;
        public Date event_end;
        public Double event_latitude;
        public Double event_longitude;
        public Integer event_radius;
        public Boolean event_link_sharing;
        public Boolean event_require_approval;
        public List<EventUser> Attendees;
        public Integer AttendeesCount;
    }

    public class User {
        public Integer user_id;
        public String user_name;
        public Double user_lat;
        public Double user_lng;
    }

    private class EventUser extends User {

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

    private class GetUserList extends AsyncTask<String, Void, List<User>> {

        @Override
        protected List<User> doInBackground(String... strings) {
            List<User> userList = new ArrayList<>();
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
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    User user = new User();
                    user.user_id = rs.getInt(1);
                    user.user_name = rs.getString(2);
                    user.user_lat = rs.getDouble(3);
                    user.user_lng = rs.getDouble(4);
                    userList.add(user);
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

            return userList;
        }
    }

    private class GetEventList extends AsyncTask<String, Void, List<Event>> {

        @Override
        protected List<Event> doInBackground(String... strings) {
            List<Event> eventList = new ArrayList<>();
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
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Event event = new Event();
                    event.event_id = rs.getInt(1);
                    event.event_name = rs.getString(2);
                    event.event_created = rs.getDate(3);
                    event.event_start = rs.getDate(4);
                    event.event_end = rs.getDate(5);
                    event.event_latitude = rs.getDouble(6);
                    event.event_longitude = rs.getDouble(7);
                    event.event_radius = rs.getInt(8);
                    event.event_link_sharing = rs.getBoolean(9);
                    event.event_require_approval = rs.getBoolean(10);
                    event.AttendeesCount = rs.getInt(11);
                    eventList.add(event);
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

            return eventList;
        }
    }

    public List<MapEventAttendee> GetEventAttendees() {
        String sql = "SELECT `user_id`, `user_name`, `user_centerLatitude`, `user_centerLongitude` FROM `user`";
        try {
            List<User> users = (new GetUserList().execute(sql).get());
            List<MapEventAttendee> attendees = new ArrayList<>();
            for(User user:users) {
                MapEventAttendee attendee = new MapEventAttendee(
                    user.user_id,
                    user.user_name,
                    user.user_lat,
                    user.user_lng
                );
                attendees.add(attendee);
            }
            return attendees;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> ListAllUsers() {
        String sql = "SELECT `user_id`, `user_name`, `user_centerLatitude`, `user_centerLongitude` FROM `user`";
        try {
            return (new GetUserList().execute(sql).get());
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

    private class InsertQuery extends AsyncTask<String, Void, List<Integer>> {

        @Override
        protected List<Integer> doInBackground(String... strings) {
            List<Integer> ids = new ArrayList<>();
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
                stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                int autoIncKeyFromApi = -1;

                ResultSet rs = stmt.getGeneratedKeys();

                while(rs.next()) {
                    ids.add(rs.getInt(1));
                }

                rs.close();
                rs = null;
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

            return ids;
        }
    }

    public List<Integer> InsertEvent(String name, double lat, double lng, java.util.Date start, java.util.Date end, Integer radius) {
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO `event` " +
                "(`event_name`, `event_dateCreated`, `event_startTime`, `event_endTime`, `event_centerLatitude`, `event_centerLongitude`, `event_radius`) " +
                "VALUES " +
                "('" + name + "','" + sdf.format(new java.util.Date()) + "','" + sdf.format(start) + "','" + sdf.format(end) + "'," + lat + "," + lng + "," + radius + ")";


        try {
            return (new InsertQuery()).execute(sql).get();
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

    public List<Integer> InsertUserInEvent(Integer event_id, List<Integer> user_id) {
        String sql = "INSERT INTO `xrefEventAttendance_event_user` (`eventAttendance_event_id`,`eventAttendance_user_id`) VALUES ";
        for (Integer userId:user_id) {
            sql += "(" + event_id + "," + userId + "),";
        }
        sql = sql.substring(0, sql.length()-1);

        try {
            return (new InsertQuery()).execute(sql).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Event> GetEventsForUser(Integer user_id) {
        String sql = "SELECT `event_id`, `event_name`, `event_dateCreated`, `event_startTime`, `event_endTime`, `event_centerLatitude`, `event_centerLongitude`, `event_radius`, `event_link_sharing`, `event_require_approval`, count(`eventAttendance_user_id`) FROM `event` inner join `xrefEventAttendance_event_user` on `event_id`=`eventAttendance_event_id` WHERE `event_id` IN (SELECT `event_id` FROM `event` inner join `xrefEventAttendance_event_user` on `event_id`=`eventAttendance_event_id` where `eventAttendance_user_id`=" + user_id +") GROUP BY `event_id`;";

        try {
            return (new GetEventList()).execute(sql).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Event GetEventById(Integer event_id) {
        String sql = "SELECT `event_id`, `event_name`, `event_dateCreated`, `event_startTime`, `event_endTime`, `event_centerLatitude`, `event_centerLongitude`, `event_radius`, `event_link_sharing`, `event_require_approval`, count(`eventAttendance_user_id`) FROM `event` inner join `xrefEventAttendance_event_user` on `event_id`=`eventAttendance_event_id` WHERE `event_id`=" + event_id + " GROUP BY `event_id`;";
        try {
            List<Event> events = (new GetEventList()).execute(sql).get();
            if(events.size()==1)
                return events.get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> InsertEventChat(Integer event_id) {
        String sql = "INSERT INTO `chat` (`chat_event_id`) VALUES (" + event_id + ")";

        try {
            return (new InsertQuery().execute(sql).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> InsertUsersIntoChat(List<Integer> user_ids, Integer chat_id) {
        String sql = "INSERT INTO `xrefChatUser_chat_user` (`chatuser_chat_id`, `chatuser_user_id`) VALUES ";
        for (Integer user_id:user_ids) {
            sql += "(" + chat_id + "," + user_id + "),";
        }
        sql = sql.substring(0,sql.length()-1);

        try {
            return (new InsertQuery().execute(sql).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
