import org.jsoup.Jsoup;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Coronavirus {
    private static final String HTML = "https://www.quebec.ca/en/health/health-issues/a-z/2019-coronavirus/situation-coronavirus-in-quebec/";
    private String information;

    public Coronavirus(){
        try {
            scan();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void scan() throws IOException {
        Document doc = Jsoup.connect(HTML).get();
        Elements text = doc.getElementsByClass("ce-bodytext");  //Get all the paragraphs
        information = text.get(1).text(); //Get paragraph with daily updates on Coronavirus number
    }

    public String getInformation(){
        StringBuilder info= new StringBuilder("Coronavirus Update brought to you by: Gouvernement du Québec \n");
        String[] tokens = information.split("[.]"); //parse the entire text string
        for(String s: tokens){
            info.append("• ").append(s).append(".\n");
        }
        return info.toString();
    }
    public static void main(String[]args){
    }
}
