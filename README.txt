Java
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

・絵文字
■emoji4unicode ( EmojiSources.txt)
https://code.google.com/p/emoji4unicode/source/browse/trunk/data/emoji4unicode.xml
https://code.google.com/p/emoji4unicode/source/browse/trunk/generated/EmojiSources.txt
http://developers.linecorp.com/blog/?p=30
http://unicode.org/~scherer/emoji4unicode/snapshot/full.html
http://trialgoods.com/emoji/?career=i&page=all
http://qiita.com/aMasatoYui/items/0d4ff33df5f67e23e67c
http://www.fileformat.info/info/unicode/char/2600/index.htm

x-docomo-shift_jis-2007
x-kddi-shift_jis-2007
x-softbank-shift_jis-2007

■docomo-shift_jis-2007.ucm
https://code.google.com/p/android-source-browsing/source/browse/data/mappings/docomo-shift_jis-2007.ucm?repo=platform--external--icu4c&name=android-4.0.4_r1.1&r=ea1f1813c8b13a850b13f256aeb5152bb0942e81
String strstr = "\u2600";
String bstr = "";
try {
   byte[] strstrbyte = strstr.getBytes("UTF-8");
   for(byte b:strstrbyte)
      bstr = bstr + Integer.toHexString(b & 0xff);
   } catch (UnsupportedEncodingException e) {
   }
Log.d("myapp",bstr);
String strstr = "";
try {
   strstr = new String("\u2600".getBytes("x-docomo-shift_jis-2012"), "Shift-jis");
} catch (UnsupportedEncodingException e1) {
}
String bstr = "";
try {
   byte[] strstrbyte = strstr.getBytes("Shift-jis");
   for(byte b:strstrbyte)
      bstr = bstr + Integer.toHexString(b & 0xff);
   } catch (UnsupportedEncodingException e) {
   }
Log.d("myapp",bstr);

http://qiita.com/alzybaad/items/23201e45e081d1c8c86f
http://yuyakaido.hatenablog.com/entry/2014/02/16/230947
http://developer.android.com/about/versions/android-4.2.html#NestedFragments
http://qiita.com/nein37/items/32613e9acd9558566c5e
Android Open Source Project - Issue Tracker
	https://code.google.com/p/android/issues/list

Git
git reset --hard HEAD
git clean -fd src assets res
git reset --soft HEAD^
git reset --hard ORIG_HEAD
git cherry-pick -n 221984d3472296ac22c85efef154bXxxxxxxxxxxxxxxxx
git branch master2 origin/master2

git clone https://github.com/ttt4tsuo/Android_memo.git

git branch issue1
git checkout issue1
git push origin BN

git push origin master:test
ローカルのmasterブランチをリモートのtestブランチにpush


fetch, pull, merge, rebase

git pull
fetch + mergeコマンドと動作、結果は同じです。

http://qiita.com/Yuki_312/items/b9af7d09559054ecabb5

Dialog dialog = new Dialog(this);
// タイトル非表示
dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
// フルスクリーン
dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
dialog.setContentView(R.layout.alert_dialog);
// 背景を透明にする
dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
dialog.show();

ヒント: カスタム ダイアログが必要な場合は、Dialog API を使う代わりに、Activity をダイアログとして表示できます。 アクティビティを作成し、<activity> マニフェスト要素でそのテーマを Theme.Holo.Dialog に設定します。
<activity android:theme="@android:style/Theme.Holo.Dialog" >
これだけです。これで、アクティビティは全画面でなく、ダイアログ ウィンドウに表示されるようになります。

