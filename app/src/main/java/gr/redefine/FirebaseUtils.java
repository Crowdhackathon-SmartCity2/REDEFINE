package gr.redefine;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gr.redefine.extras.Location;

public class FirebaseUtils {

    public static FirebaseDatabase getInstance(){
        return FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getUser(String user) {
        return getInstance().getReference("demo/"+user);
    }

    public static DatabaseReference getNewPostForUser(String user){
        return getUser(user).push();
    }

    public static DatabaseReference getLocation(Location location) {
        return getInstance().getReference("demo/"+location.toString());
    }

    public static DatabaseReference getNewPostOnLocation(Location location){
        return getLocation(location).push();
    }
}
