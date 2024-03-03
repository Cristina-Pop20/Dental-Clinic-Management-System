package main;

import domain.Appointment;
import domain.Patient;
import gui.AppointmentController;
import gui.PatientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;
import service.DentistAppointmentService;
import UI.DentistAppointmentUI;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        InterRepo<Patient, Integer> patientRepository = null;
        InterRepo<Appointment, Integer> appointmentRepository = null;

        try {
            FileReader fr = new FileReader("C:\\MAP\\a5-Cristina-Pop20\\src\\settings.properties");

            try {
                Properties props = new Properties();
                props.load(fr);
                String repoType = props.getProperty("repository");
                String sourceName1 = props.getProperty("patient_file");
                String sourceName2 = props.getProperty("appointment_file");
                switch (repoType) {
                    case "textfile":
                        InterRepo<Patient, Integer> Patient_repo = new PatientRepoTextFile(sourceName1);
                        InterRepo<Appointment, Integer> Appointmet_repo = new AppointmentRepoTextFile(sourceName2);
                        DentistAppointmentService service = new DentistAppointmentService(Patient_repo, Appointmet_repo);

                        PatientController patientController = new PatientController(service);
                        FXMLLoader patientsLoader = new FXMLLoader(getClass().getResource("C:\\MAP\\a5-Cristina-Pop20\\src\\gui\\PatinetGUI.fxml"));
                        patientsLoader.setController(patientController);
                        Scene patinetsScene = new Scene(patientsLoader.load());
                        stage.setScene(patinetsScene);
                        stage.show();

                        AppointmentController appointmentController = new AppointmentController(service);
                        FXMLLoader appointmentsLoader = new FXMLLoader(getClass().getResource("C:\\MAP\\a5-Cristina-Pop20\\src\\gui\\AppointmentGUI.fxml"));
                        appointmentsLoader.setController(appointmentController);
                        Scene appointmentsScene = new Scene(appointmentsLoader.load());
                        Stage appointmentsStage = new Stage();
                        appointmentsStage.setScene(appointmentsScene);
                        appointmentsStage.show();

                        break;
                    case "binaryfile":
                        Patient_repo = new PatientRepoBinaryFile(sourceName1);
                        Appointmet_repo = new AppointmentRepoBinaryFile(sourceName2);
                        Patient patient1 = new Patient(4, "Maria", 89);
                        Patient patient2 = new Patient(5, "Luci", 30);
                        Patient patient3 = new Patient(6, "Albert", 57);
                        Patient_repo.addItem(patient1);
                        Patient_repo.addItem(patient2);
                        Patient_repo.addItem(patient3);

                        DentistAppointmentService service2 = new DentistAppointmentService(Patient_repo, Appointmet_repo);
                        Appointment appointment1 = new Appointment(2, patient1, "26/05/2024", "18:30");
                        Appointment appointment2 = new Appointment(3, patient2, "30/02/2024", "19:30");
                        Appointment appointment3 = new Appointment(4, patient3, "11/01/2024", "12:30");
                        Appointmet_repo.addItem(appointment1);
                        Appointmet_repo.addItem(appointment2);
                        Appointmet_repo.addItem(appointment3);
                        DentistAppointmentUI ui2 = new DentistAppointmentUI(service2);

                        ui2.start();
                        break;
                    case "database":
                        Patient_repo = new PatientRepoDB(sourceName1);
                        Appointmet_repo = new AppointmentRepoDB(sourceName2);
                        DentistAppointmentService service3 = new DentistAppointmentService(Patient_repo, Appointmet_repo);
                        DentistAppointmentUI ui3 = new DentistAppointmentUI(service3);
                        ui3.start();
                        break;
                }
            } catch (Throwable var15) {
                try {
                    fr.close();
                } catch (IOException e) {
                    System.err.println("File not found: " + e.getMessage());
                }
            }
            fr.close();
        } catch (IOException var16) {
            throw new RuntimeException(var16);
        }


    }

}
