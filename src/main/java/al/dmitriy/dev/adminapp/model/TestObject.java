package al.dmitriy.dev.adminapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@lombok.Setter
@lombok.Getter
@Entity(name = "testdb")
public class TestObject {

    @Id
    private int id;
    private String name;


    @Override
    public String toString() {
        return " < name: " + name + ", id = " + id + "> ";
    }
}
