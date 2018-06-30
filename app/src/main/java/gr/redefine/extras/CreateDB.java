package gr.redefine.extras;

import com.google.firebase.database.DatabaseReference;

import gr.redefine.FirebaseUtils;
import gr.redefine.Message;

public class CreateDB {


    public static void initDb() {
        //Canal
        DatabaseReference db = FirebaseUtils.addNewMessage();
        Message canal = new Message("Canal Café", "snfcc", Message.TYPES.ORG, new Location(23.695441, 37.941714));
        canal.setImgURL("https://www.snfcc.org/media/2232051/canal-cafe_500x334.jpg");
        db.setValue(canal);

        db = FirebaseUtils.addNewMessage();
        //Laburinthos
        Message labyrinth = new Message("Labyrinth", "snfcc", Message.TYPES.ORG, new Location( 23.691231,37.941475));
        labyrinth.setImgURL("https://www.snfcc.org/imagegen.ashx?image=/media/1948921/capture-8_responsive.jpg&width=1440&constrain=false&height=960&pad=true&bgcolor=151515");
        db.setValue(labyrinth);

        db = FirebaseUtils.addNewMessage();
        //Floisvos
        Message floisvos = new Message("Marina Floisvou", "snfcc", Message.TYPES.ORG, new Location(23.685126,37.930544));
        floisvos.setImgURL("https://image.ibb.co/mTQquy/MARINA_FLOISVOU600_662x414.jpg");
        db.setValue(floisvos);

        db = FirebaseUtils.addNewMessage();
        //Sef
        Message sef = new Message("Peace and Friendship Stadium", "snfcc", Message.TYPES.ORG, new Location(23.667586,37.941892 ));
        sef.setImgURL("https://image.ibb.co/cZJX7J/sef.jpg");
        db.setValue(sef);

    }

}
