package gr.redefine;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import gr.redefine.extras.Location;

public class FirebaseUtils {

    public static FirebaseDatabase getInstance(){
        return FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getRoot(){
        return getInstance().getReference("demo");
    }

    public static DatabaseReference addNewMessage(){
        return getRoot().push();
    }
}
