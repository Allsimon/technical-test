package technical.test.api.services.mapper;

import org.mapstruct.*;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.storage.models.Author;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

// TODO: configure all the mapstruct stuff (componentModel,unmappedTargetPolicy, etc) globally
@Mapper(componentModel = SPRING, builder = @Builder(disableBuilder = true))
public interface AuthorMapper {

    Author map(String firstname, String lastname, Integer birthdate);

    Author map(AuthorRepresentation author);

    AuthorRepresentation map(Author author);

    default String generateId(String firstname, String lastname) { // ⚠️ WARNING: this smells like a bug
        return "%s_%s".formatted(firstname, lastname).toLowerCase();
    }

    @AfterMapping
    default Author afterMapping(@MappingTarget Author author) { // ⚠️ WARNING: this smells like a bug
        if (author.getId() == null) {
            return author.withId(generateId(author.getFirstname(), author.getLastname()));
        }
        return author;
    }
}
