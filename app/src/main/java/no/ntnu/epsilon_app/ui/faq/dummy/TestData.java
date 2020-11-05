package no.ntnu.epsilon_app.ui.faq.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestData {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListItems = new HashMap<String, List<String>>();

        List<String> question1 = new ArrayList<String>();
        question1.add("Sander Hurlen");
        expandableListItems.put("Hvem er lederen i Epsilon?", question1);


        List<String> question2 = new ArrayList<String>();
        question2.add("50kr");
        expandableListItems.put("Hvor mye koster et medlemskap i Epsilon?", question2);

        List<String> question3 = new ArrayList<String>();
        question3.add("Sprenger skalaen");
        expandableListItems.put("Hvor søt er Eskil?", question3);

        List<String> question4 = new ArrayList<String>();
        question4.add("Kongsberg IT ønsker å bli bedre kjent med studentene ved NTNU . Derfor inviterer de til en liten bedriftpresentasjon og gode samtaler med de ansatte fra Kongsberg IT. Rundt bordene blir det også servert noe godt å spise og drikke.\n" +
                "Obs! Påmeldingsfrist: 22.03.2020\n" +
                "Påmeldingsskjema:\n" +
                "https://docs.google.com/.../1FAIpQLSfRlHe9StR2ca.../viewform\n" +
                "Program:\n" +
                "16.00 - Presentasjon av Kongsberg IT\n" +
                "17.00 - Uformell prat rundt bordet\n" +
                "Baren på banken holdes åpen, så det er bare å ta turen innom!\n" +
                "Velkommen! Se mindre");
                expandableListItems.put("Lorem Ipsum?", question4);


        return expandableListItems;
    }
}