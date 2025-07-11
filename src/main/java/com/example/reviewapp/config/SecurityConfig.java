package com.example.reviewapp.config;

import com.example.reviewapp.repository.UserRepository;
import com.example.reviewapp.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * アプリケーションのセキュリティ設定を定義するクラス
 * 管理者用と一般ユーザー/公開ページ用のそれぞれ異なる認証・認可ルールを適用
 */
@Configuration
public class SecurityConfig {

    /**
     * 管理者用のSecurityFilterChainを定義
     * /admin/** パス、および管理者ログイン(/login)・ログアウト(/logout)を処理
     *
     * @param http HttpSecurityオブジェクト
     * @return 構築されたSecurityFilterChain
     * @throws Exception 設定中に発生する可能性のある例外
     */
    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http

                // /admin/** パス、および管理者ログイン(/login)・ログアウト(/logout)にマッチ
                .securityMatcher("/admin/**", "/login", "/logout")

                // HTTPリクエストの承認ルールを設定
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 管理者ログインページとログアウトURLは認証なしでアクセスを許可
                        .requestMatchers("/login", "/logout").permitAll()
                        .anyRequest().authenticated()
                )

                // フォームベース認証の設定
                .formLogin(form -> form
                        .loginPage("/login") // 管理者ログインページのURL
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/admin/products", true)
                        .permitAll()
                )

                // ログアウトの設定
                .logout(logout -> logout
                        .logoutUrl("/logout") // 管理者ログアウトのURL
                        .logoutSuccessUrl("/")
                        .permitAll()
                )

                // 管理者ユーザーの認証情報を提供
                .userDetailsService(new InMemoryUserDetailsManager(
                        User.withUsername("admin") // ユーザー名
                                .password(passwordEncoder().encode("admin123")) // パスワード（BCryptでエンコード）
                                .roles("ADMIN") // ロール
                                .build()
                ));
        return http.build();
    }

    /**
     * 一般ユーザーおよび公開ページ用のSecurityFilterChainを定義
     *
     * @param http HttpSecurityオブジェクト
     * @param userDetailsService カスタムのUserDetailsService（データベースからのユーザー認証用）
     * @return 構築されたSecurityFilterChain
     * @throws Exception 設定中に発生する可能性のある例外
     */
    @Bean
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http,
                                                       UserDetailsService userDetailsService) throws Exception {
        http

                // 管理者用フィルターチェーンにマッチしない、残りの主要なパスにマッチ
                .securityMatcher("/user/**", "/reviews/**", "/products/**", "/")

                // HTTPリクエストの承認ルールを設定
                .authorizeHttpRequests(authz -> authz

                        // 認証されたユーザーのみが投稿できるように制限
                        .requestMatchers(HttpMethod.POST, "/products/*/reviews").hasAnyRole("USER", "ADMIN")
                        // ユーザー登録ページは認証なしでアクセスを許可
                        .requestMatchers("/user/register").permitAll()
                        // ユーザーログインページとログアウトURLは認証なしでアクセスを許可
                        .requestMatchers("/user/login", "/user/logout").permitAll()
                        // 商品一覧、商品詳細、トップページ、レビュー参照は認証なしでアクセスを許可
                        .requestMatchers("/", "/products", "/products/{id}", "/reviews/**").permitAll()
                        // 上記以外の、このsecurityMatcherにマッチするリクエストは認証済みである必要がある
                        .anyRequest().authenticated()
                )

                // フォームベース認証の設定
                .formLogin(form -> form
                        .loginPage("/user/login") // ユーザーログインページのURL
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/products", true)
                        .permitAll()
                )
                // ログアウトの設定
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/products")
                        .permitAll() // ログアウトURLへのアクセスを許可
                )

                // カスタムのUserDetailsServiceを設定し、データベースからのユーザー認証を有効に
                .userDetailsService(userDetailsService);

        return http.build();
    }

    /**
     * パスワードエンコーダーを定義
     *
     * @return BCryptPasswordEncoderのインスタンス
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * ユーザー詳細サービスを定義
     *
     * @param userRepository ユーザーデータにアクセスするためのリポジトリ
     * @return UserDetailsServiceのインスタンス
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }
}
