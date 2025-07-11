package com.example.reviewapp.model;

import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 

/**
 * 商品情報を表すエンティティクラス
 * データベースの'products'テーブルに対応
 */
@Entity
public class Product {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; // 商品ID（主キー）

    private String name; // 商品名
    private String description; // 商品説明
    private int price; // 価格
    private String imageUrl; // 商品画像のURL
    private double averageRating; // 商品の平均評価

    // Thymeleafなどで表示用に整形された評価文字列を保持するフィールド
    private String ratingDisplay;

    /**
     * デフォルトコンストラクタ。
     */
    public Product() {}

    /**
     * 商品の初期情報を設定するためのコンストラクタ。
     *
     * @param name 商品名
     * @param description 商品説明
     * @param price 価格
     * @param imageUrl 商品画像のURL
     */
    public Product(String name, String description, int price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    /**
     * 表示用に整形された評価文字列（例: "★3.5"）を取得
     * @return 整形された評価文字列
     */
    public String getRatingDisplay() {
        return ratingDisplay;
    }

    /**
     * 表示用に整形された評価文字列を設定
     * @param ratingDisplay 設定する整形された評価文字列
     */
    public void setRatingDisplay(String ratingDisplay) {
        this.ratingDisplay = ratingDisplay;
    }

    /**
     * 商品IDを取得
     * @return 商品ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 商品名を取得
     * @return 商品名
     */
    public String getName() {
        return name;
    }

    /**
     * 商品説明を取得
     * @return 商品説明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 価格を取得
     * @return 価格
     */
    public int getPrice() {
        return price;
    }

    /**
     * 商品画像のURLを取得
     * @return 商品画像のURL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 商品の平均評価を取得
     * @return 平均評価
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * 商品IDを設定
     * @param id 設定する商品ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 商品名を設定
     * @param name 設定する商品名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 商品説明を設定
     * @param description 設定する商品説明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 価格を設定
     * @param price 設定する価格
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * 商品画像のURLを設定
     * @param imageUrl 設定する商品画像のURL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 商品の平均評価を設定
     * @param averageRating 設定する平均評価
     */
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
