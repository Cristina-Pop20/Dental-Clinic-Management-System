package service;
import domain.Appointment;
import domain.Patient;
import repository.FileRepo;
import repository.InterRepo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DentistAppointmentService {
    private final FileRepo<Integer,Patient> patientRepository;
    private final FileRepo<Integer,Appointment> appointmentRepository;
    public DentistAppointmentService(InterRepo<Patient,Integer> repo, InterRepo<Appointment,Integer> repo2){
        this.patientRepository=(FileRepo)repo;
        this.appointmentRepository=(FileRepo)repo2;
    }

    public void addPatient(Integer id,String name,int age)  {
        Patient patient=new Patient(id,name,age);
        patientRepository.addItem(patient);
    }
    public void addAppointment(Integer appointmentID,Integer pacientID,String date,String time) {
        Patient patient=patientRepository.findItem(pacientID);
        if(patient!=null){
            Appointment appointment=new Appointment(appointmentID,patient,date,time);
            appointmentRepository.addItem(appointment);
        }
    }

    public Patient getPatientById(Integer id){

        return patientRepository.findItem(id);
    }
    public Appointment getAppointmentById(Integer id){

        return appointmentRepository.findItem(id);
    }

    public void updatePatient(Integer id,String newName,int newAge){
        Patient patient=patientRepository.findItem(id);
        if(patient!=null){
            patient.setName(newName);
            patient.setAge(newAge);
        }
    }
    public void updateAppointment(Integer appointmentID,Patient patient,String date,String time){
        Appointment appointment=appointmentRepository.findItem(appointmentID);
        if(appointment!=null){
            appointment.setPatient(patient);
            appointment.setDate(date);
            appointment.setTime(time);
        }
    }

    public Iterable<Appointment> getAllAppointments(){
        return appointmentRepository.getAllItems();
    }
    public Iterable<Patient>getAllPatients(){
        return patientRepository.getAllItems();
    }

    public void deleteAppointment(Integer id) {

        appointmentRepository.removeItem(id);
    }
    public void  deletePatient(Integer id){

        List<Appointment> appointmentsToRemove=new ArrayList<>();
        var l=appointmentRepository.getAllItems();
        var a=l.iterator();
        while (a.hasNext()){
            var ap=a.next();
            if(ap.getPatient().getId()==id)
                appointmentsToRemove.add(ap);
        }
        for(Appointment appointment:appointmentsToRemove){
            appointmentRepository.removeItem(appointment.getId());
        }
        patientRepository.removeItem(id);
    }

//    private List<Appointment> listOrderedAppointmentsOfSpecificPatient(Integer idP) {
//        //System.out.print("Enter patent ID: ");
//      //  Integer idP= Integer.valueOf(scanner.nextLine());
//        Iterable<Appointment> allAppointmentsIterable = appointmentRepository.getAllItems();
//        List<Appointment> allAppointments = StreamSupport.stream(allAppointmentsIterable.spliterator(), false)
//                .collect(Collectors.toList());
//        List<Appointment> filteredAppointments =allAppointments.stream()
//                .filter(appointment->appointment.getPatient().getId()==idP)
//                .collect(Collectors.toList());
//        filteredAppointments.sort(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTime));
//        return filteredAppointments;
//    }
}
