package com.example.walkmaze;

import java.util.Objects;

/**
 * @author zhuguohui
 * @description:
 * @date :2021/5/11 14:14
 */
public class Position {
    int row, col;

    public Position(int x, int y) {
        this.row = x;
        this.col = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}