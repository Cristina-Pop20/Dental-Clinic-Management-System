package repository;

import domain.Identifiable;

public abstract class FileRepo <ID,T extends Identifiable<ID>>extends MemoryRepo<T,ID> {
    protected String filename;
    FileRepo(String filename){
        this.filename=filename;
    }
    protected abstract void readFromFile();
    protected abstract void writeToFile();
    public void addItem(T item){
        super.addItem(item);
        this.writeToFile();
    }
    public void removeItem(ID id){
        super.removeItem(id);
        this.writeToFile();
    }
    public void update(T entity,ID id){
        super.update(entity,id);
        this.writeToFile();
    }
    public T findItem(ID id){
        return super.findItem(id);
    }
}
