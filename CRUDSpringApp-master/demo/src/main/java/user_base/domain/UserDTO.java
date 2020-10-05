package user_base.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class UserDTO {

    private String id;

    private String firstName;

    private String lastName;

    @JsonProperty(value = "email_address")
    private String emailAddress;

    private String password;

    @JsonProperty(value = "local_date_time")
    private LocalDateTime localDateTime = LocalDateTime.now();


}
