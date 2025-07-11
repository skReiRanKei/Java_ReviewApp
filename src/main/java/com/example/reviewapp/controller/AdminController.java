package com.example.reviewapp.controller;

import com.example.reviewapp.model.Product;
import com.example.reviewapp.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 管理者向けの商品管理機能を提供するコントローラークラス
 * 主に商品の登録、一覧表示、削除などの操作
 */
@Controller
@RequestMapping("/admin/products") 
public class AdminController {

    private final ProductRepository productRepository; // 商品データへのアクセスを提供するリポジトリ

    /**
     * AdminControllerのコンストラクタ
     * @param productRepository 商品データを操作するためのリポジトリ
     */
    public AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 新しい商品を登録するためのフォーム画面を表示
     * @param model ビューにデータを渡すためのModelオブジェクト
     * @return "product-form" テンプレートの論理名
     */
    @GetMapping("/new")
    public String showProductForm(Model model) {

        model.addAttribute("product", new Product());
        return "product-form"; // product-form.html を表示
    }

    /**
     * 商品登録フォームから送信されたデータを受け取り、商品をデータベースに保存
     * @param product フォームからバインドされたProductオブジェクト
     * @return 商品登録後、管理者用の商品一覧ページにリダイレクト
     */
    @PostMapping
    public String registerProduct(@ModelAttribute Product product) {
        // 受け取ったProductオブジェクトをデータベースに保存
        productRepository.save(product);

        return "redirect:/admin/products";
    }

    /**
     * 管理者向けの商品一覧画面を表示
     * @param model ビューにデータを渡すためのModelオブジェクト
     * @return "admin-product" テンプレートの論理名
     */
    @GetMapping
    public String showAdminProduct(Model model) {
        // データベースから全ての商品を取得
        List<Product> products = productRepository.findAll();

        // 取得した商品リストをモデルに追加し、ビューで表示
        model.addAttribute("products", products);

        return "admin-product"; // admin-product.html を表示
    }

    /**
     * 指定されたIDの商品をデータベースから削除
     * @param id 削除する商品のID
     * @return 商品削除後、管理者用の商品一覧ページにリダイレクト
     */
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        // 指定されたIDの商品をデータベースから削除
        productRepository.deleteById(id);

        // 削除後、管理者商品一覧ページにリダイレクトして最新の状態を表示
        return "redirect:/admin/products";
    }
}
