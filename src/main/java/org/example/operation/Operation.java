package org.example.operation;

import org.example.index.FilePosition;

import java.util.Set;

public interface Operation {
    Set<FilePosition> evaluate();

}
