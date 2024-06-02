package ed.uopp.uoppcore.security.service;

import ed.uopp.uoppcore.security.data.Role;
import ed.uopp.uoppcore.security.data.User;
import ed.uopp.uoppcore.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> {
            log.info("User is not found");
            return new UsernameNotFoundException(username);
        });
    }

    @Override
    public List<User> getAllModerators() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(Role.MODERATOR))
                .toList();
    }

}
