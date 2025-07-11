package com.example.reviewapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 管理者ログインページの表示
 */
@Controller
public class LoginController {

    /**
     * 管理者ログインページを表示します。
     * @return "login" テンプレートの論理名（login.htmlを表示）
     */
    @GetMapping("/login")
    public String login() {

        // ユーザーが認証されていない場合にこのページを表示
        return "login";
    }
}
