//package repository;
//
//import domain.Identifiable;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import org.sqlite.SQLiteDataSource;
//
//
//public abstract class DBRepo <ID,T extends Identifiable<ID>> extends MemoryRepo<T,ID>{
//    protected final String URL="jdbc:sqlite:Clinic";
//    protected String tableName;
//    protected Connection conn=null;
//
//    public DBRepo(String tableName) {
//        this.tableName = tableName;
//    }
//    public abstract void getData();
//
//    public void openConnection() throws SQLException {
//        SQLiteDataSource dataSource=new SQLiteDataSource();
//        dataSource.setUrl(URL);
//        if(conn==null || conn.isClosed())
//            conn=dataSource.getConnection();
//    }
//    public void closeConnection() throws SQLException {
//        conn.close();
//    }
//    @Override
//    public Iterable<T> getAllItems(){
//        return super.getAllItems();
//    }
//    @Override
//    public void addItem(T item){
//        super.addItem(item);
//    }
//}
