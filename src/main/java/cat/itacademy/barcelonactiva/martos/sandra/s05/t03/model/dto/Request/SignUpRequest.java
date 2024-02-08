package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotEmpty(message = "Please input a first name")
    private String firstName;
    @NotEmpty(message = "Please input a last name")
    private String lastName;
    @Email(message = "Please input a valid email")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
