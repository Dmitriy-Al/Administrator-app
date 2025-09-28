package al.dmitriy.dev.adminapp.restcontroller;

import al.dmitriy.dev.adminapp.model.TestObject;
import al.dmitriy.dev.adminapp.model.TestObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//  Создание REST контроллера
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "https://dmitriy-al.github.io") // замените на ваш GitHub Pages URL https://dmitriy-al.github.io/Miniapp/   "https://your-username.github.io"
public class TestObjectController {

    @Autowired
    private TestObjectRepository testObjectRepository;

    @GetMapping("/testdb")
    public Iterable <TestObject> getTestObject() { //List<TestObject> getTestObject() {
        return testObjectRepository.findAll();
    }

    @PostMapping("/testdb")
    public TestObject createTestObject(@RequestBody TestObject testObject) {
        return testObjectRepository.save(testObject);
    }

}
