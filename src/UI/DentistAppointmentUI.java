package UI;

import service.DentistAppointmentService;
import domain.Appointment;
import domain.Patient;

import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.List;

public class DentistAppointmentUI {
    private final DentistAppointmentService appointmentService;
    private final Scanner scanner;
    static Integer pacientID = 20;
    static Integer appointmentID = 20;


    public DentistAppointmentUI(DentistAppointmentService service) {
        this.appointmentService = service;
        this.scanner = new Scanner(System.in);
    }

    void addPatient() {
        System.out.print("Enter patent name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        appointmentService.addPatient(pacientID, name, age);
        pacientID++;
    }

    void addAppointment() {
        System.out.print("Enter patient id: ");
        Integer id = Integer.valueOf(scanner.nextLine());
        System.out.print("Enter date (MM/DD/YYYY): ");
        String date = scanner.nextLine();
        System.out.print("Enter time: ");
        String time = scanner.nextLine();
        appointmentService.addAppointment(appointmentID, id, date, time);
        appointmentID++;
    }

    void deletePatient() {
        System.out.print("Enter patient ID to cancel: ");
        Integer id = Integer.valueOf(scanner.nextLine());
        appointmentService.deletePatient(id);
    }

    void deleteAppointment() {
        System.out.print("Enter appointment ID to cancel: ");
        Integer id = Integer.valueOf(scanner.nextLine());
        appointmentService.deleteAppointment(id);
    }

    void getAllAppointments() {
        System.out.println("List of Appointments:");
        for (Appointment appointment : appointmentService.getAllAppointments())
            System.out.println(appointment);
    }

    void getAllPatients() {
        System.out.println("List of Patients:");
        for (Patient patient : appointmentService.getAllPatients())
            System.out.println(patient);
    }

    void updatePatient() {
        System.out.print("Enter patient ID to update: ");
        Integer id = Integer.valueOf(scanner.nextLine());
        System.out.print("Enter patent name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        appointmentService.updatePatient(id, name, age);
    }

    void updateAppointment() {
        System.out.print("Enter appointment ID to update: ");
        Integer id = Integer.valueOf(scanner.nextLine());
        System.out.print("Enter new patent ID: ");
        Integer idP = Integer.valueOf(scanner.nextLine());
        System.out.print("Enter new date (MM/DD/YYYY): ");
        String date = scanner.nextLine();
        System.out.print("Enter new time: ");
        String time = scanner.nextLine();
        Patient patient = appointmentService.getPatientById(idP);
        appointmentService.updateAppointment(id, patient, date, time);
    }

    private void listOrderedAppointmentsOfSpecificPatient() {
        System.out.print("Enter patent ID: ");
        Integer idP = Integer.valueOf(scanner.nextLine());
        Iterable<Appointment> allAppointmentsIterable = appointmentService.getAllAppointments();
        List<Appointment> allAppointments = StreamSupport.stream(allAppointmentsIterable.spliterator(), false)
                .collect(Collectors.toList());
        List<Appointment> filteredAppointments = allAppointments.stream()
                .filter(appointment -> appointment.getPatient().getId() == idP)
                .collect(Collectors.toList());
        filteredAppointments.sort(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTime));
        for (Appointment appointment : filteredAppointments) {
            System.out.println(appointment);
        }
    }

    private void listPatientsAgeAboveGivenNumber() {
        System.out.print("Enter age: ");
        Integer age = Integer.valueOf(scanner.nextLine());
        Iterable<Patient> allPatientsIterable = appointmentService.getAllPatients();
        List<Patient> allPatients = StreamSupport.stream(allPatientsIterable.spliterator(), false)
                .collect(Collectors.toList());
        List<Patient> patientList = allPatients.stream()
                .filter(patient -> patient.getAge() > age)
                .collect(Collectors.toList());
        for (Patient patient : patientList) {
            System.out.println(patient);
        }
    }

    private void listPatientsOnGivenDate() {
        System.out.print("Enter new date (MM/DD/YYYY): ");
        String date = scanner.nextLine();
        Iterable<Appointment> allAppointmentsIterable = appointmentService.getAllAppointments();
        List<Appointment> allAppointments = StreamSupport.stream(allAppointmentsIterable.spliterator(), false)
                .collect(Collectors.toList());
        List<Appointment> appointmentList = allAppointments.stream()
                .filter(appointment -> appointment.getDate().equals(date))
                .collect(Collectors.toList());
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment.getPatient());
        }
    }

    private void orderPatientsWithMultipleAppointments() {
        Iterable<Appointment> allAppointmentsIterable = appointmentService.getAllAppointments();
        List<Appointment> allAppointments = StreamSupport.stream(allAppointmentsIterable.spliterator(), false)
                .collect(Collectors.toList());

        Map<Integer, List<Appointment>> appointmentsByPatientId = allAppointments.stream()
                .collect(Collectors.groupingBy(appointment -> appointment.getPatient().getId()));

        List<Patient> patientsWithMultipleAppointments = appointmentsByPatientId.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(entry -> entry.getValue().get(0).getPatient()) // Get the patient from the first appointment
                .sorted(Comparator.comparing(Patient::getName)) // Order patients by name
                .collect(Collectors.toList());
        for (Patient patient : patientsWithMultipleAppointments) {
            System.out.println(patient);
        }
    }

    private void orderAppointmentsByPatientNameFromSpecificHour() {
        System.out.print("Enter time: ");
        String time = scanner.nextLine();
        Iterable<Appointment> allAppointmentsIterable = appointmentService.getAllAppointments();
        List<Appointment> allAppointments = StreamSupport.stream(allAppointmentsIterable.spliterator(), false)
                .collect(Collectors.toList());
        List<Appointment> filteredAppointments = allAppointments.stream()
                .filter(appointment -> appointment.getTime().equals(time))
                .sorted(Comparator.comparing(appointment -> appointment.getPatient().getName()))
                .collect(Collectors.toList());
        for (Appointment appointment : filteredAppointments) {
            System.out.println(appointment);
        }

    }

    public void start() {
        while (true) {
            System.out.println("Menu");
            System.out.println("1. Add new Patient");
            System.out.println("2. Add new Appointment");
            System.out.println("3. Delete Patient");
            System.out.println("4. Delete Appointment");
            System.out.println("5. Update Patient");
            System.out.println("6. Update Appointment");
            System.out.println("7. List Patients");
            System.out.println("8. List Appointments");
            System.out.println("9. Create reports");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    addAppointment();
                    break;
                case 3:
                    deletePatient();
                    break;
                case 4:
                    deleteAppointment();
                    break;
                case 5:
                    updatePatient();
                    break;
                case 6:
                    updateAppointment();
                    break;
                case 7:
                    getAllPatients();
                    break;
                case 8:
                    getAllAppointments();
                    break;
                case 9:
                    System.out.println("1.List all appointments ordered by date and time of a specific patient");
                    System.out.println("2.List all patients with ages above a given number");
                    System.out.println("3.List of patients with appointments on a given date");
                    System.out.println("4.Order by name patients with more than 1 appointment");
                    System.out.println("5.Order by the name of patients the appointments from a given hour");
                    System.out.println("Enter your choice:");
                    int choice2 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice2) {
                        case 1:
                            listOrderedAppointmentsOfSpecificPatient();
                            break;
                        case 2:
                            listPatientsAgeAboveGivenNumber();
                            break;
                        case 3:
                            listPatientsOnGivenDate();
                            break;
                        case 4:
                            orderPatientsWithMultipleAppointments();
                            break;
                        case 5:
                            orderAppointmentsByPatientNameFromSpecificHour();
                            break;
                    }
                case 10:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        }
    }


}
