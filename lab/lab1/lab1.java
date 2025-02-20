import java.io.*;
import java.util.*;

public class lab1 {
    public static void main(String[] args) {
        // 初始化
        int count = 0;
        int sum = 0;
        int average = 0;
        String bufferString;
        List<Student> studentList = new ArrayList<>();
        File file = new File("F:\\vscode\\oop\\lab\\lab1\\grade.txt");
        try {
            // 文件读取
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 读取数据，添加至列表
            while ((bufferString = bufferedReader.readLine()) != null) {
                String[] clip = bufferString.split(" ");
                studentList.add(new Student(clip[0], Integer.parseInt(clip[1])));
                count++;
            }

            // 计算平均值并输出
            for (int i = 0; i < count - 1; i++) {
                sum += studentList.get(i).score;
            }
            average = sum / (count - 1);
            System.out.println("平均成绩是：" + average);

            // 成绩排序输出名字
            studentList.sort(Comparator.comparingInt(Student::getScore).reversed());
            for (int i = 0; i < count - 1; i++) {
                System.err.println(studentList.get(i).name);
            }

            // 关闭文件读取
            bufferedReader.close();
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
