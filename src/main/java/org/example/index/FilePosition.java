package org.example.index;

public class FilePosition {
    private Long offset;
    private int length;

    public FilePosition() {}

    public FilePosition(Long offset, int length) {
        this.offset = offset;
        this.length = length;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Position {offset: " + offset + ", length: " + length + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FilePosition){
            var pos2 = (FilePosition) obj;
            return offset.equals(pos2.getOffset()) && length == pos2.getLength();
        }
        return false;
    }
}
