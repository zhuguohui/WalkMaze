package com.example.walkmaze;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    int[][] mazeData;
    private MazeView mazeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mazeView = findViewById(R.id.mazeView);
        mazeData = getMazeData();
        mazeView.setMazeData(mazeData);
        btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setText("正在求解");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getPath();
                    }
                }).start();
                btnStart.setText("求解完成");
            }
        });
    }


    private void getPath() {
        Stack<com.example.walkmaze.Position> stack = new Stack<>();

        int row = mazeData.length;
        int col = mazeData[0].length;
        boolean[][] checkMap = new boolean[row][col];
        int[][] pathData = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                checkMap[i][j] = false;
            }
        }

        stack.add(new Position(0, 0));
        checkMap[0][0] = true;
        DirectionHelper dh = new DirectionHelper(row, col);
        while (!stack.isEmpty()) {
            //当前的位置
            Position cp = stack.peek();
            Position nextPosition = null;
            //检查右边是可以通行
            Position[] ps = dh.getDirections(cp);
            boolean canGo = false;
            for (int i = 0; i < ps.length; i++) {
                Position p = ps[i];
                runOnUiThread(() -> {
                    //更新正在检查的点
                    mazeView.setCheckPosition(p);
                });
                if (p != null && !checkMap[p.row][p.col] && mazeData[p.row][p.col] == 0) {
                    //可以通行
                    checkMap[p.row][p.col] = true;//表示已经访问过
                    stack.push(p);
                    canGo = true;
                    nextPosition = p;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            if (!canGo) {
                //后退一步
                stack.pop();
            } else {
                //判断是否到达终点
                if (nextPosition.row == row - 1 && nextPosition.col == col - 1) {
                    break;
                }
            }
            //更新UI
            runOnUiThread(() -> {
                mazeView.setPathData(stack);
            });
        }


        runOnUiThread(() -> {
            String result = "求解成功";
            if (stack.isEmpty()) {
                result = "没有出路";
            }
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        });


    }

    private int[][] getMazeData() {
        InputStream inputStream = getResources().openRawResource(R.raw.maze);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String str = baos.toString();
        String[] lines = str.split("\r\n");
        int row = lines.length;
        int col = -1;
        int[][] mazeData = null;
        for (int i = 0; i < lines.length; i++) {
            String[] rows = lines[i].split(" ");
            if (col == -1) {
                col = rows.length;
                mazeData = new int[row][col];
            }
            for (int j = 0; j < rows.length; j++) {
                int number = Integer.parseInt(rows[j]);
                mazeData[i][j] = number;
            }
        }

        return mazeData;

    }
}