package test;

import java.util.Random;

public class Tile {
    protected final char letter;
    protected final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    public char getLetter() {
        return this.letter;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + letter;
        result = prime * result + score;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (letter != other.letter)
            return false;
        if (score != other.score)
            return false;
        return true;
    }

    public static class Bag {
        private static Bag instance = null; // Singleton instance, initially null
        private int[] frequency_array;
        private final int[] check_frequency_array;
        private final Tile[] tiles_array;

        private Bag() {
            frequency_array = new int[] {
                    9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2,
                    1 };
            check_frequency_array = new int[] {
                    9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1,
                    2, 1 };
            tiles_array = new Tile[] {
                    new Tile('A', 1), new Tile('B', 3), new Tile('C', 3), new Tile('D', 2),
                    new Tile('E', 1),
                    new Tile('F', 4), new Tile('G', 2), new Tile('H', 4), new Tile('I', 1), new Tile('J', 8),
                    new Tile('K', 5), new Tile('L', 1), new Tile('M', 3), new Tile('N', 1), new Tile('O', 1),
                    new Tile('P', 3), new Tile('Q', 10), new Tile('R', 1), new Tile('S', 1), new Tile('T', 1),
                    new Tile('U', 1), new Tile('V', 4), new Tile('W', 4), new Tile('X', 8), new Tile('Y', 4),
                    new Tile('Z', 10) };
        }

        // Public method to get the single instance of Bag
        public static Bag getBag() {
            if (instance == null) {
                instance = new Bag();
            }
            return instance;
        }

        public Tile getRand() {
            Random r = new Random();
            int random_tile_index;
            // loop that check if tje bag isn't empty
            for (int i = 0; i <= frequency_array.length; i++) {
                if (frequency_array[i] != 0) {
                    break;
                }
            }

            do {
                random_tile_index = r.nextInt(frequency_array.length);
            } while (frequency_array[random_tile_index] == 0);

            frequency_array[random_tile_index] = frequency_array[random_tile_index] - 1;
            return tiles_array[random_tile_index];
        }

        public Tile getTile(char c) {
            if (c >= 'A' && c <= 'Z') {
                int index = (int) (c - 'A');
                if (frequency_array[index] > 0) {
                    frequency_array[index] = frequency_array[index] - 1;
                    return tiles_array[index];
                }
            }
            return null;
        }

        public void put(Tile t) {
            int index = (int) (t.getLetter() - 'A');
            if (frequency_array[index] + 1 <= check_frequency_array[index]) {
                frequency_array[index] = frequency_array[index] + 1;
            }
        }

        public int size() {
            int sum = 0;
            for (int i = 0; i <= frequency_array.length; i++) {
                sum = sum + frequency_array[i];
            }
            return sum;
        }

        public int[] getQuantities() {
            return frequency_array.clone();
        }
    }
}
