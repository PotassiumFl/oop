import java.util.ArrayList;
import java.util.Scanner;

public class Boarddata {
    int height;
    int width;
    int boardNum;
    int currentBoard;
    int ifQuit;
    int boardDrift;
    String playerBlack;
    String playerWhite;
    ArrayList<Board> board;

    // 构造方法
    public Boarddata() {
    }

    public Boarddata(int height, int width, int boardNum, String playerBlack, String playerWhite) {
        this.height = height;
        this.width = width;
        this.boardNum = boardNum;
        this.currentBoard = 1;
        this.ifQuit = 0;
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.boardDrift = 40 + (playerBlack.length() > playerWhite.length() ? playerBlack.length()
                : playerWhite.length());
        this.board = new ArrayList<>();

        // 创建棋盘
        board.add(new Board(height, width, board.size(), Rule.peace, playerBlack, playerWhite));
        board.add(new Board(height, width, board.size(), Rule.reversi, playerBlack, playerWhite));
    }

    // 游戏主体逻辑
    public void boardGame(Scanner input) {
        int ifCorrect = 0;
        System.out.print("\033[10;1H");
        if (this.board.get(this.currentBoard - 1).emptyRemain == 0) {
            this.board.get(this.currentBoard - 1).printResult();
        }
        // 判断当前玩家
        for (Player player : this.board.get(this.currentBoard - 1).player) {
            if (player.color == this.board.get(this.currentBoard - 1).currentColor) {

                if (this.board.get(this.currentBoard - 1).canPass[player.color.ordinal()] == 1
                        && this.board.get(this.currentBoard - 1).emptyRemain != 0) {
                    System.out.println("玩家[" + player.name + "]无子可下！");
                }
                System.out.print(
                        "请玩家[" + player.name + "]输入落子位置（1A）/棋盘编号（1-" + this.boardNum
                                + "）/新游戏类型（peace或reversi）");
                if (this.board.get(this.currentBoard - 1).rule == Rule.reversi) {
                    System.out.print("/放弃行棋（pass）");
                }
                System.out.println("/退出游戏（quit）");

            }
        }

        while (ifCorrect == 0) {
            String move = input.nextLine();
            if (move.matches("\\d+")) {// 全数字，则为棋盘跳转
                if (Integer.valueOf(move) <= 0 || Integer.valueOf(move) > this.boardNum) {
                    System.out.println("不存在该棋盘，请重新输入！");
                    continue;
                } else {
                    this.currentBoard = Integer.valueOf(move);
                    this.board.get(this.currentBoard - 1).ifPlaceable();
                    return;
                }
            } else if (move.length() == 2) {// 长为2，进入切割
                ifCorrect = this.board.get(this.currentBoard - 1).boardUpdate(move);
            } else if (move.equals("peace")) {// 创建peace棋盘
                this.board.add(new Board(this.height, this.width, this.board.size(), Rule.peace, this.playerBlack,
                        this.playerWhite));
                this.boardNum += 1;
                return;
            } else if (move.equals("reversi")) {// 创建reversi棋盘
                this.board.add(new Board(this.height, this.width, this.board.size(), Rule.reversi, this.playerBlack,
                        this.playerWhite));
                this.boardNum += 1;
                return;
            } else if (move.equals("pass")) {// 放弃行棋
                if (this.board.get(this.currentBoard - 1).canPass[this.board.get(this.currentBoard - 1).currentColor
                        .ordinal()] == 1) {
                    this.board.get(this.currentBoard - 1).currentColor = Color
                            .convert(this.board.get(this.currentBoard - 1).currentColor);
                    this.board.get(this.currentBoard - 1).ifPlaceable();
                    return;
                } else {
                    System.out.println("存在合法落子点，不允许放弃行棋，请重新输入！");
                }
            } else if (move.equals("quit")) {// 退出游戏
                this.ifQuit = 1;
                return;
            } else {
                System.out.println("输入非法，请重新输入！");
            }
        }
    }

    // 打印棋盘列表
    public void boardListPrint() {
        System.out.print("\033[1;" + this.boardDrift + "H");
        System.out.println("Game List");
        int printCount = 0;// 计数
        int row = 0;
        int column = this.boardDrift;
        for (Board board : this.board) {
            row = printCount + 2;
            System.out.print("\033[" + row + ";" + column + "H");
            System.out.println(board.boardCount + "、" + board.rule);
            printCount++;
            if (printCount == 8) {// 8个一列
                printCount = 0;
                column += 12;
            }
        }
        System.out.println("\033[10:2H");
    }

    // 棋盘打印
    public void boardDataPrint() {
        this.board.get(this.currentBoard - 1).printBoard(this);
    }
}