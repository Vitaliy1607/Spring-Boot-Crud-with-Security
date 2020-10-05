package user_base.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "Users")
public class UserDocument {


    @Id
    private String id;

    @Field(value = "first_name")
    private String firstName;

    @Field(value = "last_name")
    private String lastName;

    @Field(value = "email_address")
    private String emailAddress;

    @Field
    private String password;

    @Field(value = "create_date")
    @JsonProperty(value = "local_date_time")
    private LocalDateTime localDateTime;

    @DBRef
    private List<RoleDocument> roles;

}
