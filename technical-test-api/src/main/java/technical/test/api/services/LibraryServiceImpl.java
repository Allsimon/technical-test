package technical.test.api.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.services.mapper.AuthorMapper;
import technical.test.api.services.mapper.BookMapper;
import technical.test.api.storage.models.Author;
import technical.test.api.storage.models.Book;
import technical.test.api.storage.repositories.AuthorRepository;
import technical.test.api.storage.repositories.BookRepository;

@Service
record LibraryServiceImpl(
        BookMapper bookMapper,
        AuthorMapper authorMapper,
        BookRepository bookRepository,
        AuthorRepository authorRepository) implements LibraryService {
    @Override
    public Mono<Book> registerBook(String isbn, String title, Integer releaseDate, String authorId) {
        var entity = bookMapper.map(isbn, title, releaseDate, authorId);

        return bookRepository.save(entity);
    }

    @Override
    public Mono<BookRepresentation> registerBookRepresentation(BookRepresentation book) {
        var entity = bookMapper.map(book);

        return bookRepository.save(entity)
                .map(bookMapper::map);
    }

    @Override
    public Mono<Author> registerAuthor(String firstname, String lastname, Integer birthdate) {
        var entity = authorMapper.map(firstname, lastname, birthdate);

        return authorRepository.save(entity);
    }

    @Override
    public Mono<AuthorRepresentation> registerAuthorRepresentation(AuthorRepresentation author) {
        var entity = authorMapper.map(author);

        return authorRepository.save(entity).map(authorMapper::map);
    }

    @Override
    public Flux<Book> findBookByAuthorAndDateBetween(String author, Integer releaseDateMin, Integer releaseDateMax) {
        return bookRepository.findBookByAuthorIdAndReleaseDateBetween(author, releaseDateMin, releaseDateMax);
    }

    @Override
    public Flux<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<BookRepresentation> findBookRepresentationByAuthorAndDateBetween(String author, Integer releaseDateMin, Integer releaseDateMax) {
        return findBookByAuthorAndDateBetween(author, releaseDateMin, releaseDateMax)
                .map(bookMapper::map);
    }
}
