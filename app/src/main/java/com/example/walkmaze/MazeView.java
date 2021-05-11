package com.example.walkmaze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Stack;

/**
 * @author zhuguohui
 * @description:
 * @date :2021/5/11 10:37
 */
public class MazeView extends View {

    int[][] mazeData;
    Position[][] mazePositionData;
    int[][] pathData;//保存路径的
    private int row;
    private int col;
    private Paint wallPaint;//画墙
    private Paint pathPaint;//画路径
    private Paint checkPaint;//画正在检查的点

    private int itemWidth;
    private int itemHeight;

    private int wallColor;

    private Position checkPosition;//正在检查的点

    public MazeView(Context context) {
        super(context);
    }

    public MazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        wallPaint = new Paint();
        wallColor = Color.parseColor("#E2AB6F");
        wallPaint.setColor(wallColor);
        wallPaint.setStyle(Paint.Style.FILL);


        pathPaint = new Paint();
        pathPaint.setColor(Color.parseColor("#00ff00"));
        pathPaint.setStyle(Paint.Style.FILL);
        pathPaint.setAlpha(100);

        checkPaint = new Paint();
        checkPaint.setColor(Color.parseColor("#ff0000"));
        checkPaint.setStyle(Paint.Style.FILL);


    }

    /**
     * 设置地图数据，一个二维数组，其中0表示可以走，1表示墙壁
     *
     * @param mazeData
     */
    public void setMazeData(int[][] mazeData) {

        row = mazeData.length;
        col = mazeData[0].length;
        this.mazeData = mazeData;
        this.mazePositionData = new Position[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                mazePositionData[i][j] = new Position(i, j);
            }
        }


    }

    private void resetPath() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                pathData[i][j] = 0;
            }
        }
    }


    public void setCheckPosition(Position checkPosition) {
        this.checkPosition = checkPosition;
        invalidate();
    }

    /**
     * 生成带墙壁的迷宫
     *
     * @param mazeData
     * @return
     */
    private int[][] makeWall(int[][] mazeData) {
        int[][] wallMaze = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 || i == row - 1 || j == 0 || j == col - 1) {
                    wallMaze[i][j] = 1;
                } else {
                    wallMaze[i][j] = mazeData[i - 1][j - 1];
                }
            }
        }
        return wallMaze;
    }

    Stack<Position> pathStack;

    public void setPathData(Stack<Position> stack) {
        this.pathStack = stack;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemWidth = w / col;
        itemHeight = h / row;
        invalidate();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#ffffff"));

        for (int i = 0; i < row; i++) {

            for (int j = 0; j < col; j++) {
                canvas.save();
                canvas.translate(j * itemWidth, i * itemHeight);
                if (mazeData[i][j] != 0) {
                    canvas.drawRect(0, 0, itemWidth, itemHeight, wallPaint);
                }
                if (pathStack!=null&&pathStack.contains(mazePositionData[i][j])) {
                    canvas.drawRect(0, 0, itemWidth, itemHeight, pathPaint);
                }
                if (checkPosition != null && checkPosition.row == i && checkPosition.col == j) {
                    canvas.drawRect(0, 0, itemWidth, itemHeight, checkPaint);
                }
                canvas.restore();
            }

        }

    }
}