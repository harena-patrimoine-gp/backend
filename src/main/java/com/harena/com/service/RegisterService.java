package com.harena.com.service;

import com.harena.com.file.BucketComponent;
import com.harena.com.model.User;
import com.harena.com.service.utils.SerializationFunctions;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class RegisterService {
    private final SerializationFunctions<User> serializationFunctions;
    private final BucketComponent bucketComponent;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public RegisterService(SerializationFunctions<User> serializationFunctions, BucketComponent bucketComponent, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.serializationFunctions = serializationFunctions;
        this.bucketComponent = bucketComponent;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public String registerUser(User user){
        try {

            User userWithPWD=user.setPassword("{noop}"+user.password());
            File serializedUser= serializationFunctions.serialize(userWithPWD,"credential");
            String directory= user.email();

            bucketComponent.upload(serializedUser, directory+"/credential.txt");

            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.email(),user.password()
            ));
            return tokenService.generateToken(authentication);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
