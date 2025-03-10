import java.io.IOException;
import java.util.Scanner;

public class Lab2 {
    public static void main(String[] args) {
        // 初始化棋盘
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            String player1, player2;
            Scanner input = new Scanner(System.in);
            System.out.print("请输入黑方：");
            player1 = input.nextLine();
            System.out.print("请输入白方：");
            player2 = input.nextLine();
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            Mapdata map = new Mapdata(8, 8, player1, player2);
            map.printMap();

            // 游戏主体循环
            while (map.emptyRemain != 0) {
                map.mapGame(input);
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                map.printMap();
            }

            // 游戏结束
            input.close();
            System.out.println("棋盘已满！");
            System.out.println("游戏结束！");
        } catch (InterruptedException | IOException e) {
        }
    }
}
