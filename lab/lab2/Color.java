public enum Color {
    black, white, empty;

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
