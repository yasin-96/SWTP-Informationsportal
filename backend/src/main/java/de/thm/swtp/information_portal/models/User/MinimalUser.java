package de.thm.swtp.information_portal.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Data
@Getter
@Setter
public class MinimalUser {

    // UUID from user
    private String userId;

    // Fullname of Person
    private String userName;

    public MinimalUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}