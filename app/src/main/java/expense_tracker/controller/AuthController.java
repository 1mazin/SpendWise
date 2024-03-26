package expense_tracker.controller;

import expense_tracker.entities.RefreshToken;
import expense_tracker.models.UserInfoData;
import expense_tracker.response.JwtResponseDTO;
import expense_tracker.services.JwtService;
import expense_tracker.services.RefreshTokenService;
import expense_tracker.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoData userInfoData){
        try{
            Boolean isSignUped = userDetailsServiceImpl.signupUser(userInfoData);
            if(Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity<>( "Already Exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoData.getUserName());
            String jwtToken = jwtService.GenerateToken(userInfoData.getUserName());
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken).
                    token(refreshToken.getToken()).build(), HttpStatus.OK);
        }
        catch(Exception E){
            return new ResponseEntity<>("Exception in User Service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
