package org.example.index;

public interface IndexProcessor {
    void tryToAddIndex(int columnNumber, String str, long offset, int length);
}
