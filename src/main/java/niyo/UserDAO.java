package niyo;

import java.util.List;

public interface UserDAO {

    void create(User u);

    User addUser(String id, String username, String password, String firstName, String lastName, String email, String phone);

    List<User> getByFirstName(String firstName);
    List<User> getByLastname(String lastName);
    List<User> getByUsername(String username);
    List<User> getAll();

    boolean updatePassword(String id, String NewPassword);

    boolean updatePhone(String id, String phone);

    boolean removeUser(String id);

}
