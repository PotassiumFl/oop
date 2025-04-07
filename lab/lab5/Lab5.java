import java.io.IOException;
import java.util.*;

public class Lab5 {
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
            Boarddata boarddata = new Boarddata(8, 8, 3, player1, player2);
            boarddata.boardDataPrint();

            // 游戏主体循环
            while (boarddata.ifQuit == 0) {
                boarddata.boardGame(input);
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                boarddata.boardDataPrint();
            }

            // 游戏结束
            input.close();
            System.out.print("\033[10;1H");
            System.out.println("游戏结束！");
        } catch (InterruptedException | IOException e) {
        }
    }
}