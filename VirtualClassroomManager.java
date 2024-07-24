import java.util.*;

class Submission {
    private String studentId;
    private String assignmentDetails;

    public Submission(String studentId, String assignmentDetails) {
        this.studentId = studentId;
        this.assignmentDetails = assignmentDetails;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAssignmentDetails() {
        return assignmentDetails;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + ", Assignment: " + assignmentDetails;
    }
}

class Classroom {
    private String name;
    private List<String> students;
    private List<String> assignments;
    private List<Submission> submissions;

    public Classroom(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.assignments = new ArrayList<>();
        this.submissions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getStudents() {
        return students;
    }

    public List<String> getAssignments() {
        return assignments;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void addStudent(String studentId) {
        students.add(studentId);
    }

    public void scheduleAssignment(String assignmentDetails) {
        assignments.add(assignmentDetails);
    }

    public void submitAssignment(String studentId, String assignmentDetails) {
        if (students.contains(studentId)) {
            submissions.add(new Submission(studentId, assignmentDetails));
            System.out.println("Assignment submitted by Student " + studentId + " in " + name + ".");
        } else {
            System.out.println("Student " + studentId + " is not enrolled in " + name + ".");
        }
    }
    }
}

class VirtualClassroomManager {
    private Map<String, Classroom> classrooms;

    public VirtualClassroomManager() {
        this.classrooms = new HashMap<>();
    }

    public void addClassroom(String className) {
        if (!classrooms.containsKey(className)) {
            classrooms.put(className, new Classroom(className));
            System.out.println("Classroom " + className + " has been created.");
        } else {
            System.out.println("Classroom " + className + " already exists.");
        }
    }

    public void addStudent(String studentId, String className) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            classroom.addStudent(studentId);
            System.out.println("Student " + studentId + " has been enrolled in " + className + ".");
        } else {
            System.out.println("Classroom " + className + " does not exist.");
        }
    }

    public void scheduleAssignment(String className, String assignmentDetails) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            classroom.scheduleAssignment(assignmentDetails);
            System.out.println("Assignment for " + className + " has been scheduled.");
        } else {
            System.out.println("Classroom " + className + " does not exist.");
        }
    }

    public void submitAssignment(String studentId, String className, String assignmentDetails) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            classroom.submitAssignment(studentId, assignmentDetails);
        } else {
            System.out.println("Classroom " + className + " does not exist.");
        }
    }

    public void listClassrooms() {
        if (classrooms.isEmpty()) {
            System.out.println("No classrooms available.");
        } else {
            classrooms.keySet().forEach(System.out::println);
        }
    }

    public void listStudentsInClassroom(String className) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            List<String> students = classroom.getStudents();
            if (students.isEmpty()) {
                System.out.println("No students enrolled in " + className + ".");
            } else {
                students.forEach(System.out::println);
            }
        } else {
            System.out.println("Classroom " + className + " does not exist.");
        }
    }

    public void listAssignmentsInClassroom(String className) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            List<String> assignments = classroom.getAssignments();
            if (assignments.isEmpty()) {
                System.out.println("No assignments scheduled for " + className + ".");
            } else {
                assignments.forEach(System.out::println);
            }
        } else {
            System.out.println("Classroom " + className + " does not exist.");
        }
    }

    public void listSubmissionsInClassroom(String className) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            List<Submission> submissions = classroom.getSubmissions();
            if (submissions.isEmpty()) {
                System.out.println("No submissions for " + className + ".");
            } else {
                submissions.forEach(System.out::println);
            }
        } else {
            System.out.println("Classroom " + className + " does not exist.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VirtualClassroomManager manager = new VirtualClassroomManager();

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            String[] parts = command.split(" ", 2);

            switch (parts[0]) {
                case "add_classroom":
                    manager.addClassroom(parts[1]);
                    break;
                case "add_student":
                    String[] studentParts = parts[1].split(" ", 2);
                    manager.addStudent(studentParts[0], studentParts[1]);
                    break;
                case "schedule_assignment":
                    String[] assignmentParts = parts[1].split(" ", 2);
                    manager.scheduleAssignment(assignmentParts[0], assignmentParts[1]);
                    break;
                case "submit_assignment":
                    String[] submissionParts = parts[1].split(" ", 3);
                    manager.submitAssignment(submissionParts[0], submissionParts[1], submissionParts[2]);
                    break;
                case "list_classrooms":
                    manager.listClassrooms();
                    break;
                case "list_students":
                    manager.listStudentsInClassroom(parts[1]);
                    break;
                case "list_assignments":
                    manager.listAssignmentsInClassroom(parts[1]);
                    break;
                case "list_submissions":
                    manager.listSubmissionsInClassroom(parts[1]);
                    break;
                case "exit":
                    scanner.close();
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}
