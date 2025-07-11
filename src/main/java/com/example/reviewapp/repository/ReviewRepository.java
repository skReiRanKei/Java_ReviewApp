package com.example.reviewapp.repository;

import com.example.reviewapp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Reviewエンティティのデータアクセスを管理するリポジトリインターフェース
 * Review：Reviewクラス
 * Long：エンティティの主キーの型
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * 指定された商品ID（productId）に紐づく全てのレビューを検索して返す
     * @param productId 検索対象となる商品のID
     * @return 指定された商品IDに紐づくReviewエンティティのリスト
     */
    List<Review> findByProductId(Long productId);
}
