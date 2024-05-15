package technical.test.api.services;

import jakarta.annotation.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.storage.models.Author;
import technical.test.api.storage.models.Book;

// TODO: remove methods with multiple arguments, they'll probably cause bugs in the future (mistake in argument ordering)
// TODO: remove methods exposing the 'storage.models' classes
public interface LibraryService {
    Mono<Book> registerBook(String isbn, String title, Integer releaseDate, String authorId);

    Mono<BookRepresentation> registerBookRepresentation(BookRepresentation book);

    Mono<Author> registerAuthor(String firstname, String lastname, Integer birthdate);

    Mono<AuthorRepresentation> registerAuthorRepresentation(AuthorRepresentation author);

    Flux<Book> findBookByAuthorAndDateBetween(@Nullable String author, @Nullable Integer releaseDateMin, @Nullable Integer releaseDateMinMax);

    Flux<Book> findAllBooks();

    Flux<BookRepresentation> findBookRepresentationByAuthorAndDateBetween(@Nullable String author, @Nullable Integer releaseDateMin, @Nullable Integer releaseDateMinMax);
}
