public class Board {
    int height;
    int width;
    int emptyRemain;
    int boardCount;
    Color currentColor;
    Player[] player;
    Color[][] boardColor;
    String[][] boardString;

    public Board() {
    }

    public Board(int height, int width, int boardCount, String playerBlack, String playerWhite) {
        this.height = height;
        this.width = width;
        this.boardCount = boardCount;
        this.emptyRemain = height * width;
        this.boardColor = new Color[height][width];
        this.boardString = new String[height + 1][width + 1];
        this.currentColor = Color.black;
        // 创建玩家数据
        this.player = new Player[] { new Player(playerBlack, Color.black), new Player(playerWhite, Color.white) };
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.boardColor[i][j] = Color.empty;
                this.boardString[i + 1][j + 1] = this.boardColor[i][j].toString();
            }
        }
        // 字符棋盘添加行列引索
        for (int i = 1; i <= this.height; i++) {
            this.boardString[i][0] = Integer.toString(i);
        }
        for (int i = 1; i <= this.width; i++) {
            this.boardString[0][i] = String.valueOf((char) (64 + i));
        }
        this.boardString[0][0] = " ";
    }

    // 枚举类型棋盘转换为字符类型棋盘
    public void colorToChar() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.boardString[i + 1][j + 1] = this.boardColor[i][j].toString();
            }
        }
    }

    // 打印游戏地图及当前玩家状态
    public void printBoard() {
        for (int i = 0; i <= this.height; i++) {
            for (int j = 0; j <= this.width; j++) {
                System.out.print(this.boardString[i][j] + " ");
            }
            if (i == 3) {
                System.out.print("\t\t棋盘" + this.boardCount);
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

    // 游戏主体更新棋盘
    public int boardUpdate(String move) {
        // 解析输入位置
        int row = Integer.valueOf(move.charAt(0)) - 48;
        int column = Integer.valueOf(move.charAt(1)) - 96;

        // 判断是否合法
        if (row > this.height || row < 0 || column > this.width ||
                column < 0 || this.boardColor[row - 1][column - 1] != Color.empty) {
            System.out.println("输入非法，请重新输入！");
            return 0;
        } else {
            this.boardColor[row - 1][column - 1] = this.currentColor;
            emptyRemain--;
            this.colorToChar();
            this.currentColor = Color.convert(this.currentColor);
            return 1;
        }
    }
}