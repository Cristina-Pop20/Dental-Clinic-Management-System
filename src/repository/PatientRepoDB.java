package repository;

import domain.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientRepoDB extends DBRepo<Integer, Patient> {
    public PatientRepoDB(String tableName) {
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
                    int id = resultSet.getInt("Pid");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    Patient patient = new Patient(id, name, age);
                    super.elements.put(id, patient);
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
    public void addItem(Patient elem){
        try
        {
            openConnection();
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                ps.setInt(1, elem.getId());
                ps.setString(2, elem.getName());
                ps.setInt(3, elem.getAge());
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
