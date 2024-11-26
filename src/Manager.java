import java.util.ArrayList;

public class Manager {
    private String name;
    private String password;
    private ArrayList<Person> workers;
    public Manager() {
        workers = new ArrayList<>();
    }


    public void addWorker(int id, String name) {
        Person worker = new Person();
        worker.setName(name);
        worker.setId(id);
        workers.add(worker);
    }
    public void removeWorker(int id) {
        workers.removeIf(worker -> worker.getId() == id);
    }
    public void display() {
        if (workers.isEmpty()) {
            System.out.println("No worker found");
        }
        else {
            for (Person person : workers) {
                person.printInfo();
            }
        }
    }
}
