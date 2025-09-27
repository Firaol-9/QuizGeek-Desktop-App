package com.quiz_geek.backend.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @NonNull private String fullName;
    @NonNull private String email;
    @NonNull private String password;
    @NonNull private UserRole role;
}
