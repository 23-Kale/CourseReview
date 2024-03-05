package BusinessLogicLayer;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect();
        databaseManager.createTables();
        databaseManager.createTables();
        Student tony = new Student("Tony Chang", "milk");
        Student jake = new Student("Jake Li", "juice");
        Student jofu = new Student("Joshua Fu", "coffee");
        databaseManager.addStudent(tony);
//        System.out.println(databaseManager.isStudentRegistered(tony));
        databaseManager.addStudent(jake);
//        System.out.println(databaseManager.isStudentRegistered(jofu));
        databaseManager.addStudent(jofu);
        Course sde = new Course("CS", 3140);
        Course dsa = new Course("CS", 3100);
//        databaseManager.addCourse(sde);
//        databaseManager.addCourse(dsa);
        Review tonyReview = new Review(tony, sde, "Loved it", 5);
        Review jakeReview = new Review(jake, sde, "Hated it", 3);
        Review jofuReview = new Review(jofu, sde, "Kinda mid", 3);
        Review tonyReview2 = new Review(tony, dsa, "What even is this class", 2);
        Review jakeReview2 = new Review(jake, dsa, "I'm doing great", 4);


        System.out.println(databaseManager.studentHasReviewedCourse(jake, dsa));
        databaseManager.addReview(tonyReview);
        databaseManager.addReview(jakeReview);
        databaseManager.addReview(jofuReview);
        databaseManager.addReview(tonyReview2);
        databaseManager.addReview(jakeReview2);
        System.out.println(databaseManager.studentHasReviewedCourse(jake, dsa));
        List<Review> reviewList = databaseManager.seeReviewsForCourse(sde);

        double sum = 0;
        int count = 0;
        for(Review review : reviewList) {
            System.out.println(review.getMessage());
            sum += review.getRating();
            count++;
        }
        System.out.println("Course Average " + sum/count + "/5");

        databaseManager.disconnect();
    }
}
