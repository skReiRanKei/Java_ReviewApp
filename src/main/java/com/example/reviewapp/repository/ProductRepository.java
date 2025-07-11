package com.example.reviewapp.repository;

import com.example.reviewapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Productエンティティのデータアクセスを管理するリポジトリインターフェース
 * Product：Productクラス
 * Long　　：エンティティの主キー
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
