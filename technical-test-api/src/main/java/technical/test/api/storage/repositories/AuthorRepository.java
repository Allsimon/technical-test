package technical.test.api.storage.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import technical.test.api.storage.models.Author;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {
}
