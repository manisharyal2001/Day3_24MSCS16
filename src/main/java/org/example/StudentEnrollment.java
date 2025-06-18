package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.util.Arrays;

public class StudentEnrollment {
    public static void main(String[] args) {
        try (MongoClient client = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = client.getDatabase("Student_Details");
            MongoCollection<Document> students = db.getCollection("Students");
            MongoCollection<Document> courses = db.getCollection("Courses");
            MongoCollection<Document> enrollments = db.getCollection("Enrollments");


            students.drop(); courses.drop(); enrollments.drop();


            Document s1 = new Document("name", "Yashwant CR").append("email", "cryashwant2025@gmail.com").append("age", 25);
            Document s2 = new Document("name", "Swaraswatri R").append("email", "swaraswatiedu@gmail.com").append("age", 21);
            students.insertMany(Arrays.asList(s1, s2));


            Document c1 = new Document("title", "Intro to CS").append("code", "CS101").append("credits", 4);
            Document c2 = new Document("title", "DB Systems").append("code", "CS202").append("credits", 3);
            courses.insertMany(Arrays.asList(c1, c2));

            Document embedded = new Document("type", "embedded")
                    .append("student", new Document("name", "Manish Aryal").append("email", "manisharyal490@gmail.com").append("age", 23))
                    .append("course", new Document("title", "AI").append("code", "CS305").append("credits", 4))
                    .append("date", "2023-10-15");
            enrollments.insertOne(embedded);


            Document referenced = new Document("type", "referenced")
                    .append("studentId", s1.getObjectId("_id"))
                    .append("courseId", c1.getObjectId("_id"))
                    .append("date", "2023-10-16");
            enrollments.insertOne(referenced);


            System.out.println("\nEmbedded Enrollments:");
            enrollments.find(Filters.eq("type", "embedded")).forEach(doc -> {
                System.out.println("Student: " + doc.get("student"));
                System.out.println("Course: " + doc.get("course"));
                System.out.println("Date: " + doc.getString("date"));
            });


            System.out.println("\nReferenced Enrollments:");
            enrollments.find(Filters.eq("type", "referenced")).forEach(doc -> {
                Document stu = students.find(Filters.eq("_id", doc.getObjectId("studentId"))).first();
                Document cour = courses.find(Filters.eq("_id", doc.getObjectId("courseId"))).first();
                System.out.println("Student: " + stu);
                System.out.println("Course: " + cour);
                System.out.println("Date: " + doc.getString("date"));
            });


            students.updateOne(Filters.eq("_id", s1.getObjectId("_id")), Updates.set("name", "Yaashwant CRR"));
            System.out.println("\nUpdated Student Name: " + students.find(Filters.eq("_id", s1.getObjectId("_id"))).first().getString("name"));


            System.out.println("\nReferenced Enrollment After Update:");
            enrollments.find(Filters.eq("type", "referenced")).forEach(doc -> {
                Document stu = students.find(Filters.eq("_id", doc.getObjectId("studentId"))).first();
                System.out.println("Student Name: " + stu.getString("name"));
            });


            System.out.println("\nEmbedded Enrollment Remains:");
            enrollments.find(Filters.eq("type", "embedded")).forEach(doc ->
                    System.out.println("Student Name: " + doc.get("student", Document.class).getString("name"))
            );


            students.createIndex(Indexes.ascending("name"));
            students.createIndex(Indexes.ascending("email"));
            System.out.println("\nIndexes Created.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
