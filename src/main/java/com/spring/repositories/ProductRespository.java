package com.spring.repositories;

import com.spring.models.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dewes on 16/07/2016.
 */
public interface ProductRespository extends CrudRepository<Product, Integer> {}
