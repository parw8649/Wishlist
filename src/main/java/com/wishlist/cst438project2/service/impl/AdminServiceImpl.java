package com.wishlist.cst438project2.service.impl;

import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import com.wishlist.cst438project2.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private FirebaseIntegration firebaseIntegration;

    @Override
    public List<UserDTO> getAllUsers() {

        log.info("AdminServiceImpl: Starting getAllUsers");

        List<UserDTO> userDTOList = firebaseIntegration.getAllUsers();

        log.info("AdminServiceImpl: Exiting getAllUsers");

        return userDTOList;
    }
}
