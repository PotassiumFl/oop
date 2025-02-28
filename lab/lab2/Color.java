public enum Color {
    black, white, empty;

    public void printColor() {
        switch (this) {
            case black:
                System.out.print("◉");
                break;
            case white:
                System.out.print("◯");
                break;
            case empty:
                System.out.print("·");
                break;
            default:
                break;
        }
    }
}
