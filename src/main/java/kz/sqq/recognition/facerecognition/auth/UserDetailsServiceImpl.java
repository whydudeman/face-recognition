package kz.sqq.recognition.facerecognition.auth;

import kz.sqq.recognition.facerecognition.org.model.User;
import kz.sqq.recognition.facerecognition.org.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String identification) throws UsernameNotFoundException {
        System.out.println(identification);
        User user=userRepo.findByUsername(identification)
                .orElseThrow(()->new UsernameNotFoundException("no user with this username "+identification));
        System.out.println(user.getUsername() + " Hello");
        return new UserDetailsImpl(user);
    }
}
