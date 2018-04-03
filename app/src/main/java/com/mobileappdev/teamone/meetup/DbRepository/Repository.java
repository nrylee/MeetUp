package com.mobileappdev.teamone.meetup.DbRepository;

import android.os.AsyncTask;

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
}
