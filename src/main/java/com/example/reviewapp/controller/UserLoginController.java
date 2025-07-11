package com.example.reviewapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 一般ユーザー用のログイン機能を提供するコントローラークラス
 * 主にユーザーログインページの表示を担当
 */
@Controller
@RequestMapping("/user")
public class UserLoginController {

    /**
     * ユーザーログインページを表示
     * @return "user-login" テンプレートの論理名（user-login.htmlを表示）
     */
    @GetMapping("/login")
    public String userLogin() {
        // Spring SecurityがこのURLをログインページとして認識し、ユーザーが認証されていない場合にこのページを表示
        return "user-login";
    }
}
