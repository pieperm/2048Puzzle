#!/bin/bash

FILE1=$1
SOL="${FILE1%.*}.solution.txt"

javac PuzzleTester.java
java PuzzleTester < "$FILE1" > "$SOL"
