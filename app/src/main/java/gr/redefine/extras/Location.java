package gr.redefine.extras;

public class Location {
    private double lat;
    private double lon;

    public Location(String location) {
        String[] parts = location.split(",");
        if(parts.length!=2){
            throw new IllegalArgumentException();
        }
        this.setLat(Double.valueOf(parts[1]));
        this.setLon(Double.valueOf(parts[0]));
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return this.getLon()+","+this.getLat();
    }
}
