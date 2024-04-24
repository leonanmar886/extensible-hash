package models;

public class Line {
    private String index;
    private Bucket bucket;
    private int depth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Line(String index, Bucket bucket, int depth) {
        this.index = index;
        this.bucket = bucket;
        this.depth = depth;
    }

    public Line() {}

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Bucket getPointer() {
        return bucket;
    }

    public void setPointer(Bucket bucket) {
        this.bucket = bucket;
    }
}
