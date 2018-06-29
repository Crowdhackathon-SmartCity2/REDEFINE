package gr.redefine.extras;

import java.util.Objects;

public class Location {
    private double lat;
    private double lon;

    public Location(String location) {
        String[] parts = location.split(",");
        if(parts.length!=2){
            throw new IllegalArgumentException();
        }
        this.setLongitude(Double.valueOf(parts[0]));
        this.setLatitude(Double.valueOf(parts[1]));
    }

    private Location() {
    }

    public Location(Location location){
        this(location.getLatitude(),location.getLongitude());
    }

    public Location(double longitude, double latitude){
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }
    public double getLatitude() {
        return lat;
    }

    public void setLatitude(double lat) {
        this.lat = lat;
    }

    public double getLongitude() {
        return lon;
    }

    public void setLongitude(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return getLongitude()+","+getLatitude();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.lat, lat) == 0 &&
                Double.compare(location.lon, lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}
