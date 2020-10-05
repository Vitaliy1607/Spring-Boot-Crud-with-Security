package user_base.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @JsonProperty("email_address")
    private String emailAddress;

    @NotNull
    private String password;

}
