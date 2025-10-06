package com.quiz_geek.mappers;

import com.quiz_geek.models.User;
import com.quiz_geek.payloads.UserDTO;

public class UserMapper {

    public static User toUser(UserDTO userDTO){
        return new User(
                userDTO.getFullName(),
                "",
                userDTO.getRole()
        );
    }
}
