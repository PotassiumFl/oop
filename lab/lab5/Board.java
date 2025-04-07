public class Board {
    int height;
    int width;
    int emptyRemain;
    int blackCount;
    int whiteCount;
    int boardCount;
    int currentRound;
    int ifFinished;
    int[] canPass;
    Color currentColor;
    Rule rule;
    int playerDrift;
    int boardDrift;
    String winner;
    Player[] player;
    Color[][] boardColor;
    String[][] boardString;

    public Board() {
    }

    public Board(int height, int width, int boardCount, Rule rule, String playerBlack, String playerWhite) {
        // 初始化
        this.height = height;
        this.width = width;
        this.boardCount = boardCount + 1;
        this.emptyRemain = height * width - 4;
        this.rule = rule;
        this.canPass = new int[2];
        canPass[0] = canPass[1] = 0;
        this.currentRound = 0;
        this.playerDrift = 30 + (playerBlack.length() > playerWhite.length() ? playerBlack.length()
                : playerWhite.length());
        this.boardDrift = this.playerDrift + 10;
        this.boardColor = new Color[height][width];
        this.boardString = new String[height + 1][width + 1];
        this.currentColor = Color.black;
        this.winner = null;
        // 创建玩家数据
        this.player = new Player[] { new Player(playerBlack, Color.black), new Player(playerWhite, Color.white) };
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.boardColor[i][j] = Color.empty;
            }
        }

        // 初始棋子
        if (this.rule != Rule.gomoku) {
            this.boardColor[3][3] = this.boardColor[4][4] = Color.white;
            this.boardColor[3][4] = this.boardColor[4][3] = Color.black;
        }
        this.colorToChar();

        // 字符棋盘添加行列引索
        for (int i = 1; i <= this.height; i++) {
            this.boardString[i][0] = Integer.toString(i);
        }
        for (int i = 1; i <= this.width; i++) {
            this.boardString[0][i] = String.valueOf((char) (64 + i));
        }
        this.boardString[0][0] = " ";
        this.count();
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
    public void printBoard(Boarddata boarddata) {
        for (int i = 0; i <= this.height; i++) {
            for (int j = 0; j <= this.width; j++) {
                System.out.print(this.boardString[i][j] + " ");
            }
            System.out.println();
        }
        this.playerPrint();
        boarddata.boardListPrint();
    }

    // 游戏主体更新棋盘
    public int boardUpdate(String move) {
        // 解析输入位置
        int row = Integer.valueOf(move.charAt(0)) - 48;
        int column = Integer.valueOf(move.charAt(1)) - 96;

        // 判断是否合法
        if (row > this.height || row < 0 || column > this.width ||
                column < 0) {
            System.out.println("落子不在棋盘上，请重新输入！");
            return 0;
        }
        if (this.boardColor[row - 1][column - 1] != Color.empty) {
            System.out.println("该位置已有棋子，请重新输入！");
            return 0;
        }
        if (this.rule == Rule.reversi) {
            if (this.boardString[row][column] != "+") {
                System.out.println("不符合规则的落子，请重新输入！");
                return 0;
            }
        }
        this.boardColor[row - 1][column - 1] = this.currentColor;
        this.reversiConvert(row, column);// 进入翻转逻辑
        this.checkGomoku(row, column);
        this.emptyRemain--;
        if (emptyRemain == 0)
            this.ifFinished = 1;
        if (canPass[0] == 1 && canPass[1] == 1)
            this.ifFinished = 1;
        this.currentRound++;
        this.colorToChar();// 字符化
        this.count();// 棋子计数
        this.currentColor = Color.convert(this.currentColor);// 切换玩家
        this.ifPlaceable();// 判断可落子位置
        return 1;
    }

    public void playerPrint() {
        System.out.print("\033[4;24H");// 跳转光标位置
        System.out.print("棋盘" + this.boardCount);
        System.out.print("\033[5;24H");
        System.out.print("玩家[" + this.player[0].name + "]");
        System.out.print("\033[5;" + playerDrift + "H");// 为对其跳转列位置由最长玩家名决定
        if (currentColor == Color.black) {
            System.out.print("\u25CB");
        } else {
            System.out.print(" ");
        }
        if (this.rule == Rule.reversi)
            System.out.print(" " + this.blackCount);
        System.out.print("\033[6;24H");
        System.out.print("玩家[" + this.player[1].name + "]");
        System.out.print("\033[6;" + playerDrift + "H");
        if (currentColor == Color.white) {
            System.out.print("\u25CF");
        } else {
            System.out.print(" ");
        }
        if (this.rule == Rule.reversi)
            System.out.print(" " + this.whiteCount);
        if (this.rule == Rule.gomoku) {
            System.out.print("\033[7;24H");
            System.out.print("Current round: " + currentRound);
        }
    }

    public void ifPlaceable() {
        if (this.rule != Rule.reversi) {
            return;// 非reversi规则无需判断，直接跳出
        }
        this.canPass[this.currentColor.ordinal()] = 1;// 默认无落子点，即允许放弃行棋
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                int jump = 0;// 临时标识符
                if (this.boardColor[i][j] != Color.empty) {
                    continue;// 非空位置直接跳过
                }
                for (Direction direction : Direction.values()) {
                    if ((i + direction.rowChange) < 0 || (i + direction.rowChange) >= 8
                            || (j + direction.columnChange) < 0 || (j + direction.columnChange) >= 8) {
                        continue;// 若在边界上，越界方向直接跳过
                    }
                    if (this.boardColor[i + direction.rowChange][j + direction.columnChange] != Color
                            .convert(currentColor)) {
                        continue;// 相邻位置不是对方棋子则直接跳过
                    }
                    for (int k = 2; k <= 8; k++) {
                        if ((i + direction.rowChange * k) < 0 || (i + direction.rowChange * k) >= 8
                                || (j + direction.columnChange * k) < 0 || (j + direction.columnChange * k) >= 8) {
                            break;// 越界跳出
                        }
                        if (this.boardColor[i + direction.rowChange * k][j + direction.columnChange * k] == Color.empty)
                            break;// 遇空跳出
                        if (this.boardColor[i + direction.rowChange * k][j
                                + direction.columnChange * k] == this.currentColor) {
                            jump = 1;// 连续对方棋子，且能找到己方棋子，则该位置是合法落子点
                            break;
                        }
                    }
                    if (jump == 1) {
                        this.boardString[i + 1][j + 1] = "+";// 标识合法落子点
                        this.canPass[this.currentColor.ordinal()] = 0;// 有合法落子点，不允许放弃行棋
                        break;
                    }
                }
            }
        }
    }

    public void reversiConvert(int row, int column) {
        if (this.rule != Rule.reversi) {
            return;// 非reversi规则无需翻转，直接跳出
        }
        for (Direction direction : Direction.values()) {
            if (row + direction.rowChange <= 0 || row + direction.rowChange > 8 || column + direction.columnChange <= 0
                    || column + direction.columnChange > 8) {
                continue;// 越界跳过
            }
            if (this.boardColor[row - 1 + direction.rowChange][column - 1
                    + direction.columnChange] == this.currentColor)
                continue;// 相邻位置为己方跳过
            for (int k = 1; k <= 8; k++) {
                if (row + k * direction.rowChange <= 0 || row + k * direction.rowChange > 8
                        || column + k * direction.columnChange <= 0 || column + k * direction.columnChange > 8)
                    break;// 越界跳出
                if (this.boardColor[row - 1 + direction.rowChange * k][column - 1
                        + direction.columnChange * k] == Color.empty)
                    break;// 存在空的跳出
                if (this.boardColor[row - 1 + direction.rowChange * k][column - 1 + direction.columnChange * k] == Color
                        .convert(this.currentColor)) {
                    continue;// 连续对方棋子，继续循环
                }

                if (k > 1) {// 有连续对方棋子，且被己方棋子所夹
                    for (int m = k - 1; m > 0; m--) {
                        this.boardColor[row - 1 + m * direction.rowChange][column - 1
                                + m * direction.columnChange] = Color
                                        .convert(this.boardColor[row - 1 + m * direction.rowChange][column - 1
                                                + m * direction.columnChange]);// 翻转棋子
                    }
                    break;
                }
            }
        }
    }

    public void printResult() {
        System.out.println("游戏结束！");
        if (this.rule == Rule.peace) {
            return;// peace模式不打印分数，直接跳出
        }
        // 打印双方得分
        if (this.rule == Rule.gomoku) {
            System.out.println("玩家[" + this.winner + "]获胜！");
            return;
        }
        System.out.println("Player 1 [" + this.player[0].name + "]\u25CB" + this.blackCount);
        System.out.println("Player 2 [" + this.player[1].name + "]\u25CF" + this.whiteCount);
        if (this.blackCount > this.whiteCount) {
            System.out.println("Player 1 [" + this.player[0].name + "]获胜！");
            return;
        } else if (this.blackCount < this.whiteCount) {
            System.out.println("Player 2 [" + this.player[1].name + "]获胜！");
            return;
        } else {
            System.out.println("平局！");
            return;
        }
    }

    // 棋盘计数
    public void count() {
        this.whiteCount = 0;
        this.blackCount = 0;
        for (Color colorList[] : this.boardColor) {
            for (Color color : colorList) {
                switch (color) {
                    case white:
                        this.whiteCount += 1;
                        break;
                    case black:
                        this.blackCount += 1;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void checkGomoku(int row, int column) {
        if (this.rule != Rule.gomoku)
            return;
        for (Direction direction : Direction.values()) {
            if (direction == Direction.down
                    || direction == Direction.downleft
                    || direction == Direction.left
                    || direction == Direction.upleft)
                continue;

            int count = 1;
            for (int k = 1; k < this.height; k++) {
                if (row - 1 + k * direction.rowChange < 0
                        || row - 1 + k * direction.rowChange >= this.height
                        || column - 1 + k * direction.columnChange < 0
                        || column - 1 + k * direction.columnChange >= this.width)
                    break;
                if (boardColor[row - 1 + k * direction.rowChange][column - 1
                        + k * direction.columnChange] != this.currentColor)
                    break;
                count++;
            }
            for (int k = -1; k > -1 * this.height; k--) {
                if (row - 1 + k * direction.rowChange < 0
                        || row - 1 + k * direction.rowChange >= this.height
                        || column - 1 + k * direction.columnChange < 0
                        || column - 1 + k * direction.columnChange >= this.width)
                    break;
                if (boardColor[row - 1 + k * direction.rowChange][column - 1
                        + k * direction.columnChange] != this.currentColor)
                    break;
                count++;
            }
            if (count >= 5) {
                ifFinished = 1;
                for (Player player : this.player) {
                    if (player.color == this.currentColor)
                        winner = player.name;
                }
            }
        }
    }
}