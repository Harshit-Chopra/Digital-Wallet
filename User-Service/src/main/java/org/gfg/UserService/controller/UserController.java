package org.gfg.UserService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gfg.UserService.dto.UserRequestDTO;
import org.gfg.UserService.model.Users;
import org.gfg.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUpdate")
    public ResponseEntity<Users> addUpdate(@RequestBody @Validated UserRequestDTO userRequestDTO) throws JsonProcessingException {
        Users user = userService.addUpdate(userRequestDTO);
        // u should not return users but return the dto, convert in into userresponse dto first then return, remove the id
        if(user != null){
            ResponseEntity response = new ResponseEntity(user, HttpStatus.OK);
            return response;
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
