package user_base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import user_base.Entity.UserDocument;
import user_base.domain.UserDTO;
import user_base.repository.UserRepository;
import user_base.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO user){
        System.out.println(user.getId() + " " +
                user.getFirstName() + " "
                        + user.getLastName());
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.showUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("/update/{userID}")
    public ResponseEntity<?> updateUserbyId(
            @PathVariable("userID") String id,
            @RequestBody UserDTO userDTO
    ){
        boolean isUserExist = userRepository.existsById(id);
    if (isUserExist == false){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
        userDTO.setId(id);
        userService.updateUser(userDTO, id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id){
        boolean isUserExist = userRepository.existsById(id);
        if (isUserExist == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
