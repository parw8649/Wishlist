package com.wishlist.cst438project2.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.TokenManager;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.dto.*;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.exception.ExternalServerException;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import com.wishlist.cst438project2.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FirebaseIntegration firebaseIntegration;

    @Autowired
    private TokenManager tokenManager;

    @SneakyThrows
    @Override
    public String saveUser(SignUpDTO signUpDTO) {

        log.info("UserServiceImpl: Starting saveUser");

        UserDTO dbUserDTO = firebaseIntegration.getUser(signUpDTO.getUsername());

        User user;
        if(Objects.isNull(dbUserDTO)) {
            user = modelMapper.map(signUpDTO, User.class);
        } else
            throw new BadRequestException(Constants.ERROR_USER_ALREADY_EXISTS.replace(Constants.KEY_USERNAME, signUpDTO.getUsername()));

        user.setPassword(Utils.encodePassword(user.getPassword()));

        ApiFuture<WriteResult> collectionApiFuture = firebaseIntegration.dbFirestore.collection(Constants.DOCUMENT_USER).document(user.getUsername()).set(user);

        String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

        log.info("ResponseTimestamp: {}", responseTimestamp);

        log.info("UserServiceImpl: Exiting saveUser");

        return responseTimestamp;

    }

    @SneakyThrows
    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        log.info("UserServiceImpl: Starting updateUser");

        User user = fetchUser(userDTO.getUsername());

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmailId(userDTO.getEmailId());

        ApiFuture<WriteResult> collectionApiFuture = firebaseIntegration.dbFirestore.collection(Constants.DOCUMENT_USER).document(user.getUsername()).set(user);

        String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

        if(responseTimestamp.isEmpty()) {
            throw new ExternalServerException(Constants.ERROR_UNABLE_TO_UPDATE_USER);
        }

        log.info("UserServiceImpl: Exiting updateUser");

        return user.fetchUserDTO();
    }

    @SneakyThrows
    @Override
    public String changePassword(ChangePasswordDTO changePasswordDTO) {

        log.info("UserServiceImpl: Starting changePassword");

        User user = fetchUser(changePasswordDTO.getUsername());

        user.setPassword(Utils.encodePassword(changePasswordDTO.getPassword()));

        ApiFuture<WriteResult> collectionApiFuture = firebaseIntegration.dbFirestore.collection(Constants.DOCUMENT_USER).document(user.getUsername()).set(user);

        String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

        if(responseTimestamp.isEmpty()) {
            throw new ExternalServerException(Constants.ERROR_UNABLE_TO_UPDATE_USER);
        }

        log.info("UserServiceImpl: Exiting changePassword");

        return Constants.USER_PASSWORD_CHANGED_SUCCESSFULLY;
    }

    @Override
    public String login(SignInDTO signInDTO) {

        log.info("UserServiceImpl: Starting login");

        User user = fetchUser(signInDTO.getUsername());

        String accessToken = null;

        if(Utils.checkPassword(signInDTO.getPassword(), user.getPassword())) {
            log.info(Constants.USER_LOGIN_SUCCESSFUL);
            accessToken = tokenManager.generateToken(user);
        }

        log.info("UserServiceImpl: Exiting login");
        return accessToken;
    }

    @Override
    public void deleteUser(DeleteUserDTO deleteUserDTO) {

        log.info("UserServiceImpl: Starting deleteUser");

        User user = fetchUser(deleteUserDTO.getUsername());

        try {

            if (Utils.checkPassword(deleteUserDTO.getPassword(), user.getPassword()))
                firebaseIntegration.deleteUser(user.getUsername());
            else
                throw new BadRequestException(Constants.ERROR_INVALID_PASSWORD);

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }

        log.info("UserServiceImpl: Exiting deleteUser");
    }

    @Override
    public void logout(String accessToken) {

        log.info("UserServiceImpl: Starting logout");

        firebaseIntegration.deleteAccessToken(accessToken);

        log.info("UserServiceImpl: Exiting logout");
    }

    //Private Methods
    private User fetchUser(String username) {

        UserDTO dbUserDTO = firebaseIntegration.getUser(username);

        if(Objects.isNull(dbUserDTO)) {
            throw new BadRequestException(Constants.ERROR_USER_DOES_NOT_EXISTS.replace(Constants.KEY_USERNAME, username));
        }

        return modelMapper.map(dbUserDTO, User.class);
    }
}
