package user_base.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {

    @NotNull
    @JsonProperty("email_address")
    @Size(min = 3 , max = 50)
    private String emailAddress;

    @NotNull
    @Size(max = 50)
    private String password;

}
