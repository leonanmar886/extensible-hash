package models;

import java.util.List;
import java.util.Objects;

public class Directory {
    private int globalDepth;
    private List<Line> lines;

    public Directory(int globalDepth, List<Line> lines) {
        this.globalDepth = globalDepth;
        this.lines = lines;
    }

    public Directory() {}

    public int getGlobalDepth() {
        return globalDepth;
    }

    public void setGlobalDepth(int globalDepth) {
        this.globalDepth = globalDepth;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public void search(int key){
        String bucketName = Hasher.hash(key, globalDepth);
        Line lineFind = lines.stream()
                .filter((line) -> Objects.equals(line.getIndex(), bucketName))
                .findFirst()
                .orElse(null);

        if (lineFind != null) {
            Bucket bucket = lineFind.getPointer();
            if (bucket.getEntryValues().contains(key)) {
                System.out.println("Key found in bucket " + bucket.getName()); //TODO: adicionar ao out.txt
            } else {
                System.out.println("Key not found in bucket " + bucket.getName());
            }
        } else {
            System.out.println("Key not found in directory");
        }
    }

    public void insert(int key){
        String bucketName = Hasher.hash(key, globalDepth);
        List<Line> lineFind = lines.stream()
                .filter((line) -> Objects.equals(line.getIndex(), bucketName))
                .toList();

        Bucket bucket = lineFind.get(0).getPointer();
        if (bucket.getEntryValues().size() < 2) {
            bucket.getEntryValues().add(key);
            System.out.println("Key inserted in bucket " + bucket.getName()); //TODO: adicionar ao out.txt
        } else {
            Bucket newBucket = new Bucket();
            if(lineFind.size() > 1){ //caso onde com certeza a pl é menor do que a pg
                if (bucket.getEntryValues().size() == 2) { //se tiver lotado
                    lineFind.get(1).setPointer(newBucket); //separa as linhas que apontavam para o bucket cheio
                    distributeBucket(lineFind.get(0), lineFind.get(1), globalDepth); //distribui os valores do bucket cheio
                } else {
                    bucket.getEntryValues().add(key);
                }
            } else { //caso onde a pl é igual a pg
                if (bucket.getEntryValues().size() < 2) {
                    bucket.getEntryValues().add(key);
                } else {
                    duplicateDirectorySize(); //duplica o diretório
                    Line correctLine = lines.stream()//acha o novo bucket que vai receber parte dos valores distribuidos
                            .filter((line) -> Objects.equals(line.getIndex(), bucketName))
                            .findFirst()
                            .orElse(null);
                    distributeBucket(lineFind.get(0), correctLine, globalDepth );
                }
            }
        }
    }

    private void duplicateDirectorySize(){
        globalDepth++;
        int size = lines.size();
        for (int i = 0; i < size; i++) {
            Line line = lines.get(i);
            Line newLine = new Line();
            line.setIndex(line.getIndex() + "0");
            newLine.setIndex(line.getIndex() + "1");
            newLine.setPointer(line.getPointer());
            newLine.setDepth(line.getDepth());
            lines.add(newLine);
        }
    }

    private void distributeBucket(Line oldLine, Line newLine, int depth){
        oldLine.getPointer().getEntryValues().forEach((key) -> {
            String newBucketName = Hasher.hash(key, depth);
            if (newBucketName.equals(newLine.getIndex())) {
                newLine.getPointer().getEntryValues().add(key);
                oldLine.getPointer().getEntryValues().remove((Integer) key);
            }
        });
        oldLine.setDepth(depth);
        newLine.setDepth(depth);
    }

    public void remove(int key){
        String bucketName = Hasher.hash(key, globalDepth);
        Line lineFind = lines.stream()
                .filter((line) -> Objects.equals(line.getIndex(), bucketName))
                .findFirst()
                .orElse(null);

        if (lineFind != null) {
            Bucket bucket = lineFind.getPointer();
            if (bucket.getEntryValues().contains(key)) {
                bucket.getEntryValues().remove((Integer) key);
                System.out.println("Key removed from bucket " + bucket.getName()); //TODO: adicionar ao out.txt
            } else {
                System.out.println("Key not found in bucket " + bucket.getName());
            }
        } else {
            System.out.println("Key not found in directory");
        }
    }
}
