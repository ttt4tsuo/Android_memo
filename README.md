■Java
正規表現
String str = "Orange is 100yen, Banana is 180yen.";
String regex = "yen";
Pattern p = Pattern.compile(regex);
Matcher m = p.matcher(str);
String result = m.replaceAll("YEN");

■外部設計
■内部設計
内部設計
http://www.h6.dion.ne.jp/~akn/pm/SystemDevelopment/InternalDesign.html
ソフトウェア個人開発プロセス手順書
http://www.sage-p.com/process/process.htm
ビジネスルールの概要
https://www.ogis-ri.co.jp/otc/swec/process/am-res/am/artifacts/businessRule.html

シーケンス図、ステートマシン；astah

■URL
https://sites.google.com/site/devcollaboration/codesearch


■テスト
mockito
http://y-anz-m.blogspot.jp/search/label/mockito
http://stackoverflow.com/questions/19599934/issues-with-mocking-httpurlconnection-using-mockito
mockito サンプル
http://atec.googlecode.com/svn/atmarkit/usemock/trunk/UseMockExample/src/org/android_tec/atmarkit/usemockexample/models/FxRateLoader.java
https://gist.github.com/nowsprinting/4179494
リフレクション
http://itinfo.main.jp/tan/?p=116

■DefaultHttpClient
既定でHttpGetに付くヘッダー
	GET./.HTTP/1.1
	Host:.www.google.co.jp
	Connection:.Keep-Alive
	User-Agent:.Apache-HttpClient/UNAVAILABLE.(java.1.4)

■OpenSSL
openssl s_server -CAfile chain.crt -cert server.crt -key server.key -www
http://qiita.com/ngyuki/items/d0e7f3c162c1f8446298

▪viewを一部消去
https://akira-watson.com/android/inflate.html
http://ichitcltk.hustle.ne.jp/gudon2/index.php?pageType=file&id=Android059_ViewTree
http://andbrissyu.blogspot.jp/2012/12/blog-post.html

