#~\bin\bash

FILE1=$1
SOL="${FILE1%.*}.solution.txt"

HEURISTIC=$2

javac MainPuzzle.java
java MainPuzzle "$HEURISTIC" < "$FILE1" > "$SOL"
cat "$SOL"
