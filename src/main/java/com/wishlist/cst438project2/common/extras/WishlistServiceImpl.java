package com.wishlist.cst438project2.common.extras;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.dto.AddItemsWishlistDTO;
import com.wishlist.cst438project2.dto.ItemDTO;
import com.wishlist.cst438project2.dto.WishlistResponseDTO;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private FirebaseIntegration firebaseIntegration;

    @SneakyThrows
    @Override
    public Wishlist addItemsWishlist(AddItemsWishlistDTO addItemsWishlistDTO) {

        log.info("WishlistServiceImpl: Starting addItemsWishlist");

        Wishlist dbWishlist = fetchWishlistByUser(addItemsWishlistDTO.getUserId());

        if(Objects.isNull(dbWishlist))
            dbWishlist = new Wishlist(addItemsWishlistDTO.getUserId(), addItemsWishlistDTO.getItemIds());
        else {

            List<Long> itemIds = dbWishlist.getItemIds();
            itemIds.addAll(addItemsWishlistDTO.getItemIds());
            dbWishlist.setItemIds(itemIds);
        }

        ApiFuture<WriteResult> collectionApiFuture = firebaseIntegration.dbFirestore.collection(Constants.DOCUMENT_USER_WISHLIST).document(String.valueOf(addItemsWishlistDTO.getUserId())).set(dbWishlist);

        String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

        log.info("WishlistServiceImpl: Exiting addItemsWishlist");

        if(!responseTimestamp.isEmpty()) {
             return dbWishlist;
        }

        return null;
    }

    @Override
    public WishlistResponseDTO getWishlistByUser(Long userId) {

        log.info("WishlistServiceImpl: Starting addItemsWishlist");

        Wishlist dbWishlist = fetchWishlistByUser(userId);

        if(Objects.isNull(dbWishlist))
            return null;
        else {

            List<ItemDTO> itemDTOList = new LinkedList<>();

            for(Long itemId : dbWishlist.getItemIds()) {

                try {
                    ItemDTO itemDTO = firebaseIntegration.fetchItemByItemId(itemId);

                    if(Objects.nonNull(itemDTO))
                        itemDTOList.add(itemDTO);

                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }

            return new WishlistResponseDTO(userId, itemDTOList);
        }
    }

    private Wishlist fetchWishlistByUser(Long userId) {

        return firebaseIntegration.getUserWishlist(userId);
    }
}
