package repository;

import domain.Patient;

import java.io.*;
import java.nio.Buffer;
import java.util.Iterator;

public class PatientRepoTextFile extends FileRepo<Integer, Patient> {
    public PatientRepoTextFile(String filename) {
        super(filename);
        this.readFromFile();
    }

    @Override
    public void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            String line=null;
            while ((line=reader.readLine())!=null){
                String[] tokers=line.split("[,]");
                if(tokers.length!=3)
                    continue;
                else {
                    Integer ID=Integer.parseInt(tokers[0]);
                    String name=tokers[1].trim();
                    int age=Integer.parseInt(tokers[2].trim());
                    super.elements.put(ID,new Patient(ID,name,age));
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        catch (IOException var8){
            throw new RuntimeException(var8);
        }
    }

    @Override
    public void writeToFile() {
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter(this.filename));
            Iterable<Patient> patients=this.getAllItems();
            Iterator var3=patients.iterator();

            while (var3.hasNext()){
                Patient p=(Patient)var3.next();
                Integer var10001=p.getId();
                writer.write(""+var10001+", "+p.getName()+", "+p.getAge()+"\n");
            }
            writer.close();
        }
        catch (IOException var5){
            throw new RuntimeException(var5);
        }
    }
}
