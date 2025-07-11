package com.example.reviewapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/**
 * 商品に対するレビュー情報を表すエンティティクラス
 * データベースの'reviews'テーブルに対応
 */
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // レビューID（主キー）

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // このレビューがどの商品に対するものかを示すProductエンティティへの参照

    private String username; // レビュー投稿者のユーザー名
    private int rating; // 評価（例: 1～5の整数）
    private String comment; // レビューのコメント本文

    /**
     * デフォルトコンストラクタ。
     * JPAの仕様により、エンティティクラスには引数なしのpublicまたはprotectedなコンストラクタが必要です。
     */
    public Review() {
    }

    /**
     * レビューの初期情報を設定するためのコンストラクタ。
     *
     * @param username レビュー投稿者のユーザー名
     * @param comment レビューのコメント本文
     * @param rating 評価（1～5の整数）
     */
    public Review(String username, String comment, int rating) {
        this.username = username;
        this.comment = comment;
        this.rating = rating;
    }

    /**
     * このレビューが関連付けられている商品を取得します。
     * @return 関連するProductエンティティ
     */
    public Product getProduct() {
        return product;
    }

    /**
     * このレビューが関連付けられている商品を設定します。
     * @param product 設定するProductエンティティ
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * レビュー投稿者のユーザー名を設定します。
     * @param username 設定するユーザー名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * レビュー投稿者のユーザー名を取得します。
     * @return ユーザー名
     */
    public String getUsername() {
        return username;
    }

    /**
     * レビューコメントを取得します。
     * @return コメント本文
     */
    public String getComment() {
        return comment;
    }

    /**
     * 評価を取得します。
     * @return 評価（整数値）
     */
    public int getRating() {
        return rating;
    }

    /**
     * 評価を設定します。
     * @param rating 設定する評価（整数値）
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * レビューコメントを設定します。
     * @param comment 設定するコメント本文
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
