# WalkMaze
可自定义的可视化求解迷宫的算法演示demo

# 序言
写了一个项目，用来演示走迷宫算法。红色表示正在检查的点，绿色表示已经记录的路径。遇到一个没有走过的点，会依次从右，下，左，上。进行尝试。如果都不能成功，会回退一步。效果如下。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210511150049463.gif#pic_center)

该项目地图支持自定义
在raw 目录下有一个maze.txt 文件，其中1 表示墙壁，0表示可以走的地方。求解的起点是左上角，终点是右下角。地图的大小可以扩展。自带的是11行10列的。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210511150330569.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIyNzA2NTE1,size_16,color_FFFFFF,t_70)

# 算法
核心代码如下，本质上就是深度优先算法。
```java
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
```

# 项目地址

> [WalkMaze](https://github.com/zhuguohui/WalkMaze)
