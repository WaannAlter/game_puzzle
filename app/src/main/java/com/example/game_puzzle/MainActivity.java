package com.example.game_puzzle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private List<Button> buttons;
    private Button emptyBlock;
    private final int gridSize = 4; // Ukuran grid (misalnya 4x4)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        emptyBlock = findViewById(R.id.emptyBlock);

        buttons = new ArrayList<>();
        for (int i = 1; i <= gridSize * gridSize - 1; i++) {
            int resID = getResources().getIdentifier("block" + i, "id", getPackageName());
            Button block = findViewById(resID);
            buttons.add(block);
            block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBlockClick(block);
                }
            });
        }

        // Set listener untuk tombol Acak
        Button buttonRandomize = findViewById(R.id.buttonRandomize);
        buttonRandomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleBlocks();
            }
        });
    }

    // Implementasi logika ketika tombol blok di klik di sini
    private void onBlockClick(Button block) {
        // Temukan indeks blok yang diklik dalam daftar tombol
        int clickedIndex = buttons.indexOf(block);

        // Temukan indeks blok kosong dalam daftar tombol
        int emptyIndex = buttons.indexOf(emptyBlock);

        // Periksa apakah blok yang diklik berada di sebelah blok kosong
        if (isAdjacent(clickedIndex, emptyIndex)) {
            // Tukar teks dan tombol antara blok yang diklik dan blok kosong
            CharSequence clickedText = block.getText();
            block.setText(emptyBlock.getText());
            emptyBlock.setText(clickedText);

            // Blok kosong sekarang menjadi blok yang diklik
            emptyBlock = block;
        }
    }

    // Implementasi logika untuk mengacak blok-blok
    private void shuffleBlocks() {
        Collections.shuffle(buttons);

        // Perbarui tampilan grid dengan urutan blok yang sudah diacak
        gridLayout.removeAllViews();
        for (Button button : buttons) {
            gridLayout.addView(button);
        }
    }

    // Periksa apakah dua blok bersebelahan
    private boolean isAdjacent(int index1, int index2) {
        int row1 = index1 / gridSize;
        int col1 = index1 % gridSize;
        int row2 = index2 / gridSize;
        int col2 = index2 % gridSize;

        return (Math.abs(row1 - row2) == 1 && col1 == col2) || (Math.abs(col1 - col2) == 1 && row1 == row2);
    }
}
