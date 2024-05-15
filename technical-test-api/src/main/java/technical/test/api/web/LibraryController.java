package technical.test.api.web;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.services.LibraryService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/library")
public class LibraryController {
    private final LibraryService libraryService;

    @PostMapping("/authors")
    public Mono<AuthorRepresentation> registerAuthor(@Valid AuthorRepresentation author) {
        var cleanedAuthor = author.withId(null); // TODO: find out what the original author meant about this ID

        return libraryService.registerAuthorRepresentation(cleanedAuthor);
    }

    @PostMapping("/books")
    // FIXME: test have a different input/output naming :(, we can't re-use the "representation" object...
    public Mono<BookRepresentation> registerAuthor(
            @NotBlank String isbn, // the tests expect a 200 from an invalid ISBN, we can't use the @ISBN validator
            @NotBlank String title,
            @NotNull Integer releaseDateYear,
            @NotBlank String authorRefId) {
        var book = BookRepresentation.builder()
                .isbn(isbn)
                .title(title)
                .releaseDate(releaseDateYear)
                .authorId(authorRefId)
                .build();

        return libraryService.registerBookRepresentation(book);
    }

    @GetMapping("/books")
    public Flux<BookRepresentation> getBooks(@Nullable String authorRefId,
                                             @Nullable Integer yearFrom,
                                             @Nullable Integer yearTo) {
        return libraryService.findBookRepresentationByAuthorAndDateBetween(authorRefId, yearFrom, yearTo);
    }
}
