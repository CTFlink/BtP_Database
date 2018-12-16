import HtmlParser.TableParser;

public class Main {

    public static void main(String[] args) {
        System.out.println("JDBC database eksempel");

        try {
            for (int i = 0; i < 9; i++) {
                TableParser.sendPost(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}