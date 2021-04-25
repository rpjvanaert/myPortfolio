package logo.philist.portfolioapp1.Models;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import logo.philist.portfolioapp1.Models.ArticleData.Article;
import logo.philist.portfolioapp1.Models.ArticleData.ArticleItem;
import logo.philist.portfolioapp1.Models.ViewData.ViewData;

public class ResourceManager {

    public static final String TAG = ResourceManager.class.getSimpleName();

    public static final String PORTFOLIO_INCL_FILE = "portfolio_incl.json";
    public static final String PROFILE_PICTURE_FILE = "profile_picture1.jpg";

    public static Drawable getAssetsImageDrawable(String fileLocation, Context context){
        Drawable drawableImage = null;

        try {
            InputStream inputStream = context.getAssets().open(fileLocation);
            drawableImage = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "getAssetsImageDrawable: " + fileLocation);
        return drawableImage;
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
            InputStream inputStream = context.getAssets().open(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            inputStream.close();

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
                article.addViewData(
                        new ViewData(
                                ViewData.getStringViewType(contentObject.getString("type")),
                                contentObject.getString("content")
                        )
                );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return article;
    }

    public static File getFile(Context context, String filePath){
        Log.i(TAG, "getFile: " + filePath);

        File cacheFile = new File(context.getCacheDir(), filePath);
        try {
            InputStream inputStream = context.getAssets().open(filePath);
            FileOutputStream outputStream = new FileOutputStream(cacheFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacheFile;

        /*
        AssetManager assetManager = context.getAssets();
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(filePath);
            return assetFileDescriptor.getParcelFileDescriptor();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
         */

        /*
        File resultFile = new File(filePath);
        Log.i(TAG, "getFile: " + resultFile.getName());
        try {
            InputStream in = context.getAssets().open(filePath);
            OutputStream out = new FileOutputStream(resultFile);
            int length;
            byte[] bytes = new byte[1024];
            while ((length = in.read(bytes)) != -1) {
                out.write(bytes, 0 , length);
            }

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultFile;
         */
    }
}
