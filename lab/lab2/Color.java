public enum Color {
    // 枚举棋盘状态
    black, white, empty;

    // 枚举对应字符
    public String toString() {
        switch (this) {
            case black:
                return "\u25CB";
            case white:
                return "\u25CF";
            case empty:
                return "·";
            default:
                return " ";
        }
    }

    // 反转颜色
    public static Color convert(Color color) {
        switch (color) {
            case black:
                return Color.white;
            case white:
                return Color.black;
            default:
                return Color.empty;
        }
    }

}
