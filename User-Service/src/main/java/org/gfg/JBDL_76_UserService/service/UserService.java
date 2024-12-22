package org.gfg.JBDL_76_UserService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.JBDL_76_UserService.dto.UserRequestDTO;
import org.gfg.JBDL_76_UserService.model.Users;
import org.gfg.JBDL_76_UserService.repository.UserRepo;
import org.gfg.Utils.CommonConstants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${user.Authority}")
    private String userAuthority;

    public Users addUpdate(UserRequestDTO userRequestDTO) throws JsonProcessingException {
        Users user = userRequestDTO.toUser();
        user.setAuthorities(userAuthority);
        user.setPassword(encoder.encode(userRequestDTO.getPassword()));
        Users userFromDB = userRepo.findByEmail(userRequestDTO.getEmail());
        JSONObject jsonObject = new JSONObject();
        if(userFromDB == null) {
            jsonObject.put(CommonConstants.NEW_USER, true);
        }
        user = userRepo.save(user);
        jsonObject.put(CommonConstants.USER_CONTACT, user.getPhoneNo());
        jsonObject.put(CommonConstants.USER_EMAIl, user.getEmail());
        jsonObject.put(CommonConstants.USER_IDENTIFIER, user.getIdentifier());
        jsonObject.put(CommonConstants.USER_IDENTIFIER_VALUE, user.getUserIdentifierValue());
        jsonObject.put(CommonConstants.USER_NAME, user.getName());
        jsonObject.put(CommonConstants.USER_ID, user.getId());
        kafkaTemplate.send(CommonConstants.USER_ALTERATION_TOPIC, objectMapper.writeValueAsString(jsonObject));

        return  user;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users users = userRepo.findByEmail(username);
       return users;
    }
}
