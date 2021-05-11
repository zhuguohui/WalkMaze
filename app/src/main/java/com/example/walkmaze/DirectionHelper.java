package com.example.walkmaze;

/**
 * @author zhuguohui
 * @description:
 * @date :2021/5/11 14:15
 */
public class DirectionHelper {
    private int maxRows, maxCols;//最大

    public DirectionHelper(int rows, int cols) {
        this.maxRows = rows;
        this.maxCols = cols;
    }

    public Position[] getDirections(Position cp) {
        Position[] directions = new Position[4];
        directions[0] = getRight(cp);
        directions[1] = getBottom(cp);
        directions[2] = getLeft(cp);
        directions[3] = getTop(cp);
        return directions;
    }

    public Position getRight(Position cp) {
        int col = cp.col + 1;
        int row = cp.row;
        if (!isOut(row, col)) {
            return new Position(row, col);
        } else {
            return null;
        }
    }

    public Position getBottom(Position cp) {
        int col = cp.col;
        int row = cp.row + 1;
        if (!isOut(row, col)) {
            return new Position(row, col);
        } else {
            return null;
        }
    }

    public Position getLeft(Position cp) {
        int col = cp.col - 1;
        int row = cp.row;
        if (!isOut(row, col)) {
            return new Position(row, col);
        } else {
            return null;
        }
    }

    public Position getTop(Position cp) {
        int col = cp.col;
        int row = cp.row - 1;
        if (!isOut(row, col)) {
            return new Position(row, col);
        } else {
            return null;
        }
    }

    private boolean isOut(int row, int cols) {
        if (row < 0 || row >= maxRows) {
            return true;
        }
        if (cols < 0 || cols >= maxCols) {
            return true;
        }
        return false;
    }
} 