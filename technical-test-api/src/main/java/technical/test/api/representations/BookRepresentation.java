package technical.test.api.representations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.ISBN;

@Builder
public record BookRepresentation(
        @ISBN String isbn,
        @NotBlank String title,
        @NotNull Integer releaseDate,
        @NotBlank String authorId) {
}
