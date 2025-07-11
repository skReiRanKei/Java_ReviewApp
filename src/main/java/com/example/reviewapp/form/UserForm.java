package com.example.reviewapp.form;

/**
 * ユーザー登録フォームから送信されるデータを受け取るためのフォームオブジェクト（DTO: Data Transfer Object）
 * ユーザー名、パスワード、パスワード確認のフィールドを持つ
 */
public class UserForm {
    private String username; // ユーザー名
    private String password; // パスワード
    private String confirmPassword; // パスワード確認用フィールド

    /**
     * ユーザー名を取得
     * @return ユーザー名
     */
    public String getUsername() {
        return username;
    }

    /**
     * ユーザー名を設定
     * @param username 設定するユーザー名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * パスワードを取得
     * @return パスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定
     * @param password 設定するパスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 確認用パスワードを取得
     * @return 確認用パスワード
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * 確認用パスワードを設定
     * @param confirmPassword 設定する確認用パスワード
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
