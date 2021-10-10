package com.wishlist.cst438project2.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.dto.SignInDTO;
import com.wishlist.cst438project2.dto.SignUpDTO;
import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import com.wishlist.cst438project2.service.AdminService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private FirebaseIntegration firebaseIntegration;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {

        log.info("AdminServiceImpl: Starting getAllUsers");

        List<UserDTO> userDTOList = firebaseIntegration.getAllUsers();

        log.info("AdminServiceImpl: Exiting getAllUsers");

        return userDTOList;
    }

    @SneakyThrows
    @Override
    public void deleteUser(String username) {

        log.info("AdminServiceImpl: Starting deleteUser");

        User user = fetchUser(username);

        firebaseIntegration.deleteUser(user.getUsername());

        log.info("AdminServiceImpl: Exiting deleteUser");
    }

    @SneakyThrows
    @Override
    public String createUser(SignUpDTO signUpDTO) {

        log.info("AdminServiceImpl: Starting createUser");

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

    @Override
    public String login(SignInDTO signInDTO) {

        log.info("AdminServiceImpl: Starting login");

        User user = fetchUser(signInDTO.getUsername());

        String msg = HttpStatus.UNAUTHORIZED.toString();

        if(Utils.checkPassword(signInDTO.getPassword(), user.getPassword())) {
            msg = Constants.USER_LOGIN_SUCCESSFUL;
        }

        log.info("AdminServiceImpl: Exiting login");
        return msg;
    }

    private User fetchUser(String username) {

        UserDTO dbUserDTO = firebaseIntegration.getUser(username);

        if(Objects.isNull(dbUserDTO)) {
            throw new BadRequestException(Constants.ERROR_USER_DOES_NOT_EXISTS.replace(Constants.KEY_USERNAME, username));
        }

        return modelMapper.map(dbUserDTO, User.class);
    }
}
