import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Weather {
    private static final String HTML = "https://www.accuweather.com/en/ca/montreal/h3a/weather-forecast/56186";
    private final List<String> numbers;
    private final List<String> descriptions;

    public Weather(){
        numbers = new ArrayList<>();
        descriptions = new ArrayList<>();
        try{
            scan();
        }catch (IOException ex){
            System.out.println("JSoup could not connect to the website!");
        }
    }

    public void scan() throws IOException{
        Document doc = Jsoup.connect(HTML).get();
        Elements temperatures = doc.getElementsByClass("temp");
        for(Element e : temperatures){
            numbers.add(e.text());
        }

        Elements texts = doc.getElementsByClass("phrase");
        for(Element e : texts){
            descriptions.add(e.text());
        }
    }

    public String getInformation(){
        if(numbers.size()>=4){
            String current = "Current weather : "+numbers.get(0)+" : "+descriptions.get(0);
            String day = "\nToday's weather: "+numbers.get(1)+" : "+descriptions.get(1);
            String night = "\nTonight's weather: "+numbers.get(2)+" : "+descriptions.get(2);
            String tomorrow = "\nTomorrow's weather: "+numbers.get(3)+" : "+descriptions.get(3);
            return "Weather brought to you by AccuWeather\n" + current + day + night + tomorrow +"\n";
        }
        return "No weather available. \n";
    }
    public static void main(String[]args){
    }
}
