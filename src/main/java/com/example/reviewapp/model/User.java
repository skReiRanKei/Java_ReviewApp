package com.example.reviewapp.model;

import jakarta.persistence.*; // JPA関連のアノテーションをまとめてインポート

/**
 * ユーザー情報を表すエンティティクラス
 * データベースの'users'テーブルに対応
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ユーザーID（主キー）

    @Column(nullable = false, unique = true)
    private String username; // ユーザー名（ログインIDとして使用、一意）

    @Column(nullable = false)
    private String password; // パスワード（ハッシュ化）

    /**
     * ユーザーIDを取得
     * @return ユーザーID
     */
    public Long getId() {
        return id;
    }

    /**
     * ユーザーIDを設定
     * @param id 設定するユーザーID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * ユーザー名を取得
     * @return ユーザー名
     */
    public String getUsername() {
        return username;
    }

    /**
     * ユーザー名を設定し
     * @param username 設定するユーザー名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * パスワードを取得
     * @return パスワード（ハッシュ化された値）
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定
     * @param password 設定するパスワード（ハッシュ化された値）
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
