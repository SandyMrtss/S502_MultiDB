package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.Request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    private String email;

    private String password;
}
