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
        question4.add("Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a " +
                "piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a " +
                "Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, " +
                "from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable " +
                "source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good " +
                "and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the " +
                "Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.");
                expandableListItems.put("Lorem Ipsum?", question4);


        return expandableListItems;
    }
}