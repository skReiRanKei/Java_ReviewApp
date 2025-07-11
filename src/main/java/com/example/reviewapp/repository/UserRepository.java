package com.example.reviewapp.repository;

import com.example.reviewapp.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Userエンティティのデータアクセスを管理するリポジトリインターフェース
 * User :Userクラス
 * Long :エンティティの主キー
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 指定されたユーザー名（username）に紐づくユーザーを検索して返す
     *
     * @param username 検索対象となるユーザー名
     * @return 指定されたユーザー名に紐づくUserエンティティをOptionalでラップしたもの
     */
    Optional<User> findByUsername(String username);
}
