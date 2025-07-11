package com.example.reviewapp.controller;

import com.example.reviewapp.form.UserForm;
import com.example.reviewapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 * ユーザー登録機能を提供するコントローラークラス
 * ユーザー登録フォームの表示と、登録処理を担当
 * 登録成功後、自動的にログイン状態にする処理も含む
 */
@Controller
public class UserRegisterController {

    private final UserService userService; // ユーザー関連のビジネスロジックを提供するサービス
    private final PasswordEncoder passwordEncoder; // パスワードのエンコードに使用
    private final UserDetailsService userDetailsService; // ユーザー詳細情報を取得するサービス

    /**
     * UserRegisterControllerのコンストラクタ
     * @param userService ユーザー登録などのビジネスロジックを処理するサービス
     * @param passwordEncoder パスワードのハッシュ化に使用するエンコーダー
     * @param userDetailsService ユーザー名からUserDetailsを取得するサービス
     */
    public UserRegisterController(UserService userService,
                                  PasswordEncoder passwordEncoder,
                                  UserDetailsService userDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    /**
     * ユーザー登録フォーム画面を表示
     * @return "user-register" テンプレートの論理名（user-register.htmlを表示）
     */
    @GetMapping("/user/register")
    public String showRegisterForm() {
        return "user-register";
    }

    /**
     * ユーザー登録フォームから送信されたデータを受け取り、ユーザーを登録
     *
     * @param form フォームからバインドされたUserFormオブジェクト（ユーザー名とパスワードを含む）
     * @param request HTTPリクエストオブジェクト（セッションにSecurityContextを保存するために使用）
     * @return ユーザー登録およびログイン成功後、商品一覧ページにリダイレクト
     */
    @PostMapping("/user/register")
    public String processRegister(@ModelAttribute UserForm form, HttpServletRequest request) {

        // ユーザー登録サービスを呼び出し、ユーザー名とパスワードを保存
        userService.registerUser(form.getUsername(), form.getPassword());

        // 登録されたユーザーのUserDetails（認証に必要な情報）を取得
        UserDetails userDetails = userDetailsService.loadUserByUsername(form.getUsername());

        // 認証トークン（UsernamePasswordAuthenticationToken）を作成
        // ユーザー詳細と、認証済みの場合はnullの認証情報、そしてユーザーの権限（ロール）を含める
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // 現在のスレッドのSecurityContextに認証トークンを設定し、ユーザーをログイン状態
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // セッションにSecurityContextを明示的に保存
        request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        // 登録とログインが成功したら、商品一覧ページにリダイレクト
        return "redirect:/products";
    }
}
