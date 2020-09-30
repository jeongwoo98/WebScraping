import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AmazonDeals {
    //Amazon deals --> electronics --> Today's deals
    private static final String HTML = "https://www.amazon.ca/s?k=amazon+electronics&rh=p_n_specials_match%3A21224829011&s=date-desc-rank&dc&gclid=CjwKCAjw74b7BRA_EiwAF8yHFAs4PRhxn4Lq-Q9utEZrNRQvAutAFoLEs0tAMUlWHHH07o3M7HxHmBoCv64QAvD_BwE&hvadid=208458042154&hvdev=c&hvlocphy=9000412&hvnetw=g&hvqmt=e&hvrand=4594165433530598255&hvtargid=kwd-301313597215&hydadcr=1505_9454478&qid=1600285386&rnid=21224828011&tag=googcana-20&ref=sr_st_date-desc-rank";
    private final Map<String, String> items;

    public AmazonDeals(){
        items = new HashMap<>();
        try{
            scan();
            //getInformation();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void scan() throws IOException{
        Document doc = Jsoup.connect(HTML).get();
        Elements products = doc.getElementsByClass("a-link-normal a-text-normal");
        for(int i=1; i<=10; i++) {
            Element product = products.get(i);
            String url = product.attributes().get("href");
            items.put(product.text(), url);
        }
    }

    public String getInformation() throws IOException {
        StringBuilder info = new StringBuilder("Amazon Discounts Today: \n");
        for(Map.Entry<String,String> item: items.entrySet()){
            String itemHTML = "https://www.amazon.ca"+item.getValue();
            Document itemPage = Jsoup.connect(itemHTML).get();
            Elements oldPrice = itemPage.getElementsByClass("priceBlockStrikePriceString a-text-strike");
            Elements newPrice = itemPage.getElementsByClass("a-size-medium a-color-price priceBlockDealPriceString");
            Elements discount = itemPage.getElementsByClass("a-span12 a-color-price a-size-base priceBlockSavingsString");
            Elements rating = itemPage.getElementsByClass("a-icon-alt");
            Element nbReviews = itemPage.getElementById("acrCustomerReviewText");

            //Some items on the discount page are not actually discounts...
            if(oldPrice.size()==0 || newPrice.size()==0 || discount.size()==0){
                continue;
            }

            info.append("•").append(item.getKey()).append("\n");
            String priceDropDescription = oldPrice.get(0).text()+"-->"+newPrice.get(0).text()+": save "+discount.get(0).text();
            info.append(priceDropDescription).append("\n");
            String numberOfReviews = " ("+nbReviews.text()+")";
            info.append(rating.get(0).text()).append(numberOfReviews).append("\n");
            info.append("link: ").append(itemHTML).append("\n\n");
        }
        return info.toString();
    }
    public static void main(String[]args){
    }
}
