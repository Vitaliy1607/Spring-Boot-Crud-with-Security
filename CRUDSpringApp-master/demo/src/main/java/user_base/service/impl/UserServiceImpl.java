package user_base.service.impl;

import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user_base.Entity.RoleDocument;
import user_base.Entity.UserDocument;
import user_base.domain.UserDTO;
import user_base.exceptions.AlreadyExistException;
import user_base.repository.RoleRepository;
import user_base.repository.UserRepository;
import user_base.service.UserService;
import user_base.utils.ObjectMapperUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapperUtils modelMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDocument saveUser(UserDTO userDTO) {
        boolean isEmailExist = userRepository.existsByEmailAddress(userDTO.getEmailAddress());
            if (isEmailExist)  {
                throw new AlreadyExistException(
                    "Account with email: " + userDTO.getEmailAddress() + " already exist"
            );
            }
            RoleDocument roleUser = roleRepository.findByRoleIgnoreCase("USER").get();
            UserDocument userDocument = new UserDocument();
            userDocument.setFirstName(userDTO.getFirstName());
            userDocument.setLastName(userDTO.getLastName());
            userDocument.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userDocument.setEmailAddress(userDTO.getEmailAddress());
            userDocument.setLocalDateTime(userDTO.getLocalDateTime());
            userDocument.setRoles(Arrays.asList(roleUser));
            userRepository.save(userDocument);

            return userDocument;
        }

    @Override
    public List<UserDTO> showUsers() {
        List<UserDocument> userEntities = userRepository.findAll();
        List<UserDTO> userDTOS = modelMapper.mapAll(userEntities, UserDTO.class);
        return userDTOS;
    }

    @Override
    public UserDocument updateUser(UserDTO userDTOUpdate, String id) {
        if (userRepository.findById(id).isPresent()){
            UserDocument userDocument = userRepository.findById(id).get();
            userDocument.setFirstName(userDTOUpdate.getFirstName());
            userDocument.setLastName(userDTOUpdate.getLastName());
            userDocument.setPassword(passwordEncoder.encode(userDTOUpdate.getPassword()));
            userDocument.setEmailAddress(userDTOUpdate.getEmailAddress());
            userRepository.save(userDocument);
            return userDocument;
        }else {
            throw new MongoException("Record not found");
        }
    }

    @Override
    public void deleteById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        mongoTemplate.remove(query, UserDocument.class);


    }
}
