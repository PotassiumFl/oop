public class Mapdata {
    int height;
    int width;
    Color[][] mapPaint;

    public Mapdata() {
    }

    public Mapdata(int height, int width) {
        this.height = height;
        this.width = width;
        this.mapPaint = new Color[height][width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.mapPaint[i][j] = Color.empty;
            }
        }
    }

    public void printMap() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.mapPaint[i][j].printColor();
            }
            System.out.println();
        }
    }
}
