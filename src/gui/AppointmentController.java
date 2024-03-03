package gui;

import domain.Appointment;
import domain.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import service.DentistAppointmentService;

public class AppointmentController {
    public DentistAppointmentService service;

    public AppointmentController(DentistAppointmentService service) {
        this.service = service;
    }

    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<Appointment> appointmentsListView;

    void populateList() {
        Iterable<Appointment> appointmentIterable = service.getAllAppointments();

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        appointmentIterable.forEach(appointmentList::add);
        appointmentsListView.setItems(appointmentList);
    }

    public void initialize() {
        populateList();
    }


    @FXML
    private TextField idAppointmentTextField;
    @FXML
    private TextField idPatientTextField;
    @FXML
    private TextField dateAppointmentTextField;
    @FXML
    private TextField timeAppointmentTextField;

    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button removeAppointmentButton;
    @FXML
    private Button updateAppointmentButton;


    @FXML
    void addAppointment(ActionEvent event) {
        Integer id = Integer.parseInt(idAppointmentTextField.getText());
        Integer idP = Integer.parseInt(idPatientTextField.getText());
        String date = dateAppointmentTextField.getText();
        String time = timeAppointmentTextField.getText();
        service.addAppointment(id, idP, date, time);
        populateList();

    }

    @FXML
    void removeAppointment(ActionEvent event) {
        Integer id = Integer.parseInt(idAppointmentTextField.getText());
        service.deleteAppointment(id);
        populateList();
    }

    @FXML
    void updateAppointment(ActionEvent event) {
        Integer id = Integer.parseInt(idAppointmentTextField.getText());
        Integer idP = Integer.parseInt(idPatientTextField.getText());
        String date = dateAppointmentTextField.getText();
        String time = timeAppointmentTextField.getText();
        Patient patient = service.getPatientById(idP);
        service.updateAppointment(id, patient, date, time);
        populateList();


    }


}
