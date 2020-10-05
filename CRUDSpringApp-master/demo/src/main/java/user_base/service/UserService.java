package user_base.service;

import user_base.Entity.UserDocument;
import user_base.domain.UserDTO;

import java.util.List;

public interface UserService {

    UserDocument saveUser(UserDTO userDTO);

    List<UserDTO> showUsers();

    UserDocument updateUser(UserDTO userDTOUpdate, String id);

    void deleteById(String id);


}
