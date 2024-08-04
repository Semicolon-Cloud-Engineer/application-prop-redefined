#!/bin/bash

# Define the directory and properties files to compare
DIRECTORY="src/main/resources"
FILES=("application.properties" "application-local.properties" "application-dev.properties" "application-uat.properties" "application-prod.properties")
DIFFERENCES_FOUND=false

# Fetch the target branch (the base branch of the PR)
TARGET_BRANCH=$1

# Compare each properties file with the target branch
for FILE in "${FILES[@]}"; do
  FULL_PATH="$DIRECTORY/$FILE"
  if [[ -f $FULL_PATH ]]; then
    echo "Comparing $FULL_PATH..."
    FILENAME=$(basename $FILE)
    git diff $TARGET_BRANCH -- $FULL_PATH > diff_output_$FILENAME.txt
    if [[ -s diff_output_$FILENAME.txt ]]; then
      echo "Differences found in $FULL_PATH"
      cat diff_output_$FILENAME.txt
      DIFFERENCES_FOUND=true
    else
      echo "No differences found in $FULL_PATH"
      rm diff_output_$FILENAME.txt
    fi
  else
    echo "$FULL_PATH does not exist in the repository."
  fi
done

# Fail the script if differences were found
if [ "$DIFFERENCES_FOUND" = true ]; then
  echo "Differences were found in one or more files."
  exit 1
else
  echo "No differences were found in any files."
fi
