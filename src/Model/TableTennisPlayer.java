package Model;

public class TableTennisPlayer {
    int placement;
    String playerId;
    String name;
    int rating;
    int matches;
    int difference;

    public TableTennisPlayer(int placement, String playerId, String name, int rating, int matches, int difference) {
        this.placement = placement;
        this.playerId = playerId;
        this.name = name;
        this.rating = rating;
        this.matches = matches;
        this.difference = difference;
    }

    public TableTennisPlayer() {
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }
}
