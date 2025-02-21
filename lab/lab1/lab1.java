import java.io.*;
import java.util.*;

public class lab1 {
    public static void main(String[] args) {
        // 初始化
        int count = 0;
        double sum = 0.0;
        double average = 0.0;
        String bufferString;
        List<Student> studentList = new ArrayList<>();
        File file = new File("grade.txt");

        // 异常处理
        try {
            // 文件读取
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 读取数据，添加至列表
            while ((bufferString = bufferedReader.readLine()) != null) {
                String[] clip = bufferString.split(" ");
                studentList.add(new Student(clip[0], Integer.parseInt(clip[1])));
                sum += Integer.parseInt(clip[1]);
                count++;
            }

            // 计算平均值并输出
            average = sum / count;
            System.out.println("平均成绩是：" + average);

            // 成绩排序输出名字
            studentList.sort(Comparator.comparingInt(Student::getScore).reversed());
            for (int i = 0; i < count; i++) {
                System.out.println(studentList.get(i).name);
            }

            // 关闭文件读取
            bufferedReader.close();
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
