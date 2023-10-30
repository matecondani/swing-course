package src.testDB;

import java.sql.SQLException;
import src.model.AgeCategory;
import src.model.Database;
import src.model.EmploymentCategory;
import src.model.Gender;
import src.model.Person;

public class TestDatabase {

    public static void main(String[] args) {
        System.out.println("Running database");
        Database db = new Database();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.addPerson(new Person("joe", "builder", AgeCategory.ADULT, EmploymentCategory.employed, "777", true, Gender.MALE));
        db.addPerson(new Person("sue", "artist", AgeCategory.SENIOR, EmploymentCategory.selfEmployed, null, false, Gender.FEMALE));

        try {
            db.save();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            db.load();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.disconnect();
    }
}
