package com.example.alexandresilva.slidingpuzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int COLUMNS = 4;
    private static final int DIMENSION = COLUMNS * COLUMNS;

    private static String[] blockList;

    private static GestureDetectGridView puzzleGridView;

    private static TextView LabelInformation;
    private static Button BtnSolve;
    private static Button BtnStart;

    private static int puzzleColumnWidth;
    private static int puzzleColumnHeight;

    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";

    private static int MoveCount = 0;

    private static int BlankPosition = 15;


    // define instance variables that should be saved
    private static String InstBlockList = "";

    // define the SharedPreferences object
    private SharedPreferences savedValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setDimensions();

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }

    @Override
    public void onPause() {
        StringBuilder StrBlockList = new StringBuilder();
        // save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putInt("MoveCount", MoveCount);
        editor.putInt("BlankPosition", BlankPosition);
        for(String block:blockList){
            StrBlockList.append(block);
            StrBlockList.append(",");
        }
        InstBlockList = StrBlockList.toString();
        editor.putString("BlockList", InstBlockList);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        MoveCount = savedValues.getInt("MoveCount", 0);
        BlankPosition = savedValues.getInt("BlankPosition", 15);
        InstBlockList = savedValues.getString("BlockList", "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15");

        blockList = InstBlockList.split(",");

        // set the Label with game information
        LabelInformation.setText("Moves so far: " + Integer.toString(MoveCount));

        // display the board
        display(getApplicationContext());
    }

    private void setDimensions() {
        ViewTreeObserver vto = puzzleGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void  onGlobalLayout() {
                puzzleGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int displayWidth = puzzleGridView.getMeasuredWidth();
                int displayHeight = puzzleGridView.getMeasuredHeight();

                int statusBarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusBarHeight;

                puzzleColumnWidth = displayWidth / COLUMNS;
                puzzleColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());
            }
        });
    }

    private int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static void display(Context context) {
        ArrayList<ImageButton> buttons = new ArrayList<>();

        ImageButton button;

        for (int i = 0; i < blockList.length; i++) {
            button = new ImageButton(context);

            button.setImageResource(R.drawable.imageview);
            button.setPadding(0,0,0,0);

            if (blockList[i].equals("0")) {
                button.setBackgroundResource(R.drawable.img1);
            }
            else if (blockList[i].equals("1")) {
                button.setBackgroundResource(R.drawable.img2);
            }
            else if (blockList[i].equals("2")) {
                button.setBackgroundResource(R.drawable.img3);
            }
            else if (blockList[i].equals("3")) {
                button.setBackgroundResource(R.drawable.img4);
            }
            else if (blockList[i].equals("4")) {
                button.setBackgroundResource(R.drawable.img5);
            }
            else if (blockList[i].equals("5")) {
                button.setBackgroundResource(R.drawable.img6);
            }
            else if (blockList[i].equals("6")) {
                button.setBackgroundResource(R.drawable.img7);
            }
            else if (blockList[i].equals("7")) {
                button.setBackgroundResource(R.drawable.img8);
            }
            else if (blockList[i].equals("8")) {
                button.setBackgroundResource(R.drawable.img9);
            }
            else if (blockList[i].equals("9")) {
                button.setBackgroundResource(R.drawable.img10);
            }
            else if (blockList[i].equals("10")) {
                button.setBackgroundResource(R.drawable.img11);
            }
            else if (blockList[i].equals("11")) {
                button.setBackgroundResource(R.drawable.img12);
            }
            else if (blockList[i].equals("12")) {
                button.setBackgroundResource(R.drawable.img13);
            }
            else if (blockList[i].equals("13")) {
                button.setBackgroundResource(R.drawable.img14);
            }
            else if (blockList[i].equals("14")) {
                button.setBackgroundResource(R.drawable.img15);
            }
            else if (blockList[i].equals("15")) {
                button.setBackgroundResource(R.drawable.blank);
            }
            buttons.add(button);
        }

        puzzleGridView.setAdapter(new CustomAdapter(buttons, puzzleColumnWidth, puzzleColumnHeight));

    }

    private void solveGame() {
        MoveCount = 0;
        BlankPosition = 15;
        LabelInformation.setText("Moves so far: " + Integer.toString(MoveCount));
        for (int i = 0; i < DIMENSION; i++) {
            blockList[i] = String.valueOf(i);
        }
        display(getApplicationContext());
    }

    private void scramble() {
        MoveCount = 0;
        BlankPosition = 15;
        LabelInformation.setText("Moves so far: " + Integer.toString(MoveCount));
        int index;
        String temp;
        Random random = new Random();

        for (int i = blockList.length - 2; i > 0; i--) {
            index = random.nextInt( i + 1 );
            temp = blockList[index];
            blockList[index] = blockList[i];
            blockList[i] = temp;
        }
        display(getApplicationContext());
    }

    private void init() {
        LabelInformation = (TextView) findViewById(R.id.LabelInformation);
        BtnSolve = (Button) findViewById(R.id.btnSolve);
        // Set a click listener for BtnSolve
        BtnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solveGame();
            }
        });

        BtnStart = (Button) findViewById(R.id.btnStartGame);
        // Set a click listener for BtnStart
        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scramble();
            }
        });

        puzzleGridView = (GestureDetectGridView) findViewById(R.id.grid);

        puzzleGridView.setNumColumns(COLUMNS);

        blockList = new String[DIMENSION];

        for (int i = 0; i < DIMENSION; i++) {
            blockList[i] = String.valueOf(i);
        }
    }

    private static void SwapTiles(Context context, int currentPosition, int swap) {
        BlankPosition = currentPosition;
        String newPosition = blockList[currentPosition + swap];
        blockList[currentPosition + swap] = blockList[currentPosition];
        blockList[currentPosition] = newPosition;
        display(context);

        MoveCount++;

        LabelInformation.setText("Moves so far: " + Integer.toString(MoveCount));

        if (isSolved()) Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
    }

    public static void CalculateMoves(Context context, String direction, int position) {

        if (position == BlankPosition || isSolved()) {
            Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
        } else {
            switch (position) {
                case 0:
                    if (direction.equals(right) && BlankPosition == 1) SwapTiles(context, position, 1);
                    else if (direction.equals(down) && BlankPosition == 4)
                        SwapTiles(context, position, COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    if (direction.equals(right) && BlankPosition == 2) SwapTiles(context, position, 1);
                    else if (direction.equals(left) && BlankPosition == 0)
                        SwapTiles(context, position, -1);
                    else if (direction.equals(down) && BlankPosition == 5)
                        SwapTiles(context, position, COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    if (direction.equals(right) && BlankPosition == 3) SwapTiles(context, position, 1);
                    else if (direction.equals(left) && BlankPosition == 1)
                        SwapTiles(context, position, -1);
                    else if (direction.equals(down) && BlankPosition == 6)
                        SwapTiles(context, position, COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    if (direction.equals(left) && BlankPosition == 2) SwapTiles(context, position, -1);
                    else if (direction.equals(down) && BlankPosition == 7)
                        SwapTiles(context, position, COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    if (direction.equals(right) && BlankPosition == 5) SwapTiles(context, position, 1);
                    else if (direction.equals(down) && BlankPosition == 8)
                        SwapTiles(context, position, COLUMNS);
                    else if (direction.equals(up) && BlankPosition == 0)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    if (direction.equals(right) && BlankPosition == 6) SwapTiles(context, position, 1);
                    else if (direction.equals(left) && BlankPosition == 4)
                        SwapTiles(context, position, -1);
                    else if (direction.equals(down) && BlankPosition == 9)
                        SwapTiles(context, position, COLUMNS);
                    else if (direction.equals(up) && BlankPosition == 1)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    if (direction.equals(right) && BlankPosition == 7) SwapTiles(context, position, 1);
                    else if (direction.equals(left) && BlankPosition == 5)
                        SwapTiles(context, position, -1);
                    else if (direction.equals(down) && BlankPosition == 10)
                        SwapTiles(context, position, COLUMNS);
                    else if (direction.equals(up) && BlankPosition == 2)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    if (direction.equals(left) && BlankPosition == 6) SwapTiles(context, position, -1);
                    else if (direction.equals(down) && BlankPosition == 11)
                        SwapTiles(context, position, COLUMNS);
                    else if (direction.equals(up) && BlankPosition == 3)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    if (direction.equals(right) && BlankPosition == 9) SwapTiles(context, position, 1);
                    else if (direction.equals(down) && BlankPosition == 12)
                        SwapTiles(context, position, COLUMNS);
                    else if (direction.equals(up) && BlankPosition == 4)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 9:
                    if (direction.equals(right) && BlankPosition == 10) SwapTiles(context, position, 1);
                    else if (direction.equals(left) && BlankPosition == 8)
                        SwapTiles(context, position, -1);
                    else if (direction.equals(down) && BlankPosition == 13)
                        SwapTiles(context, position, COLUMNS);
                    else if (direction.equals(up) && BlankPosition == 5)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 10:
                    if (direction.equals(right) && BlankPosition == 11) SwapTiles(context, position, 1);
                    else if (direction.equals(left) && BlankPosition == 9)
                        SwapTiles(context, position, -1);
                    else if (direction.equals(down) && BlankPosition == 14)
                        SwapTiles(context, position, COLUMNS);
                    else if (direction.equals(up) && BlankPosition == 6)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    if (direction.equals(left) && BlankPosition == 10) SwapTiles(context, position, -1);
                    else if (direction.equals(down) && BlankPosition == 15)
                        SwapTiles(context, position, COLUMNS);
                    else if (direction.equals(up) && BlankPosition == 7)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    if (direction.equals(right) && BlankPosition == 13) SwapTiles(context, position, 1);
                    else if (direction.equals(up) && BlankPosition == 8)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 13:
                    if (direction.equals(left) && BlankPosition == 12) SwapTiles(context, position, -1);
                    else if (direction.equals(right) && BlankPosition == 14)
                        SwapTiles(context, position, 1);
                    else if (direction.equals(up) && BlankPosition == 9)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 14:
                    if (direction.equals(left) && BlankPosition == 13) SwapTiles(context, position, -1);
                    else if (direction.equals(right) && BlankPosition == 15)
                        SwapTiles(context, position, 1);
                    else if (direction.equals(up) && BlankPosition == 10)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
                case 15:
                    if (direction.equals(left) && BlankPosition == 14) SwapTiles(context, position, -1);
                    else if (direction.equals(up) && BlankPosition == 11)
                        SwapTiles(context, position, -COLUMNS);
                    else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < blockList.length; i++) {
            if (blockList[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }

        return solved;
    }
}
