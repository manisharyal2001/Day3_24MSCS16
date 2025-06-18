# MongoDB Student Enrollment System - Day 3 Task

This Java application demonstrates different approaches to managing student enrollments in courses using MongoDB, comparing embedded and referenced document structures.

## Task Description

The task involved creating a MongoDB database to manage student enrollments with both embedded and referenced document approaches, including:
- Inserting sample students and courses
- Creating both embedded and referenced enrollment records
- Querying and displaying enrollment details
- Updating student information to observe differences between approaches
- Creating indexes for efficient querying

## Implementation Details

### Database Structure
- **Collections**:
    - `students`: Stores student details (name, email, age)
    - `courses`: Stores course details (title, code, credits)
    - `enrollments`: Stores enrollment records in both embedded and referenced formats

### Document Structures
1. **Embedded Enrollment**:
    - Contains complete student and course data within the enrollment document
    - Example:
      ```json
      {
        "student": {
          "name": "Manish Aryal",
          "email": "manisharyal49@gmail.com",
          "age": 23
        },
        "course": {
          "title": "AI",
          "code": "65305",
          "credits": 4
        },
        "date": "2023-10-15"
      }
      ```

2. **Referenced Enrollment**:
    - Contains references (ObjectIds) to student and course documents
    - Example:
      ```json
      {
        "studentId": ObjectId("4852cfc7b59bf331a32adda8"),
        "courseId": ObjectId("4852cfc7b59bf331a32adda8"),
        "date": "2023-10-16"
      }
      ```

### Key Observations
- Updating a student's name in referenced enrollments automatically reflects in all related enrollments
- Embedded enrollments maintain their own copy of student data, requiring separate updates
- Indexes were created to optimize student queries

## Screenshots
1. **Console Output**:  
   ![Console Output](https://raw.githubusercontent.com/manisharyal2001/Day3_24MSCS16/main/console_output.png)

   Shows the embedded and referenced enrollments, update operation, and index creation.

2. **Database Structure in MongoDB Compass**:  
    ![Compass View 1](https://raw.githubusercontent.com/manisharyal2001/Day3_24MSCS16/main/courses.png)

    ![Compass View 2](https://raw.githubusercontent.com/manisharyal2001/Day3_24MSCS16/main/enrollments.png)

    ![Updated Student](https://raw.githubusercontent.com/manisharyal2001/Day3_24MSCS16/main/students.png)

   Displays the collections and sample documents.

3. **Updated Student Record**:  
   ![Updated Student](https://raw.githubusercontent.com/manisharyal2001/Day3_24MSCS16/main/Screenshot%202025-06-18%20211618.png
   )  
   Shows the student name change from "Yashmant CR" to "Yashmant CRR".

## How to Run

1. Ensure MongoDB is installed and running
2. Clone this repository
3. Run the Java application (Main class contains all operations)
4. View results in console or connect via MongoDB Compass

## Dependencies
- MongoDB Java Driver
- Java 8 or higher