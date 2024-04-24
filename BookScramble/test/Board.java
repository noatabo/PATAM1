package test;

import java.util.ArrayList;

public class Board {
    private static Board b = null;
    Tile[][] tile_board;
    char[][] score_board;

    private Board() {
        tile_board = new Tile[15][15];
        score_board = new char[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                tile_board[i][j] = null;
                score_board[i][j] = '0';
            }
        }
        // STAR
        score_board[7][7] = 'S';

        // RED- TRIPLE word score
        score_board[0][0] = 'T';
        score_board[0][7] = 'T';
        score_board[0][14] = 'T';
        score_board[7][0] = 'T';
        score_board[7][14] = 'T';
        score_board[14][0] = 'T';
        score_board[14][7] = 'T';
        score_board[14][14] = 'T';

        // BLUE-triple letter letter score
        score_board[1][5] = 't';
        score_board[1][9] = 't';
        score_board[5][1] = 't';
        score_board[5][5] = 't';
        score_board[5][9] = 't';
        score_board[5][13] = 't';
        score_board[9][1] = 't';
        score_board[9][5] = 't';
        score_board[9][9] = 't';
        score_board[9][13] = 't';
        score_board[13][5] = 't';
        score_board[13][9] = 't';

        // LIGHT BLUE-double letter score
        score_board[0][3] = 'd';
        score_board[0][11] = 'd';
        score_board[2][6] = 'd';
        score_board[2][8] = 'd';
        score_board[3][0] = 'd';
        score_board[3][7] = 'd';
        score_board[3][14] = 'd';
        score_board[6][2] = 'd';
        score_board[6][6] = 'd';
        score_board[6][8] = 'd';
        score_board[6][12] = 'd';
        score_board[7][3] = 'd';
        score_board[7][11] = 'd';
        score_board[8][2] = 'd';
        score_board[8][6] = 'd';
        score_board[8][8] = 'd';
        score_board[8][12] = 'd';
        score_board[11][0] = 'd';
        score_board[11][7] = 'd';
        score_board[11][14] = 'd';
        score_board[12][6] = 'd';
        score_board[12][8] = 'd';
        score_board[14][3] = 'd';
        score_board[14][11] = 'd';

