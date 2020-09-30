import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Images {
    private static final String HTML = "https://www.reddit.com/r/cats/new/";
    private final List<String> sourceURLs;

    public Images(){
        sourceURLs = new ArrayList<>();
        scanImages();

        int x =1;
        for(String url : sourceURLs){
            try {
                saveImage(url, x);
                x++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void scanImages(){
        try{
            Document document = Jsoup.connect(HTML).get();
            Elements images = document.getElementsByTag("img");
            for(Element image : images){
                String src = image.absUrl("src");
                //Only interested in actual pictures of cats
                if(src.contains("redditstatic") || src.contains("award_images") || src.contains("redditmedia")){
                    continue;
                }
                sourceURLs.add(src);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void saveImage(String imageURL, int dest) throws IOException {
        URL url = new URL(imageURL);
        String destinationFile = "./cats/"+Integer.toString(dest)+".jpg";
        //+imageURL.substring(imageURL.lastIndexOf("."))
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }
    public static void main(String[]args){
    }
}
