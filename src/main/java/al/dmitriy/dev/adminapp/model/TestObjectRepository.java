package al.dmitriy.dev.adminapp.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface TestObjectRepository extends CrudRepository<TestObject, Integer> {

  //  long deleteByIdAllIgnoreCase(int id);

    @Nullable
    Optional<TestObject> findFirstByIdOrderByIdAsc(int id);

    @Override
    void deleteById(Integer integer);
}
