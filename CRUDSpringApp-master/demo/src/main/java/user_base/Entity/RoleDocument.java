package user_base.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor

@Document(collection = "roles")
public class RoleDocument {

    @Id
    private String id;

    @Field
    private String role;

    @DBRef
    private List<UserDocument> userDocumentList;

}