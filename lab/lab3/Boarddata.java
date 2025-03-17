import java.util.Scanner;

public class Boarddata {
    int boardNum;
    int currentBoard;
    Board[] board;

    // 构造方法
    public Boarddata() {
    }

    public Boarddata(int height, int width, int boardNum, String playerBlack, String playerWhite) {
        this.boardNum = boardNum;
        this.currentBoard = 1;

        // 创建空棋盘
        this.board = new Board[boardNum];
        for (int i = 0; i < boardNum; i++)
            board[i] = new Board(height, width, i + 1, playerBlack, playerWhite);
    }

    // 游戏主体逻辑
    public void boardGame(Scanner input) {
        int ifCorrect = 0;

        // 判断当前玩家
        for (Player player : this.board[currentBoard - 1].player) {
            if (player.color == this.board[currentBoard - 1].currentColor) {
                System.out.print("请玩家[" + player.name + "]输入落子位置或者棋盘编号：");
            }
        }

        while (ifCorrect == 0) {
            String move = input.nextLine();
            if (move.length() == 1) {
                if (Integer.valueOf(move) <= 0 || Integer.valueOf(move) > this.boardNum) {
                    System.out.println("输入非法，请重新输入！");
                    continue;
                } else {
                    this.currentBoard = Integer.valueOf(move);
                    return;
                }
            } else if (move.length() == 2) {
                ifCorrect = this.board[currentBoard - 1].boardUpdate(move);
            } else {
                System.out.println("输入非法，请重新输入！");
            }
        }
    }
}
