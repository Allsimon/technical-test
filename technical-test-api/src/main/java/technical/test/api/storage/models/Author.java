package technical.test.api.storage.models;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Value
@Builder
@Document
@RequiredArgsConstructor
public class Author {
    @Id
    String id;
    String firstname;
    String lastname;
    Integer birthdate;
}
