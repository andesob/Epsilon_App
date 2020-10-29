package no.ntnu.epsilon_app.ui.news;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class testData {
    ArrayList<News> newsList;

    public testData(){
        newsList = new ArrayList<>();
        makeDummies();
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    public void makeDummies(){
        News news1 = new News();
        news1.setTitle("Lorem Ipsum?");
        news1.setContents("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean malesuada feugiat sapien, ut rhoncus nisl fringilla vel. Quisque tincidunt placerat sapien, quis iaculis risus fringilla sit amet. Donec vel ex a tortor finibus vehicula in eu lorem. Nullam dapibus turpis ac mauris efficitur, vitae lacinia est tristique. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Praesent lacus augue, lacinia ut ullamcorper eu, placerat eu sapien. Aenean consequat dui nisi, auctor aliquet libero porta eget. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nam at tempus purus. Pellentesque laoreet gravida mattis. Maecenas iaculis a mi et elementum. Morbi lacus arcu, lacinia sit amet purus a, venenatis pellentesque eros. Maecenas dignissim gravida ex, eu finibus ex consectetur eget. Maecenas sollicitudin est nec justo laoreet, ut pharetra nulla auctor. Maecenas malesuada magna sit amet lorem interdum, rutrum varius tortor aliquam. Pellentesque lobortis porta enim non tempor. Nunc efficitur gravida arcu, sed mattis ante pellentesque sit amet. Vivamus mi arcu, dignissim eu suscipit vestibulum, luctus eget diam. Sed a libero feugiat, aliquet risus nec, mattis magna.");

        News news2 = new News();
        news2.setTitle("Hvor mye koster et medlemskap i Epsilon?");
        news2.setContents("50kr Maecenas dignissim gravida ex, eu finibus ex consectetur eget. Maecenas sollicitudin est nec justo laoreet, ut pharetra nulla auctor. Maecenas malesuada magna sit amet lorem interdum, rutrum varius tortor aliquam. Pellentesque lobortis porta enim non tempor. Nunc efficitur gravida arcu, sed mattis ante pellentesque sit amet. Vivamus mi arcu, dignissim eu suscipit vestibulum, luctus eget diam. Sed a libero feugiat, aliquet risus nec, mattis magna.");

        News news3 = new News();
        news3.setTitle("Hvor søt er Eskil?");
        news3.setContents("Sprenger skalaen");

        News news4 = new News();
        news4.setTitle("Hvem er lederen i Epsilon?");
        news4.setContents("Sander Hurlen");

        newsList.add(news1);
        newsList.add(news2);
        newsList.add(news3);
        newsList.add(news4);
    }
}
