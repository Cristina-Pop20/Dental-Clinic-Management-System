package repository;

import domain.Appointment;
import domain.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentRepoDB extends DBRepo<Integer, Appointment> {
    public MemoryRepo<Patient,Integer> PatientRepo=new MemoryRepo<>();
    public AppointmentRepoDB(String tableName) {
        super(tableName);
        getData();
    }

    @Override
    public void getData() {
        try {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("Aid");

                    String patient = resultSet.getString("patient");
                    String[] tokens = patient.split("[,]");
                    String name=tokens[0].trim();//patient_name
                    // Extract only the numeric part of the id and age
                    String idString = tokens[1].trim().replaceAll("\\D", ""); // Remove non-numeric characters
                    String ageString = tokens[2].trim().replaceAll("\\D", ""); // Remove non-numeric characters
                    Integer IDp = Integer.parseInt(idString); // patient_id
                    Integer age = Integer.parseInt(ageString); // patient_age

                    String date = resultSet.getString("date");
                    String time = resultSet.getString("time");

                    elements.put(id,new Appointment(id,new Patient(IDp,name,age),date,time));

                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void addItem(Appointment elem) {
        try
        {
            openConnection();
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?,?);";
            try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                ps.setInt(1, elem.getId());
                ps.setString(2, String.valueOf(elem.getPatient()));
                ps.setString(3, elem.getDate());
                ps.setString(4, elem.getTime());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
