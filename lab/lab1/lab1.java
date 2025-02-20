import java.io.*;
import java.util.*;

public class lab1 {
    public static void main(String[] args) {
        System.setProperty("sun.stdout.encoding", "UTF-8");
        File file = new File("F:\\vscode\\oop\\lab\\lab1\\grade.txt");
        try {
            int count = 0;
            int sum = 0;
            int average = 0;
            String bufferString;
            List<Student> studentList = new ArrayList<>();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((bufferString = bufferedReader.readLine()) != null) {
                String[] clip = bufferString.split(" ");
                studentList.add(new Student(clip[0], Integer.parseInt(clip[1])));
                count++;
            }

            for (int i = 0; i < count - 1; i++) {
                sum += studentList.get(i).score;
            }

            average = sum / (count - 1);
            System.out.println("平均成绩是：" + average);

            studentList.sort(Comparator.comparingInt(Student::getScore).reversed());

            for (int i = 0; i < count - 1; i++) {
                System.err.println(studentList.get(i).name);
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
