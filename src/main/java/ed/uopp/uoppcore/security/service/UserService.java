package ed.uopp.uoppcore.security.service;

import ed.uopp.uoppcore.security.data.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllModerators();

}
