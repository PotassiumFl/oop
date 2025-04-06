public enum Direction {
    up("up", -1, 0),
    upright("upright", -1, 1),
    right("right", 0, 1),
    downright("downright", 1, 1),
    down("down", 1, 0),
    downleft("downleft", 1, -1),
    left("left", 0, -1),
    upleft("upleft", -1, -1);

    String name;
    int rowChange;// 行步进
    int columnChange;// 列步进

    private Direction() {
    }

    private Direction(String name, int rowChange, int columnChange) {
        this.name = name;
        this.rowChange = rowChange;
        this.columnChange = columnChange;
    }

}
