package logo.philist.portfolioapp1.Models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AssetManager {

    public static final String PORTFOLIO_INCL_FILE = "portfolio_incl.json";
    public static final String PROFILE_PICTURE_FILE = "profile_picture1.jpg";

    public static Drawable getProfilePicture(Context context){
        Drawable profilePicture = null;

        try {
            InputStream inputStream = context.getAssets().open(PROFILE_PICTURE_FILE);
            profilePicture = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profilePicture;
    }

    private static List<ArticleItem> getArticleItems(String jsonString) {
        List<ArticleItem> articleItems = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);



            JSONArray articlesArray = json.getJSONArray("articles");

            for (int indexArticle = 0; indexArticle < articlesArray.length(); ++indexArticle) {
                JSONObject articleJson = articlesArray.getJSONObject(indexArticle);
                articleItems.add(new ArticleItem(
                        articleJson.getString("title"),
                        articleJson.getString("descr"),
                        articleJson.getString("preview_image"),
                        articleJson.getString("article_file")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return articleItems;
    }

    public static List<ArticleItem> getArticleItems(Context context) {
        return getArticleItems(readJson(context, PORTFOLIO_INCL_FILE));
    }

    private static String readJson(Context context, String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(context.getFilesDir(), filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return stringBuilder.toString();
    }

    public static Article getArticle(Context context, String articleFile){

        Article article = null;

        try {
            JSONObject articleJson = new JSONObject(readJson(context, articleFile));

            article = new Article(articleJson.getString("title"));

            JSONArray contentArray = articleJson.getJSONArray("content");

            for (int indexContent = 0; indexContent < contentArray.length(); ++indexContent) {
                JSONObject contentObject = contentArray.getJSONObject(indexContent);
                article.addArticleContent(
                        new ArticleContent(
                                contentObject.getString("type"),
                                contentObject.getString("content"),
                                contentObject.getString("style")
                        )
                );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return article;
    }
}