        // YELLOW- DOUBLE word score
        score_board[1][1] = 'D';
        score_board[1][13] = 'D';
        score_board[2][2] = 'D';
        score_board[2][12] = 'D';
        score_board[3][3] = 'D';
        score_board[3][11] = 'D';
        score_board[4][4] = 'D';
        score_board[4][10] = 'D';
        score_board[13][1] = 'D';
        score_board[13][13] = 'D';
        score_board[12][2] = 'D';
        score_board[12][12] = 'D';
        score_board[11][3] = 'D';
        score_board[11][11] = 'D';
        score_board[10][4] = 'D';
        score_board[10][10] = 'D';

    }

    public static synchronized Board getBoard() {
        if (b == null) {
            b = new Board();
        }
        return b;
    }

    public Tile[][] getTiles() {
        return tile_board.clone();
    }

    public boolean boardLegal(Word w) {
        if (check_first_word(w)) {
            return (check_possition(w));
        } else
            return (check_possition(w) && check_nearsTile(w) && check_tile_overlap(w));
    }

    public boolean check_possition(Word w) {
        int rowindex = w.getRow();
        int colindex = w.getCol();
        if (w.isVertical() == true) {
            if ((inLimit(rowindex, colindex)) && (inLimit(rowindex + w.getTiles().length, colindex))) {
                return true;
            } else
                return false;
        } else {
            if ((inLimit(rowindex, colindex)) && (inLimit(rowindex, colindex + w.getTiles().length))) {
                return true;
            } else
                return false;
        }
    }

    public boolean check_first_word(Word w) {
        if (this.tile_board[7][7] == null) {
            if ((w.isVertical())) {
                if ((w.getCol() == 7) && (w.getRow() <= 7) && (w.getRow() + w.getTiles().length >= 7)) {
                    return true;
                } else
                    return false;
            } else {
                if ((w.getRow() == 7) && (w.getCol() <= 7) && (w.getCol() + w.getTiles().length >= 7)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean check_nearsTile(Word w) {
        int j = w.getCol();
        int i = w.getRow();

        for (@SuppressWarnings("unused")
        Tile t : w.getTiles()) {
            if (((tile_board[i][j]) != null) || (inLimit(i + 1, j) && (tile_board[i + 1][j] != null))
                    || (inLimit(i - 1, j) && (tile_board[i - 1][j] != null))
                    || (inLimit(i, j + 1) && (tile_board[i][j + 1] != null))
                    || (inLimit(i, j - 1) && (tile_board[i][j - 1] != null))) {
                return true;
            }
            if (w.isVertical()) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }

    public boolean check_tile_overlap(Word w) {
        int j = w.getCol();
        int i = w.getRow();

        for (Tile t : w.getTiles()) {
            if (tile_board[i][j] != null && tile_board[i][j] != t) {
                return false;
            }
            if (w.isVertical()) {
                i++;
            } else {
                j++;
            }

        }
        return true;
    }

    public boolean dictionaryLegal(Word w) {
        return true;
    }

    public int getScore(Word w) {
        int baseScore = 0, wordMultiplier = 1;
        int row = w.getRow(), col = w.getCol();

        for (Tile tile : w.getTiles()) {
            int tileScore = tile.getScore();
            char scoreType = score_board[row][col];
            switch (scoreType) {
                case 'd':
                    tileScore *= 2;
                    break;
                case 't':
                    tileScore *= 3;
                    break;
                case 'D':
                case 'S':
                    wordMultiplier *= 2;
                    break;
                case 'T':
                    wordMultiplier *= 3;
                    break;
            }
            baseScore += tileScore;
            if (w.isVertical())
                row++;
            else
                col++;
        }

        return baseScore * wordMultiplier;
    }

    public boolean inLimit(int row, int col) {
        if (row >= 0 && row < 15 && col >= 0 && col < 15) {
            return true;
        }
        return false;
    }

    public ArrayList<Word> getALLWords(Tile[][] b) {
        ArrayList<Word> words = new ArrayList<Word>();
        for (int i = 0; i < tile_board[0].length; i++) {
            int j = 0;
            while (j < tile_board[i].length) {
                ArrayList<Tile> tls = new ArrayList<>();
                int row = i;
                int col = j;
                while (j < tile_board[i].length && tile_board[i][j] != null) {
                    tls.add(tile_board[i][j]);
                    j++;
                }
                if (tls.size() > 1) {
                    Tile[] newtiles = new Tile[tls.size()];
                    words.add(new Word(tls.toArray(newtiles), row, col, false));// horizontal
                }
                j++;
            }
        }
        for (int j = 0; j < tile_board[0].length; j++) {
            int i = 0;
            while (i < tile_board[j].length) {
                ArrayList<Tile> tls = new ArrayList<>();
                int row = i;
                int col = j;
                while (i < tile_board[i].length && tile_board[i][j] != null) {
                    tls.add(tile_board[i][j]);
                    i++;
                }
                if (tls.size() > 1) {
                    Tile[] newtiles = new Tile[tls.size()];
                    words.add(new Word(tls.toArray(newtiles), row, col, true));// vertical
                }
                i++;
            }
        }
        return words;

    }

    public ArrayList<Word> getWords(Word w) {
        Tile[][] tempBoard = getTiles();
        ArrayList<Word> temp = getALLWords(tempBoard);
        int row = w.getRow();
        int col = w.getCol();
        for (Tile t : w.getTiles()) {
            tempBoard[row][col] = t;
            if (w.isVertical()) {
                row++;
            } else {
                col++;
            }
        }
        Tile[][] aftertiles = getTiles();
        ArrayList<Word> newWords = getALLWords(aftertiles);
        newWords.removeAll(temp);
        return newWords;
    }

    public int tryPlaceWord(Word w) {
        Tile[] tileword = w.getTiles();
        int row = w.getRow();
        int col = w.getCol();
        for (int i = 0; i < tileword.length; i++) {
            if (tileword[i] == null) {
                tileword[i] = tile_board[row][col];
            }
            if (w.isVertical()) {
                row++;
            }
            if (!w.isVertical()) {
                col++;
            }
        }
        Word test = new Word(tileword, w.getRow(), w.getCol(), w.isVertical());

        int sum = 0;
        if (boardLegal(test)) {
            ArrayList<Word> newWords = getWords(test);
            for (Word nw : newWords) {
                if (dictionaryLegal(nw))
                    sum += getScore(nw);
                else
                    return 0;
            }
        }

        // the placement
        row = w.getRow();
        col = w.getCol();
        for (Tile t : w.getTiles()) {
            tile_board[row][col] = t;
            if (w.isVertical())
                row++;
            else
                col++;
        }

        this.score_board[7][7] = '0';
        return sum;
    }

}
