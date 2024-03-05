package BusinessLogicLayer;

public class Review {
    Student student;
    Course course;
    String message;
    int rating;

    public Review(Student student, Course course, String message, int rating) {
        this.student = student;
        this.course = course;
        this.message = message;
        this.rating = rating;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getMessage() {
        return message;
    }

    public int getRating() {
        return rating;
    }
}
