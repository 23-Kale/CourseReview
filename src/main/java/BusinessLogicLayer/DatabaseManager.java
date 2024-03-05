package BusinessLogicLayer;

import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private Connection connection;

    public void connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                throw new IllegalStateException("Connection already established");
            } else {
                connection = DriverManager.getConnection("jdbc:sqlite:Reviews.sqlite3");
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Connection already established");
        }
    }

    private void checkConnection() {
        try {
            if (connection == null || connection.isClosed()){
                throw new IllegalStateException("Connection not established yet");
            }
        } catch (SQLException e){
            throw new IllegalStateException("Connection not established yet");
        }

    }

    public void createTables() {
        checkConnection();

        try {
            Statement statement = connection.createStatement();

            String createStudentsTable = "CREATE TABLE IF NOT EXISTS Students(" +
                    "ID INTEGER NOT NULL, " +
                    "Name VARCHAR(50) NOT NULL, " +
                    "Password VARCHAR(50) NOT NULL, " +
                    "PRIMARY KEY (ID))";
            statement.executeUpdate(createStudentsTable);
        } catch (SQLException e) {
            throw new IllegalStateException("Students already exists!");
        }
        try {
            Statement statement = connection.createStatement();
            String createCoursesTable = "CREATE TABLE IF NOT EXISTS Courses(" +
                    "ID INTEGER NOT NULL, " +
                    "Department VARCHAR(4) NOT NULL, " +
                    "Catalog_Number INT(4) NOT NULL, " +
                    "PRIMARY KEY (ID))";
            statement.executeUpdate(createCoursesTable);
        } catch (SQLException e){
            throw new IllegalStateException("Courses already exists!");
        }
        try {
            Statement statement = connection.createStatement();
            String createReviewsTable = "CREATE TABLE IF NOT EXISTS Reviews(" +
                    "ID INTEGER NOT NULL, " +
                    "Student_ID INT NOT NULL, " +
                    "Course_ID INT NOT NULL, " +
                    "Message VARCHAR(4096) NOT NULL," +
                    "Rating INT(1) NOT NULL," +
                    "PRIMARY KEY (ID)," +
                    "FOREIGN KEY (Student_ID) REFERENCES Students(ID) ON DELETE CASCADE," +
                    "FOREIGN KEY (Course_ID) REFERENCES Courses(ID) ON DELETE CASCADE)";
            statement.executeUpdate(createReviewsTable);

            statement.close();
        } catch (SQLException e){
            throw new IllegalStateException("Reviews already exists!");
        }

    }

    public void deleteTables() {
        checkConnection();
        try {
            Statement statement = connection.createStatement();

            String deleteStudents = "DROP TABLE Students";
            statement.executeUpdate(deleteStudents);
            String deleteCourses = "DROP TABLE Courses";
            statement.executeUpdate(deleteCourses);
            String deleteReviews = "DROP TABLE Reviews";
            statement.executeUpdate(deleteReviews);

            statement.close();
        } catch (SQLException e) {
            throw new IllegalStateException("No tables to delete");
        }
    }

    public void addStudent(Student student) {
        checkConnection();
        try {
            if(!doesUserExist(student.getName())) {
                Statement statement = connection.createStatement();

                String addStudent = String.format("INSERT INTO Students (Name, Password)" +
                        "VALUES('%s', '%s')", student.getName(), student.getPassword());
                statement.executeUpdate(addStudent);

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCourse(Course course) {
        checkConnection();
        try {
            if(!checkCourseExists(course)) {
                Statement statement = connection.createStatement();

                String addCourse = String.format("INSERT INTO Courses (Department, Catalog_Number)" +
                        "VALUES('%s', %d)", course.getDepartment(), course.getCatalogNumber());
                statement.executeUpdate(addCourse);

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addReview(Review review) {
        checkConnection();
        try {
            Statement statement = connection.createStatement();

            int studentID = getStudentID(review.getStudent());

            int courseID = getCourseID(review.getCourse());
            if (courseID == -1) {
                addCourse(review.getCourse());
                courseID = getCourseID(review.getCourse());
            }

            String addReview = String.format("INSERT INTO " +
                            "Reviews (Student_ID, Course_ID, Message, Rating)" +
                            "VALUES (%d, %d, '%s', %d)",
                            studentID, courseID, review.getMessage().replace("'", "''"), review.getRating());
            statement.executeUpdate(addReview);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isStudentRegistered(Student student) {
        checkConnection();
        boolean studentIsRegistered = false;
        try {
            Statement statement = connection.createStatement();

            String getStudentID = String.format("SELECT * FROM Students " +
                    "WHERE Name = '%s' AND Password = '%s'", student.getName(), student.getPassword());
            ResultSet gottenStudents = statement.executeQuery(getStudentID);
            studentIsRegistered = gottenStudents.next();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentIsRegistered;
    }

    public boolean doesUserExist(String username) {
        checkConnection();
        boolean userExists = false;
        try {
            Statement statement = connection.createStatement();

            String getStudentID = String.format("SELECT * FROM Students " +
                    "WHERE Name = '%s' ", username);
            ResultSet gottenStudents = statement.executeQuery(getStudentID);
            userExists = gottenStudents.next();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userExists;
    }

    public boolean checkCourseExists(Course course){
        checkConnection();
        boolean courseExists = false;
        try {
            Statement statement = connection.createStatement();

            int courseID = getCourseID(course);
            String checkExists = String.format("SELECT * FROM Reviews WHERE Course_ID = %d", courseID);
            ResultSet exists = statement.executeQuery(checkExists);

            courseExists = exists.next();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseExists;
    }

    public boolean studentHasReviewedCourse(Student student, Course course) {
        checkConnection();

        boolean hasReviewed = false;
        try {
            Statement statement = connection.createStatement();

            int studentID = getStudentID(student);
            int courseID = getCourseID(course);
            String getReview = String.format("SELECT * FROM Reviews " +
                    "WHERE Student_ID = %d AND Course_ID = %d", studentID, courseID);
            ResultSet gottenReview = statement.executeQuery(getReview);
            hasReviewed = gottenReview.next();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasReviewed;
    }

    public List<Review> seeReviewsForCourse(Course course) {
        checkConnection();

        List<Review> reviewList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            int courseID = getCourseID(course);
            String getReviewsForCourse = String.format("SELECT * FROM Reviews WHERE Course_ID = %d", courseID);
            ResultSet gottenReviews = statement.executeQuery(getReviewsForCourse);

            while(gottenReviews.next()) {
                int studentID = gottenReviews.getInt("Student_ID");
                Student student = getStudentByID(studentID);
                String message = gottenReviews.getString("Message");
                int rating = gottenReviews.getInt("Rating");
                Review review = new Review(student, course, message, rating);
                reviewList.add(review);
            }


            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    public Student getStudentByID(int studentID) {
        checkConnection();

        Student student;
        try {
            Statement statement = connection.createStatement();

            String getStudent = String.format("SELECT * FROM Students WHERE ID = %d", studentID);
            ResultSet gottenStudent = statement.executeQuery(getStudent);
            student = new Student(gottenStudent.getString("Name"), gottenStudent.getString("Password"));

            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
        return student;
    }

    private int getStudentID(Student student) {
        int studentID = -1;
        try {
            Statement statement = connection.createStatement();

            String getStudentID = String.format("SELECT * FROM Students " +
                    "WHERE Name = '%s' AND Password = '%s'", student.getName(), student.getPassword());
            ResultSet gottenStudents = statement.executeQuery(getStudentID);
            if(!gottenStudents.next()) throw new IllegalArgumentException("No Student with given Name or Password found");
            studentID = gottenStudents.getInt("ID");

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentID;
    }

    private int getCourseID(Course course) {
        int courseID;
        try {
            Statement statement = connection.createStatement();

            String getCourseID = String.format("SELECT * FROM Courses " +
                            "WHERE Department = '%s' AND Catalog_Number = %d",
                    course.getDepartment(), course.getCatalogNumber());
            ResultSet gottenCourses = statement.executeQuery(getCourseID);
            courseID = gottenCourses.getInt("ID");

            statement.close();
        } catch (SQLException e) {
            return -1;
        }
        return courseID;
    }

    public void disconnect() {
        checkConnection();
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

