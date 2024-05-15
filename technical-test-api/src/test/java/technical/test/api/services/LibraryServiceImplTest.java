package technical.test.api.services;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;
import technical.test.api.TestInfraConfig;
import technical.test.api.TestSupport;
import technical.test.api.storage.repositories.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class LibraryServiceImplTest extends TestInfraConfig {
    @Resource
    private LibraryServiceImpl libraryServiceImpl;
    @Resource
    private  BookRepository bookRepository;
    @Resource
    TestSupport testSupport;

    @AfterEach
    void clear() {
        bookRepository.deleteAll().block();
    }

    @Test
    void registerAuthor() {
        StepVerifier.create(libraryServiceImpl.registerAuthor("Isaac","Asimov", 1920))
                .expectSubscription()
                .expectNextMatches(author -> author.getBirthdate() == 1920
                        && author.getFirstname().equals("Isaac")
                        && author.getLastname().equals("Asimov")
                        && author.getId().equals("isaac_asimov"))
                .verifyComplete();
    }

    @Test
    void registerBook() {
        StepVerifier.create(libraryServiceImpl.registerBook("1234-5678-90","Fondation", 1951, "isaac_asimov"))
                .expectSubscription()
                .expectNextMatches(book -> StringUtils.equals(book.getIsbn(), "1234-5678-90")
                        && StringUtils.equals(book.getTitle(), "Fondation")
                        && book.getReleaseDate() == 1951
                        && StringUtils.equals(book.getAuthorId(), "isaac_asimov"))
                .verifyComplete();
    }

    @Test
    void findAllBooks() {
        StepVerifier.create(testSupport.loadBooks()
                        .then(testSupport.loadAuthor())
                        .thenMany(libraryServiceImpl.findAllBooks()))
                .expectSubscription()
                .expectNextCount(14)
                .verifyComplete();
    }

    @Test
    void findBookByAuthor() {
        StepVerifier.create(testSupport.loadBooks()
                        .then(testSupport.loadAuthor())
                        .then(libraryServiceImpl.findBookByAuthorAndDateBetween("douglas_adams", null, null).collectList()))
                .expectSubscription()
                .assertNext(books -> {
                    Assertions.assertThat(books).hasSize(1);
                    Assertions.assertThat(books)
                            .extracting("title")
                            .containsExactly("Le Guide du voyageur galactique");
                        }
                )
                .verifyComplete();
    }

    @Test
    void findBookByAuthorAndDateBefore() {
        StepVerifier.create(testSupport.loadBooks()
                        .then(testSupport.loadAuthor())
                        .then(libraryServiceImpl.findBookByAuthorAndDateBetween("isaac_asimov", null, 1959).collectList()))
                .expectSubscription()
                .assertNext(books -> {
                            Assertions.assertThat(books).hasSize(6);
                            Assertions.assertThat(books)
                                    .extracting("title")
                                    .containsExactlyInAnyOrder("Fondation", "Fondation et Empire", "Seconde Fondation",
                                            "Les Robots", "Les Cavernes d'acier", "Face aux feux du soleil");
                        }
                )
                .verifyComplete();
    }

    @Test
    void findBookByAuthorAndDateOver() {
        StepVerifier.create(testSupport.loadBooks()
                        .then(testSupport.loadAuthor())
                        .then(libraryServiceImpl.findBookByAuthorAndDateBetween("isaac_asimov", 1960, null).collectList()))
                .expectSubscription()
                .assertNext(books -> {
                            Assertions.assertThat(books).hasSize(7);
                            Assertions.assertThat(books)
                                    .extracting("title")
                                    .containsExactlyInAnyOrder("Fondation foudroyée",
                                            "Terre et Fondation",
                                            "Prélude à Fondation",
                                            "L'Aube de Fondation",
                                            "Un défilé de robots",
                                            "Les Robots de l'aube",
                                            "Les Robots et l'Empire");
                        }
                )
                .verifyComplete();
    }

    @Test
    void findBookByDateBetween() {
        StepVerifier.create(testSupport.loadBooks()
                        .then(testSupport.loadAuthor())
                        .then(libraryServiceImpl.findBookByAuthorAndDateBetween(null, 1950, 1955).collectList()))
                .expectSubscription()
                .assertNext(books -> {
                            Assertions.assertThat(books).hasSize(5);
                            Assertions.assertThat(books)
                                    .extracting("title")
                                    .containsExactlyInAnyOrder("Fondation", "Fondation et Empire", "Seconde Fondation",
                                            "Les Robots", "Les Cavernes d'acier");
                        }
                )
                .verifyComplete();
    }
}
