package petmatch.service;

import petmatch.api.response.UserResponse;

public interface UserService {
    UserResponse getUserByEmail(String email);

    UserResponse getUserById(String id);
}
