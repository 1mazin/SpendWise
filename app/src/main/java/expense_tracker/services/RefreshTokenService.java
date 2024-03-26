package expense_tracker.services;

import expense_tracker.entities.RefreshToken;
import expense_tracker.entities.UserInfo;
import expense_tracker.models.UserInfoData;
import expense_tracker.repository.RefreshTokenRepository;
import expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
public class RefreshTokenService {
    @Autowired RefreshTokenRepository refreshTokenRepository;

    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;
    public RefreshToken createRefreshToken(String userName){
        UserInfo userInfoExtracted = userRepository.findByUsername(userName);
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();


        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException((token.getToken()+"Refresh token is Expired .Please login again"));
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }



}
