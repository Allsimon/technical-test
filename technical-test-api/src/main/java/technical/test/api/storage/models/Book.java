package technical.test.api.storage.models;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document
@FieldNameConstants
public class Book {
    @Id
    String id;
    String isbn;
    String title;
    Integer releaseDate;
    String authorId;
}
