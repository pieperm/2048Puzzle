## Important Usage Information
This submission includes two different heuristics. One of the heuristics is `AStarHeuristic.java` which is a non-admissible
(and thus non-optimal) heuristic that can solve all 5 puzzles efficiently. The second heuristic is `AStarHeuristicAdmissible.java`
which is an admissible heuristic. However, it can only computationally handle puzzle inputs 1 and 2.

The `admissible-puzzles` directory holds puzzle inputs and solutions when specifying the admissible heuristic as follows:
`./run.sh puzzle1.txt 1`

The `nonadmissible-puzzles` directory holds puzzle inputs and solutions when specifying the non-admissible heuristic as follows:
`./run.sh puzzle1.txt 0` or `./run.sh puzzle1.txt`


## Repository Information
Please find information on the puzzle assignment set here: https://mst.instructure.com/files/1951266/download?download_frd=1
You have been provided a sample bash script ("run.sh"); however, you will have to modify this script to suit your needs. 

We have also included a sample .gitignore, because it is bad practice to include executables (such as HelloAI) in your git repo.

Do not forget to add, commit, and push your submission files.

git add FILENAME
git commit -m "meaningfull commit message"
git push

You will also find a file call "ReadyToSubmit.txt", when you are reayd to submit your assignment you must change the contents of this file to "Yes". This is how you will indicate to the graders that your assingment is ready to be graded. 

If you have any difficulties, please contact your TAs, who are always happy to help.

