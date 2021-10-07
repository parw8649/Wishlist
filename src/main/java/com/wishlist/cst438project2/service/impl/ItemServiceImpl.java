package com.wishlist.cst438project2.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.document.Item;
import com.wishlist.cst438project2.dto.ItemDTO;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.exception.ExternalServerException;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import com.wishlist.cst438project2.service.ItemService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FirebaseIntegration firebaseIntegration;

    /**
     * database record creation route for item. Each new item will be set to an auto-incremented id
     * <p>
     * returns timestamp of record creation.
     */
    @SneakyThrows
    @Override
    public String createItem(ItemDTO itemDTO) {
        log.info("ItemServiceImpl: starting createItem");

        // check for existence of item by name and userId
        Item item = fetchItem(itemDTO.getName(), itemDTO.getUserId());
        // if item exists, don't add an identical item? throw err
        if (Objects.nonNull(item)) {
            throw new BadRequestException(Constants.ERROR_ITEM_ALREADY_EXISTS.replace(Constants.KEY_ITEM_NAME, itemDTO.getName()));
        } else {
            // If item does not exist, add the item
            item = modelMapper.map(itemDTO, Item.class);
            log.info("\n    name: " + item.getName() + "\n" + "    link: " + item.getLink() + "\n"
                    + "    description: " + item.getDescription() + "\n" + "    imgUrl: "
                    + item.getImgUrl() + "\n" + "    userId: " + item.getUserId());
        }
        String docId = item.getName();

        Map<String, Object> docData = new HashMap<>();
        docData.put(Constants.FIELD_ITEM_NAME, item.getName());
        docData.put(Constants.FIELD_ITEM_LINK, item.getLink());
        docData.put(Constants.FIELD_ITEM_DESCRIPTION, item.getDescription());
        docData.put(Constants.FIELD_ITEM_IMG_URL, item.getImgUrl());
        docData.put(Constants.FIELD_ITEM_USER_ID, item.getUserId());

        ApiFuture<WriteResult> collectionsApiFuture = firebaseIntegration.dbFirestore.collection(Constants.DOCUMENT_ITEM).document(docId).set(docData);
        String responseTimeStamp = collectionsApiFuture.get().getUpdateTime().toString();

        log.info("ItemServiceImpl: createItem: responseTimeStamp: {}", responseTimeStamp);
        log.info("ItemServiceImpl: exiting createItem");
        return responseTimeStamp;
    }

    /**
     * retrieve all documents from item collection
     * returns a list of all created items
     */
    @SneakyThrows
    @Override
    public List<ItemDTO> getAllItems() {
        log.info("ItemServiceImpl: starting getAllItems");
        List<ItemDTO> collection = firebaseIntegration.getAllItems();

        log.info("ItemServiceImpl: exiting getAllItems");
        return collection;
    }

    /**
     * returns the item found in database by given name
     */
    private Item fetchItem(String name, int userId) {
        ItemDTO dbItemDTO = firebaseIntegration.getItem(name, userId);

        if(Objects.isNull(dbItemDTO)) {
//            throw new BadRequestException(Constants.ERROR_ITEM_DOES_NOT_EXISTS.replace(Constants.KEY_ITEM_NAME, name));
            Item item = null;
            return item;
        }

        return modelMapper.map(dbItemDTO, Item.class);
    }
}
