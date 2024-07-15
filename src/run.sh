#~\bin\bash
# Usage: second argument should be 0 or nothing for non-admissible heuristic
# second argument should be 1 for admissible heuristic

FILE1=$1
SOL="${FILE1%.*}.solution.txt"

HEURISTIC=$2

javac MainPuzzle.java
java MainPuzzle "$HEURISTIC" < "$FILE1" > "$SOL"
cat "$SOL"
