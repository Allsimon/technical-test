package technical.test.api.storage.repositories;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import technical.test.api.storage.models.Book;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final ReactiveMongoTemplate mongoTemplate;
    @Delegate
    private final SpringBookRepository delegateRepository;

    // Flux<Book> findBookByAuthorIdAndReleaseDateBetween(String authorId, Integer releaseDateMin, Integer releaseDateMax);

    // Nullable parameters are a very bad idea...
    public Flux<Book> findBookByAuthorIdAndReleaseDateBetween(String authorId, Integer releaseDateMin, Integer releaseDateMax) {

        Query query = new Query();
        ofNullable(authorId).map(id -> where(Book.Fields.authorId).is(id)).ifPresent(query::addCriteria);
        if (releaseDateMin != null && releaseDateMax != null) {
            // Due to limitations of the com.mongodb.BasicDocument, you can't add a second 'releaseDate' criteria
            query.addCriteria(where(Book.Fields.releaseDate).gte(releaseDateMin).lte(releaseDateMax));
        } else {
            ofNullable(releaseDateMin).map(date -> where(Book.Fields.releaseDate).gte(date)).ifPresent(query::addCriteria);
            ofNullable(releaseDateMax).map(date -> where(Book.Fields.releaseDate).lte(date)).ifPresent(query::addCriteria);
        }

        return mongoTemplate.find(query, Book.class);
    }
}
