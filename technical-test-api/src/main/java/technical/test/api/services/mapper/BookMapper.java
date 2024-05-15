package technical.test.api.services.mapper;

import org.mapstruct.Mapper;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.storage.models.Book;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookMapper {

    Book map(String isbn, String title, Integer releaseDate, String authorId);

    Book map(BookRepresentation bookRepresentation);

    BookRepresentation map(Book book);

}
