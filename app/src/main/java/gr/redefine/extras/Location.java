package gr.redefine.extras;

public class Location {
    private double lat;
    private double lon;

//    public Location(String location) {
//        String[] parts = location.split(",");
//        if(parts.length!=2){
//            throw new IllegalArgumentException();
//        }
//        this.setLatitude(Double.valueOf(parts[1]));
//        this.setLongitude(Double.valueOf(parts[0]));
//    }
    public Location(GeoHash geoHash){
        this(geoHash.getCenter());
    }

    public Location() {
    }

    public Location(Location location){
        this(location.getLatitude(),location.getLongitude());
    }

    public Location(double latitude, double longitude){
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
        return GeoHash.fromLocation(this).toString();
    }
}
