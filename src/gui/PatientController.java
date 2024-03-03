package gui;

import domain.Patient;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import service.DentistAppointmentService;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class PatientController {
    private DentistAppointmentService service;

    public PatientController(DentistAppointmentService service) {
        this.service = service;
    }

    @FXML
    private ListView<Patient> patientsListView;
    @FXML
    private TextField searchTextField;

    void populateList() {
        Iterable<Patient> patientIterable = service.getAllPatients();
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        patientIterable.forEach(patientList::add);
        patientsListView.setItems(patientList);
    }

    public void initialize() {
        populateList();
    }


    @FXML
    private TextField agePatientTextField;

    @FXML
    private TextField idPatientTextFiled;

    @FXML
    private TextField namePatientTextField;

    @FXML
    private Button addPatientButton;
    @FXML
    private Button removePatientButton;
    @FXML
    private Button updatePatientButton;

    @FXML
    void addPatient(ActionEvent event) {
        Integer id = Integer.parseInt(idPatientTextFiled.getText());
        String name = namePatientTextField.getText();
        Integer age = Integer.parseInt(agePatientTextField.getText());
        service.addPatient(id, name, age);
        populateList();

    }

    @FXML
    void removePatient(ActionEvent event) {
        Integer id = Integer.parseInt(idPatientTextFiled.getText());
        service.deletePatient(id);
        populateList();

    }

    @FXML
    void updatePatient(ActionEvent event) {
        Integer id = Integer.parseInt(idPatientTextFiled.getText());
        String name = namePatientTextField.getText();
        Integer age = Integer.parseInt(agePatientTextField.getText());
        service.updatePatient(id, name, age);
        populateList();


    }

    @FXML
    void searchOnKeyTyped(KeyEvent event) {
        String searchText = searchTextField.getText();
    }

}
