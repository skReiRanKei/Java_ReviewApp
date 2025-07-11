# 📦 商品レビューWebアプリ

---

## 💡 開発の目的

- Javaの開発環境に触れるため
- Webアプリ開発の基礎技術を学ぶため
- 権限管理や画面遷移の実装を体験するため


---

## ⚙️ 機能概要

- ユーザー登録・ログイン機能
- 商品一覧表示
  - 商品情報の表示
  - 平均レビュー点数表示
- 商品詳細表示
  - 商品レビュー一覧
  - ログイン済みユーザーによるレビュー投稿
- 管理者機能
  - 商品登録
  - 商品削除
- 権限ごとに画面表示を切り替え
- Bootstrapによるレスポンシブ対応UI

---

## 🗺️ 全体の遷移概要

以下は、ユーザー権限ごとの大まかな画面遷移イメージです。

- <div>👥 一般ユーザー（ログイン済）<br>
  <img src="infoReadMe/loginUser.jpg" alt="一般ユーザーログイン後の画面" width="400"></div>

- <div>🔐 管理者<br>
  <img src="infoReadMe/Admin.jpg" alt="管理者ログイン後の画面" width="400"></div>

- <div>🚪 未ログインユーザー<br>
  <img src="infoReadMe/notLogin.jpg" alt="未ログインユーザーの画面" width="400"></div>


## 🖼️ 権限ごとの画面キャプチャ

### 🚪 未ログインユーザー

#### 商品一覧画面

- <div>右上にログインボタンのみ表示<br>
  <img src="infoReadMe/capture/guest-product-list.png" alt="未ログインユーザーの商品一覧画面" width="800"></div>

#### 商品詳細画面

- <div>「レビュー投稿にはログインが必要です」メッセージ表示<br>
  ログインページへのリンクあり<br>
  <img src="infoReadMe/capture/guest-product-detail.png" alt="未ログインユーザーの商品詳細画面" width="800"></div>

#### ユーザーログイン画面

- <div>ユーザー用ログインページ<br>
  <img src="infoReadMe/capture/user-login.png" alt="ユーザーログイン画面" width="800"></div>

#### ユーザー登録画面
- <div>ユーザー新規登録フォーム<br>
  <img src="infoReadMe/capture/user-register.png" alt="ユーザー登録画面" width="800"></div>


---

### 👤 一般ユーザー（ログイン済）

#### 商品一覧画面

- <div>右上にユーザー名表示<br>
  ログアウトボタン表示<br>
  <img src="infoReadMe/capture/user-product-list.png" alt="一般ユーザーログイン後の商品一覧画面" width="800"></div>

#### 商品詳細画面

- <div>星の平均点や分布グラフを表示<br>
  <img src="infoReadMe/capture/user-product-detail.png" alt="一般ユーザーログイン後の商品詳細画面（評価表示）" width="800"></div>

- <div>レビュー投稿フォームが表示される<br>
  <img src="infoReadMe/capture/posting-form.png" alt="レビュー投稿フォーム" width="800"></div>

---

### 🔒 管理者用画面

#### 管理者ログイン画面

- <div>管理者用のログインページ<br>
  <img src="infoReadMe/capture/admin-login.png" alt="管理者ログイン画面" width="800"></div>

#### 管理者用 商品一覧画面

- <div>管理者用の商品一覧画面<br>
  <img src="infoReadMe/capture/admin-list.png" alt="管理者用商品一覧画面" width="800"></div>

#### 管理者用 商品登録画面
- <div>商品情報を新規登録<br>
  <img src="infoReadMe/capture/admin-product-add.png" alt="管理者用商品登録画面" width="800"></div>

---

## 🗂️ 権限 × 画面対応表

| 画面                             | 管理者 | 一般ユーザー（ログイン済） | 未ログインユーザー |
|----------------------------------|--------|----------------------------|--------------------|
| 商品一覧画面                     | ○      | ○                          | ○                  |
| 商品詳細画面                     | ○      | ○（レビュー投稿可）        | ○（レビュー投稿不可） |
| ユーザーログイン画面             | ○      | ×                          | ○                  |
| ユーザー登録画面                 | ○      | ×                          | ○                  |
| 管理者ログイン画面               | ○      | ×                          | ×                  |
| 管理者用 商品一覧画面            | ○      | ×                          | ×                  |
| 管理者用 商品登録画面            | ○      | ×                          | ×                  |

---

## 💡 工夫点

- Spring Security で管理者とユーザーの認可を分離
- 管理者用とユーザー用でログイン画面を分けて実装
- URLごとにセキュリティチェーンを設定
- Thymeleaf の `sec:authorize` を活用し、ログイン状態に応じて画面表示を切り替え
- Bootstrap を利用し、レスポンシブ対応の見やすい UI を実現
- 商品ごとのレビュー平均点を算出・表示
- 評価分布を可視化（星の色分けやバーグラフ表示）

---

## 🛠 技術スタック

- Java 17
- Spring Boot 3.5.x
- Spring Security 6
- Spring Data JPA
- Thymeleaf
- H2 Database（開発用）
- Bootstrap 5
- Maven

---

## 🚀 今後の展望

- 管理画面に商品編集機能を追加
- 管理者登録の実装
- レビューの編集・削除機能の実装
- 商品画像のアップロード対応
- 例外対応、ログインできない場合の画面表示
