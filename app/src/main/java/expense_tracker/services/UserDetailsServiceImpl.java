package expense_tracker.services;

import expense_tracker.entities.UserInfo;
import expense_tracker.models.UserInfoData;
import expense_tracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;


@Component
@AllArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {



    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("could not find user");
        }
        return new CustomUserDetails(user);
    }


    public UserInfo checkIfUserAlreadyExists(UserInfoData userInfoData){
        return userRepository.findByUsername(userInfoData.getUserName());
    }
    public Boolean signupUser(UserInfoData userInfoData){
        //Define a function to check if userEmail and password are correct
        ValidationUtil.validateUser(userInfoData.getUserName(), userInfoData.getPassword());
        userInfoData.setPassword(passwordEncoder.encode(userInfoData.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoData))){
            return false;
        }
        String userId = UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId, userInfoData.getUserName(),userInfoData.getPassword(),new HashSet<>()));
        return true;
    }
}
