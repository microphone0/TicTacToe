package com.example.tictactoe;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private Button buttonReset;

    private boolean playerAppleTurn = true;

    private int roundCount;

    private int playerApplePoints;
    private int playerBananaPoints;

    private TextView textViewPlayerApple;
    private TextView textViewPlayerBanana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerApple = findViewById(R.id.text_view_Apple);
        textViewPlayerBanana = findViewById(R.id.text_view_Banana);



        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }


        buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (playerAppleTurn) {
            ((Button) v).setText("X");
            v.setBackgroundResource(R.drawable.apple);
        } else {
            ((Button) v).setText("O");
            v.setBackgroundResource(R.drawable.banana);
        }

        roundCount++;

        if (checkForWin()) {
            if (playerAppleTurn) {
                playerAppleWins();
            } else {
                playerBananaWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            playerAppleTurn = !playerAppleTurn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void playerAppleWins() {
        playerApplePoints++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void playerBananaWins() {
        playerBananaPoints++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayerApple.setText("Player 1: " + playerApplePoints);
        textViewPlayerBanana.setText("Player 2: " + playerBananaPoints);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(buttonReset.getBackground());
            }
        }

        roundCount = 0;
        playerAppleTurn = true;
    }

    private void resetGame() {
        playerApplePoints = 0;
        playerBananaPoints = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", playerApplePoints);
        outState.putInt("player2Points", playerBananaPoints);
        outState.putBoolean("player1Turn", playerAppleTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerApplePoints = savedInstanceState.getInt("player1Points");
        playerBananaPoints = savedInstanceState.getInt("player2Points");
        playerAppleTurn = savedInstanceState.getBoolean("player1Turn");
    }
}