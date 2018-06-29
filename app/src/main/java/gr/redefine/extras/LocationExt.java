package gr.redefine.extras;


final class LocationExt {

    private static final String PROVIDER = "geohash";

    static Location newLocation(double latitude, double longitude) {
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }
}