import java.io.*;

public class lab1 {
    public static void main(String[] args) {
        File file1 = new File("F:\\vscode\\oop\\lab\\lab1\\grade.txt");
        if (file1.isFile() && file1.exists()) {
            System.err.println("1");
        } else {
            System.err.println("0");
        }
        // FileReader fileReader = new FileReader(file1);
        // BufferedReader bufferedReader = new BufferedReader(fileReader);
        // System.err.println(System.getProperty("user.dir"));
    }
}
