package com.wishlist.cst438project2.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.dto.ChangePasswordDTO;
import com.wishlist.cst438project2.dto.SignUpDTO;
import com.wishlist.cst438project2.dto.UserDTO;
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

    @SneakyThrows
    @Override
    public String saveUser(SignUpDTO signUpDTO) {

        log.info("UserServiceImpl: Starting saveUser");

        UserDTO dbUserDTO = firebaseIntegration.getUser(signUpDTO.getUsername());

        if (Objects.nonNull(dbUserDTO)) {
            throw new BadRequestException(Constants.ERROR_USER_ALREADY_EXISTS.replace(Constants.KEY_USERNAME, signUpDTO.getUsername()));
        }
        
        User user = modelMapper.map(signUpDTO, User.class);

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

        UserDTO dbUserDTO = firebaseIntegration.getUser(userDTO.getUsername());

        if(Objects.isNull(dbUserDTO)) {
            throw new BadRequestException(Constants.ERROR_USER_DOES_NOT_EXISTS.replace(Constants.KEY_USERNAME, userDTO.getUsername()));
        }

        User user = modelMapper.map(userDTO, User.class);

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

        UserDTO dbUserDTO = firebaseIntegration.getUser(changePasswordDTO.getUsername());

        if(Objects.isNull(dbUserDTO)) {
            throw new BadRequestException(Constants.ERROR_USER_DOES_NOT_EXISTS.replace(Constants.KEY_USERNAME, changePasswordDTO.getUsername()));
        }

        User user = modelMapper.map(dbUserDTO, User.class);

        user.setPassword(Utils.encodePassword(changePasswordDTO.getPassword()));

        ApiFuture<WriteResult> collectionApiFuture = firebaseIntegration.dbFirestore.collection(Constants.DOCUMENT_USER).document(user.getUsername()).set(user);

        String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

        if(responseTimestamp.isEmpty()) {
            throw new ExternalServerException(Constants.ERROR_UNABLE_TO_UPDATE_USER);
        }

        log.info("UserServiceImpl: Exiting changePassword");

        return Constants.USER_PASSWORD_CHANGED_SUCCESSFULLY;
    }
}
