import java.util.Scanner;

public class Mapdata {
    int height;
    int width;
    int emptyRemain;
    Color currentColor;
    Player[] player;
    Color[][] mapColor;
    String[][] mapString;

    public Mapdata() {
    }

    public Mapdata(int height, int width, String playerBlack, String playerWhite) {
        this.height = height;
        this.width = width;
        this.emptyRemain = height * width;
        this.currentColor = Color.black;
        this.mapColor = new Color[this.height][this.width];
        this.mapString = new String[this.height + 1][this.width + 1];
        this.player = new Player[] { new Player(playerBlack, Color.black), new Player(playerWhite, Color.white) };
        this.mapString[0][0] = " ";
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.mapColor[i][j] = Color.empty;
                this.mapString[i + 1][j + 1] = this.mapColor[i][j].toString();
            }
        }
        for (int i = 1; i <= this.height; i++) {
            this.mapString[i][0] = Integer.toString(i);
        }
        for (int i = 1; i <= this.width; i++) {
            this.mapString[0][i] = String.valueOf((char) (64 + i));
        }
    }

    public void colorToChar() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.mapString[i + 1][j + 1] = this.mapColor[i][j].toString();
            }
        }
    }

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

    public void mapUpdate(String move) {
        int row = Integer.valueOf(move.charAt(0)) - 48;
        int column = Integer.valueOf(move.charAt(1)) - 96;
        if (row > this.height || row < 0 || column > width ||
                column < 0 || this.mapColor[row - 1][column - 1] != Color.empty)
            System.out.println("输入非法");
        else {
            this.mapColor[row - 1][column - 1] = this.currentColor;
            emptyRemain--;
            this.colorToChar();
        }
    }

    public void mapGame(Scanner input) {
        for (Player player : this.player) {
            if (player.color == this.currentColor) {
                System.out.println("请玩家[" + player.name + "]输入落子位置：");
            }
        }
        String move = input.nextLine();
        if (move.length() != 2)
            return;
        this.mapUpdate(move);
        this.currentColor = Color.convert(this.currentColor);
    }
}
