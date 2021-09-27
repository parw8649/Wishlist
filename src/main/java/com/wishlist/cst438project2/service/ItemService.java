package com.wishlist.cst438project2.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.v1.FirestoreClient;
import com.wishlist.cst438project2.document.Item;

import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    public String createItem(Item item);
    
}
