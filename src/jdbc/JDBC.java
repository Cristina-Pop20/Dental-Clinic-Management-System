package jdbc;

import domain.Patient;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    private static final String JDBC_URL =
            "jdbc:sqlite:Clinic";

    private Connection conn = null;

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Patients(" +
                        "Pid INTEGER PRIMARY KEY ," +
                        "name VARCHAR(200), " +
                        "age int);");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Appointments(Aid INTEGER PRIMARY KEY, patient varchar(200), date varchar(200),time varchar(200));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    void initTables() {
        final String[] Patients = new String[]{
                "1,Maria,29",
                "2,Ana,50",
                "3,Mihai,23",
                "4,Liviu,63",
        };
        final String[] Appointments = new String[]{
                "1,1,Maria,29,10|11|2024,18:00",
                "2,2,Ana,50,20|01|2024,12:00",
                "3,3,Mihai,23,23|09|2024,17:00",
                "4,4,Liviu,63,29|02|2024,18:30",

        };

        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Patients(Pid, name, age) VALUES (?, ?,?)")) {
                for (String p : Patients) {
                    String[] tokens = p.split("[,]");
                    statement.setInt(1, Integer.parseInt(tokens[0]));
                    statement.setString(2, tokens[1]);
                    statement.setInt(3, Integer.parseInt(tokens[2]));
                    statement.executeUpdate();
                }
            }

            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Appointments(Aid,patient, date,time) VALUES (?, ?,?,?)")) {
                for (String order : Appointments) {
                    String[] tokens = order.split("[,]");
                    statement.setInt(1, Integer.parseInt(tokens[0]));//id
                    Patient patient = new Patient(Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]));
                    // Serialize the Patient object to a string
                    String stringPatient = patient.toString();

                    statement.setString(2, stringPatient);
                    statement.setString(3, tokens[4]); //date
                    statement.setString(4, tokens[5]); //time
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JDBC db_example = new JDBC();
        db_example.openConnection();
        db_example.createSchema();
        db_example.initTables();
        db_example.closeConnection();
    }

}
