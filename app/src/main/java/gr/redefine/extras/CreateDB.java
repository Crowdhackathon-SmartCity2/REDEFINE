package gr.redefine.extras;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gr.redefine.FirebaseUtils;
import gr.redefine.Message;

public class CreateDB {

    private static final String PHARMA_ICON = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQvAX5WWZ7mHZhJLhdrgOqFqW5vSzb2XOR1dgObudYsMBJ_6wbX";
    private static final String HOSPITAL_ICON = "https://www.qubo.gr/uploads/companies/88183/agios_dhmhtrios_-_nomarxiako_geniko_nosokomeio_thessalonikhs_slider_gold.jpg";
    private static final String POLICE_ICON = "https://upload.wikimedia.org/wikipedia/el/3/3a/Elas.png";
    private static final String FIRE_ICON = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/CoA_Hellenic_Fire_Service.svg/1200px-CoA_Hellenic_Fire_Service.svg.png";
    private static final String SEA_ICON = "https://upload.wikimedia.org/wikipedia/el/9/95/Hellenic_Coast_Guard_logo.png";
    private static final String APERGIA_ICON = "https://is4-ssl.mzstatic.com/image/thumb/Purple118/v4/1f/30/6d/1f306d8f-769d-31de-6181-ac7d423d236a/mzl.eqxwxtgp.jpg/246x0w.jpg";
//
    public static void initDb() {
        List<Message> pharmacies = new ArrayList<>(
                Arrays.asList(
                        new Message(Message.TYPES.HEALTH,"ΣΥΣΤΕΓΑΣΜΕΝΑ ΦΑΡΜΑΚΕΙΑ ΣΤΕΛΛΑΣ ΘΕΟΛΟΓΟΥ ΜΑΡΙΑΣ ΘΕΟΛΟΓΟΥ", new Location( 23.7068, 37.9387), PHARMA_ICON),
                        new Message(Message.TYPES.HEALTH,"ΛΟΥΚΙΣΣΑ ΣΤΥΛΙΑΝΗ", new Location(23.7116, 37.9222), PHARMA_ICON),
                        new Message(Message.TYPES.HEALTH,"ΠΕΛΩΡΙΑΔΗΣ ΣΩΤΗΡΙΟΣ", new Location(23.6937, 37.9282), PHARMA_ICON),
                        new Message(Message.TYPES.HEALTH,"ΣΤΑΣΙΝΟΠΟΥΛΟΣ ΔΗΜΗΤΡΙΟΣ", new Location(23.6973,  37.9483), PHARMA_ICON),
                        new Message(Message.TYPES.HEALTH,"ΩΝΑΣΕΙΟ ΚΑΡΔΙΟΧΕΙΡΟΥΡΓΙΚΟ ΚΕΝΤΡΟ", new Location(23.6952457,37.9414325), HOSPITAL_ICON),
                        new Message(Message.TYPES.PUBLIC_SAFETY,"ΑΤ ΝΕΑΣ ΣΜΥΡΝΗΣ", new Location(23.705437, 37.946355), POLICE_ICON),
                        new Message(Message.TYPES.PUBLIC_SAFETY,"ΠΥΡΟΣΒΕΣΤΙΚΉ ΥΠΗΡΕΣΙΑ ΠΕΙΡΑΙΑ", new Location(23.651241,37.949967 ), FIRE_ICON),
                        new Message(Message.TYPES.PUBLIC_SAFETY,"ΛΙΜΕΝΙΚΟ ΣΩΜΑ ΠΕΙΡΑΙΑ", new Location( 23.648630,37.933897), SEA_ICON),
                        new Message(Message.TYPES.BUS,"217 -> 3 Λεπτά", new Location( 23.6928533,37.9374729), APERGIA_ICON),
                        new Message(Message.TYPES.BUS,"A1  -> 7 Λεπτά", new Location( 23.6928533,37.9374729), APERGIA_ICON),
                        new Message(Message.TYPES.BUS,"130 -> 12 Λεπτά", new Location( 23.6928533,37.9374729), APERGIA_ICON)
                )
        );

        pharmacies.forEach(p->{
            DatabaseReference db = FirebaseUtils.addNewMessage();
            db.setValue(p);
        });

    }

}
