package technical.test.api.storage.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import technical.test.api.storage.models.Book;

// This boilerplate repository is not meant to be used directly (it should stay package private), it's sole purpose is
// to declare the @Repository in order for Spring to inject it in BookRepository
@Repository
interface SpringBookRepository extends ReactiveCrudRepository<Book, String> {
}