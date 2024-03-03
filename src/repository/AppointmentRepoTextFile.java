package repository;

import domain.Appointment;
import domain.Patient;

import java.io.*;
import java.util.Iterator;

public class AppointmentRepoTextFile extends FileRepo<Integer, Appointment> {
    public AppointmentRepoTextFile(String filename) {
        super(filename);
        this.readFromFile();
    }

    @Override
    public void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("[,]");
                if (tokens.length == 6) {
                    Integer ID = Integer.parseInt(tokens[0]);
                    Integer IDp = Integer.parseInt(tokens[1].trim());
                    String name = tokens[2].trim();
                    int age = Integer.parseInt(tokens[3].trim());
                    String date = tokens[4].trim();
                    String time = tokens[5].trim();
                    super.elements.put(ID, new Appointment(ID, new Patient(IDp, name, age), date, time));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException var10) {
            throw new RuntimeException(var10);
        }
    }

    @Override
    public void writeToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename));
            Iterable<Appointment> appointments = this.getAllItems();
            Iterator var3 = appointments.iterator();

            while (var3.hasNext()) {
                Appointment a = (Appointment) var3.next();
                Integer var10001 = a.getId();
                writer.write("" + var10001 + ", " + a.getPatient().getId() + ", " + a.getPatient().getName() + ", " + a.getPatient().getAge() + ", " + a.getDate() + ", " + a.getTime() + "\n");
            }
            writer.close();
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }
    }

}
