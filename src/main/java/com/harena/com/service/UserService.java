package com.harena.com.service;

import com.harena.com.file.BucketComponent;
import com.harena.com.model.User;
import com.harena.com.service.utils.SerializationFunctions;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class UserService {
    private final BucketComponent bucketComponent;
    private final SerializationFunctions<User> serializationFunctions;


    public UserService(BucketComponent bucketComponent, SerializationFunctions<User> serializationFunctions) {
        this.bucketComponent = bucketComponent;
        this.serializationFunctions = serializationFunctions;
    }

    public User registerUser(User user){
    try {
       /* File file=bucketComponent.download(user.email());
        if(file.exists()){
            throw new RuntimeException();
        }*/

       File serializedUser= serializationFunctions.serialize(user,"credential");
       String directory= user.email();
        bucketComponent.upload(serializedUser, directory+"/credential.txt");

    }catch (Exception e){
        e.printStackTrace();
    }
        return user;
    }
    public User findUserByEmail(String email){
        try {
            File user=bucketComponent.download(email+"/credential.txt");
            if(user.exists()){
                return serializationFunctions.decodeFile(user);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
