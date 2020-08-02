package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.User;
import de.thm.swtp.information_portal.models.UserInformation;
import de.thm.swtp.information_portal.repositories.UserInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInformationService {

    @Autowired
    private UserInformationRepository userInformationRepository;

    /**
     *
     * @param id
     * @return
     */
    public Optional<UserInformation> getUserInfo(String id){
        return userInformationRepository.findById(id);
    }

    /**
     *
     * @param userInfo
     * @return
     */
    public UserInformation addUserInfo(UserInformation userInfo){
       return userInformationRepository.save(userInfo);
    }
}
