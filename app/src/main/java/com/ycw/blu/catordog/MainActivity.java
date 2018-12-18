package com.ycw.blu.catordog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String winner;
    enum Player {
        ONE, TWO, No
    }

    Player currentPlayer = Player.ONE;
    Player[] playerChoices = new Player[9];
    int [][] winnerRowsColumns = {
            {0,1,2}, {3,4,5}, {6,7,8}, // row
            {0,3,6}, {1,4,7}, {2,5,8}, // column
            {0,4,8}, {2,4,6} // diagonal
    };

    private boolean gameOver = false;
    private android.support.v7.widget.GridLayout gridLayout;
    private Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < playerChoices.length; i++) {
            playerChoices[i] = Player.No;
        }

        resetBtn = findViewById(R.id.btnReset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame ();
            }
        });

        gridLayout = findViewById(R.id.gridLayout);
    }

    public void imageViewapped (View imageView) {
        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());
        if (playerChoices[tiTag] == Player.No && gameOver == false) {
            tappedImageView.setTranslationX(-2000);

            if (currentPlayer == Player.ONE) {
                playerChoices[tiTag] = currentPlayer;
                tappedImageView.setImageResource(R.drawable.dog);
                currentPlayer = Player.TWO;
            }
            else if (currentPlayer == Player.TWO) {
                playerChoices[tiTag] = currentPlayer;
                tappedImageView.setImageResource(R.drawable.cat);
                currentPlayer = Player.ONE;
            }

            tappedImageView.animate().translationXBy(2000).alpha(1f).rotation(3600).setDuration(500);

//        Toast.makeText(this, ""+tappedImageView.getTag().toString(), Toast.LENGTH_SHORT).show();

            for (int[] winnerPatterns : winnerRowsColumns ) {
                if (playerChoices[winnerPatterns[0]] == playerChoices[winnerPatterns[1]] &&
                        playerChoices[winnerPatterns[1]] == playerChoices[winnerPatterns[2]] &&
                        playerChoices[winnerPatterns[0]] != Player.No) {

                    resetBtn.setVisibility(View.VISIBLE);
                    gameOver = true;

                    winner = "Player 1!";
                    if (currentPlayer == Player.ONE) {
                        winner = "Player 2!";
                    }
                    Toast.makeText(this, "Winner is " + winner, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // reset game
    private void resetGame () {

        for (int index = 0; index < gridLayout.getChildCount(); index++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }

        // reset environment
        currentPlayer = Player.ONE;
        for (int i = 0; i < playerChoices.length; i++) {
            playerChoices[i] = Player.No;
        }
        gameOver = false;

        resetBtn.setVisibility(View.GONE);
    }
}
