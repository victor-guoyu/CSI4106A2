package main;

public enum CellType {
        EMPTY('E'),
        OBSTACLE('O'),
        HOME('H'),
        BOUNDAY('B'),
        SMILEYFACE('S');

        private char symbol;

        private CellType(char symbol){
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }
}
