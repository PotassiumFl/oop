import java.util.Scanner;

public class Mapdata {
    int height;
    int width;
    int emptyRemain;
    Color currentColor;
    Player[] player;
    Color[][] mapColor;
    String[][] mapString;

    // 构造方法
    public Mapdata() {
    }

    public Mapdata(int height, int width, String playerBlack, String playerWhite) {
        this.height = height;
        this.width = width;
        this.emptyRemain = height * width;
        this.currentColor = Color.black;

        // 创建玩家数据
        this.player = new Player[] { new Player(playerBlack, Color.black), new Player(playerWhite, Color.white) };

        // 创建空棋盘
        this.mapColor = new Color[this.height][this.width];
        this.mapString = new String[this.height + 1][this.width + 1];
        this.mapString[0][0] = " ";
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.mapColor[i][j] = Color.empty;
                this.mapString[i + 1][j + 1] = this.mapColor[i][j].toString();
            }
        }

        // 字符数组添加行列引索
        for (int i = 1; i <= this.height; i++) {
            this.mapString[i][0] = Integer.toString(i);
        }
        for (int i = 1; i <= this.width; i++) {
            this.mapString[0][i] = String.valueOf((char) (64 + i));
        }
    }

    // 对象数组转至字符数组
    public void colorToChar() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.mapString[i + 1][j + 1] = this.mapColor[i][j].toString();
            }
        }
    }

    // 打印游戏地图及当前玩家状态
    public void printMap() {
        for (int i = 0; i <= this.height; i++) {
            for (int j = 0; j <= this.width; j++) {
                System.out.print(this.mapString[i][j] + " ");
            }
            if (i == 4) {
                System.out.print("\t\t玩家[" + this.player[0].name + "]");
                if (currentColor == Color.black) {
                    System.out.print("\u25CB");
                }
            }
            if (i == 5) {
                System.out.print("\t\t玩家[" + this.player[1].name + "]");
                if (currentColor == Color.white) {
                    System.out.print("\u25CF");
                }
            }
            System.out.println();
        }
    }

    // 游戏主体更新地图
    public int mapUpdate(String move) {
        // 解析输入位置
        int row = Integer.valueOf(move.charAt(0)) - 48;
        int column = Integer.valueOf(move.charAt(1)) - 96;

        // 判断是否合法
        if (row > this.height || row < 0 || column > width ||
                column < 0 || this.mapColor[row - 1][column - 1] != Color.empty) {
            System.out.println("输入非法，请重新输入！");
            return 0;
        } else {
            this.mapColor[row - 1][column - 1] = this.currentColor;
            emptyRemain--;
            this.colorToChar();
            return 1;
        }
    }

    // 游戏主体函数
    public void mapGame(Scanner input) {
        int ifCorrect = 0;

        // 判断当前玩家
        for (Player player : this.player) {
            if (player.color == this.currentColor) {
                System.out.println("请玩家[" + player.name + "]输入落子位置：");
            }
        }

        // 输入循环
        while (ifCorrect == 0) {
            String move = input.nextLine();
            if (move.length() != 2) {
                System.out.println("输入非法，请重新输入！");
            } else {
                ifCorrect = this.mapUpdate(move);
            }
        }
        this.currentColor = Color.convert(this.currentColor);
    }
}
