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
}
