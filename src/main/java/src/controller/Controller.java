package src.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import src.gui.FormEvent;
import src.model.AgeCategory;
import src.model.Database;
import src.model.EmploymentCategory;
import src.model.Gender;
import src.model.Person;

public class Controller {
    Database db = new Database();

    public List<Person> getPeople(){
        return db.getPeople();
    }

    public void addPerson(FormEvent ev) {
        String name = ev.getName();
        String occupation = ev.getOccupation();
        int ageCatId = ev.getAgeCategory();
        String empCat = ev.getEmpCat();
        boolean usCitizen = ev.isUsCitizen();
        String taxId = ev.getTaxId();
        String gender = ev.getGender();

        AgeCategory ageCategory = switch (ageCatId) {
            case 0 -> AgeCategory.CHILD;
            case 1 -> AgeCategory.ADULT;
            default -> AgeCategory.SENIOR;
        };

        EmploymentCategory employmentCategory = switch (empCat) {
            case "employed" -> EmploymentCategory.employed;
            case "unemployed" -> EmploymentCategory.unemployed;
            case "self-employed" -> EmploymentCategory.selfEmployed;
            default -> EmploymentCategory.other;
        };

        Gender genderCat = "male".equals(gender) ? Gender.MALE : Gender.FEMALE;

        Person person = new Person(name, occupation, ageCategory, employmentCategory, taxId, usCitizen, genderCat);

        db.addPerson(person);
    }

    public void saveToFile(File file) throws IOException {
        db.saveToFile(file);
    }

    public void loadFromFile(File file) throws IOException {
        db.loadFromFile(file);
    }
}
