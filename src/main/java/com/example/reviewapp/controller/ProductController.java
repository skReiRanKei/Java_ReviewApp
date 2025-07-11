package com.example.reviewapp.controller;

import com.example.reviewapp.model.Product;
import com.example.reviewapp.model.Review;
import com.example.reviewapp.repository.ProductRepository;
import com.example.reviewapp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品情報とレビュー表示、レビュー投稿機能を提供するコントローラークラス
 */
@Controller
public class ProductController {

    // ProductRepositoryを自動で注入（DI）商品データへのアクセスに使用
    @Autowired
    private ProductRepository productRepository;
    // ReviewRepositoryを自動で注入（DI）レビューデータへのアクセスに使用
    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * 商品一覧ページを表示
     * 各商品の平均評価を計算し、表示用に整形してモデルに追加
     * @param model ビューにデータを渡すためのModelオブジェクト
     * @return "product-list" テンプレートの論理名（product-list.htmlを表示）
     */
    @GetMapping({"/", "/products"})
    public String showProductList(Model model) {
        // 全ての商品をデータベースから取得
        List<Product> products = productRepository.findAll();

        // 各商品に対してレビューを取得し、平均評価を計算
        for (Product product : products) {
            List<Review> reviews = reviewRepository.findByProductId(product.getId());
            // レビューがない場合は0.0をデフォルト値として平均評価を計算
            double avgRating = reviews.stream()
                    .mapToInt(r -> r.getRating()) // レビューの評価値を取得
                    .average() // 平均を計算
                    .orElse(0.0); // レビューがない場合のデフォルト値

            // 平均評価を小数点以下1桁に丸めます。
            BigDecimal avg = BigDecimal.valueOf(avgRating);
            avg = avg.setScale(1, RoundingMode.HALF_UP); // 四捨五入

            // 表示用の評価文字列（例: "★3.5"）と数値の平均評価をProductオブジェクトに設定
            product.setRatingDisplay("★" + avg.toString());
            product.setAverageRating(avg.doubleValue());
        }

        // 商品リストをモデルに追加し、ビューで表示
        model.addAttribute("products", products);
        return "product-list";
    }

    /**
     * 特定の商品の詳細ページを表示
     * 商品情報、関連するレビュー、平均評価、評価の内訳などを計算し、モデルに追加
     *
     * @param id 表示する商品のID
     * @param model ビューにデータを渡すためのModelオブジェクト
     * @param principal 現在認証されているユーザー情報（ログイン状態の判定に使用）
     * @return "product-detail" テンプレートの論理名（product-detail.htmlを表示）、または商品が見つからない場合は商品一覧へリダイレクト
     */
    @GetMapping("/products/{id}")
    public String showProductDetail(@PathVariable Long id, Model model, Principal principal) {
        // 指定されたIDの商品をデータベースから取得見つからない場合はnull。
        Product product = productRepository.findById(id).orElse(null);

        // 商品が見つからない場合は商品一覧ページにリダイレクト
        if (product == null) return "redirect:/products";

        // その商品に関連する全てのレビューを取得
        List<Review> reviews = reviewRepository.findByProductId(id);

        // レビューの平均評価を計算
        double avgRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);

        // 平均評価を切り捨てて、星の表示などに使用
        int roundedRating = (int) Math.floor(avgRating);

        // 評価の内訳（各評価（1-5）の件数とパーセンテージ）を計算
        Map<Integer, Integer> ratingCount = new HashMap<>(); // 各評価の件数
        Map<Integer, Integer> ratingBreakdown = new HashMap<>(); // 各評価のパーセンテージ
        int total = reviews.size(); // 総レビュー数

        for (int i = 1; i <= 5; i++) {
            final int finalRating = i; // ラムダ式内で使用するためfinalにする
            // 現在の評価値（i）に一致するレビューの数をカウント
            int count = (int) reviews.stream().filter(r -> r.getRating() == finalRating).count();
            // 総レビュー数に基づいてパーセンテージを計算総レビューが0の場合は0%と
            int percentage = (total > 0) ? (count * 100 / total) : 0;
            ratingCount.put(i, count);
            ratingBreakdown.put(i, percentage);
        }

        // 評価バーの色のマッピングを設定（任意の色設定）
        Map<Integer, String> ratingColors = new HashMap<>();
        ratingColors.put(5, "#28a745"); // 緑
        ratingColors.put(4, "#007bff"); // 青
        ratingColors.put(3, "#17a2b8"); // 水色
        ratingColors.put(2, "#ffc107"); // 黄色
        ratingColors.put(1, "#dc3545"); // 赤
        model.addAttribute("ratingColors", ratingColors);

        // 平均評価を小数点以下1桁に丸めます。（再度計算していますが、showProductListと同じロジック）
        BigDecimal avg = BigDecimal.valueOf(avgRating);
        avg = avg.setScale(1, RoundingMode.HALF_UP);

        // 表示用の評価文字列と数値の平均評価をProductオブジェクトに設定
        product.setRatingDisplay("★" + avg.toString());
        product.setAverageRating(avg.doubleValue());

        // 各データをモデルに追加
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("roundedRating", roundedRating);
        model.addAttribute("ratingBreakdown", ratingBreakdown);
        model.addAttribute("ratingCount", ratingCount);

        // ユーザーがログインしているかどうかを判定し、その情報をビューに渡す
        model.addAttribute("isLoggedIn", principal != null);

        return "product-detail";
    }

    /**
     * 商品に対するレビューを投稿
     * @param id 投稿対象の商品のID
     * @param username レビュー投稿者のユーザー名
     * @param rating 評価（1～5）
     * @param comment レビューコメント
     * @param principal 現在認証されているユーザー情報
     * @return レビュー投稿後、商品の詳細ページにリダイレクト
     */
    @PostMapping("/products/{id}/reviews")
    public String submitReview(@PathVariable Long id,
                               @RequestParam String username,
                               @RequestParam int rating,
                               @RequestParam String comment,
                               Principal principal) {

        // ユーザーがログインしていない場合、ログインページへリダイレクト
        if (principal == null) {
            return "redirect:/user/login";
        }

        // 投稿対象の商品をデータベースから取得
        Product product = productRepository.findById(id).orElse(null);

        // 商品が見つからない場合は商品一覧ページにリダイレクト
        if (product == null) return "redirect:/products";

        // 新しいReviewオブジェクトを作成し、フォームから受け取ったデータを設定
        Review review = new Review();
        review.setProduct(product); // どの商品に対するレビューかを設定
        review.setUsername(username);
        review.setRating(rating);
        review.setComment(comment);

        // レビューをデータベースに保存
        reviewRepository.save(review);

        // レビュー投稿後、レビューした商品の詳細ページにリダイレクトして、最新のレビューを表示
        return "redirect:/products/" + id;
    }
}
