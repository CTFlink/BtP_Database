package HtmlParser;

import Model.TableTennisPlayer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static DBConnection.ConnectionManager.insert;


public class TableParser {


    //Dette er parser metoden som tage imod en string med navnet json
    public static void parse(String json) throws IndexOutOfBoundsException {

        //Document er en Jsoup understøttet variabel type
        Document doc;

        //doc assignes til at være den parsede json string
        doc = Jsoup.parse(json);

        //Element table er jeg ikke helt sikker på hvad er...
        // men den assignes til at være den table fra bordtennisportalen som indeholder de data jeg skal bruge i databasen
        // med andre ord vælges den korrekte table ud fra dens unikke CSS style
        Element table = doc.select("table.RankingListGrid").first();

        //her fortæller vi programmet at der er en ny række for hver gang den støder på tr
        Elements rows = table.select("tr");

        List<TableTennisPlayer> players = new ArrayList<>();

        TableTennisPlayer player;

        rows.remove(0);
        rows.remove(rows.size()-1);

        for(Element row :rows)
        {
            Elements columns = row.select("td");

            player = new TableTennisPlayer();

            player.setPlacement(Integer.parseInt(columns.get(0).text()));
            player.setPlayerId(columns.get(2).text());
            player.setName(columns.get(3).text());
            player.setRating(Integer.parseInt(columns.get(4).text()));
            player.setDifference(Integer.parseInt(columns.get(5).text()));
            player.setMatches(Integer.parseInt(columns.get(6).text()));

            players.add(player);

            insert(player);
        }
    }

    // HTTP POST request
    public static void sendPost(int pages) throws Exception {

        String url = "http://bordtennisportalen.dk/SportsResults/Components/WebService1.asmx/GetRankingListPlayers";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "da,en-GB;q=0.9,en;q=0.8");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        String postMessage = "{\"callbackcontextkey\":\"BEE1EEF25DA7FE564FCE2E8EEBAF08FC151B271131A8032050630CB6190AD308AE732956E10D9340555B9559A9E902A5\",\"rankinglistagegroupid\":\"\",\"rankinglistid\":59,\"seasonid\":42018,\"rankinglistversiondate\":\"\",\"agegroupid\":\"\",\"classid\":\"\",\"gender\":\"\",\"clubid\":\"\",\"searchall\":false,\"regionid\":\"\",\"pointsfrom\":\"\",\"pointsto\":\"\",\"rankingfrom\":\"\",\"rankingto\":\"\",\"birthdatefromstring\":\"\",\"birthdatetostring\":\"\",\"agefrom\":\"\",\"ageto\":\"\",\"playerid\":\"\",\"param\":\"\",\"pageindex\":"+ pages +" ,\"sortfield\":0,\"getversions\":true,\"getplayer\":true}";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postMessage);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;

        StringBuilder jsonString = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            jsonString.append(inputLine);
        }
        in.close();

        JSONParser parser = new JSONParser();

        JSONObject json = (JSONObject) parser.parse(jsonString.toString());

        JSONObject jsonDObject = (JSONObject) json.get("d");

        parse(jsonDObject.get("Html").toString());
    }
}
