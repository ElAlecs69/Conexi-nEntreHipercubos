package ConexiónDeHipercubos;

public class Nodos {
    private String id;
    private int x;
    private int y;

    public Nodos(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getIdInt() {
        // binario a decimal
        return String.valueOf(Integer.parseInt(id, 2));
    }
}