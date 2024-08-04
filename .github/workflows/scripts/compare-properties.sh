#!/bin/bash

# Get the root directory of the git repository
ROOT_DIR=$(git rev-parse --show-toplevel)

# Set the directory containing the properties files
PROPERTIES_DIR="$ROOT_DIR/src/main/resources"

# Define the properties files to compare
FILES=("$PROPERTIES_DIR/application.properties" "$PROPERTIES_DIR/application-local.properties" "$PROPERTIES_DIR/application-dev.properties" "$PROPERTIES_DIR/application-uat.properties" "$PROPERTIES_DIR/application-prod.properties")
DIFFERENCES_FOUND=false

# Fetch the target branch (the base branch of the PR)
TARGET_BRANCH=$1

# Compare each properties file with the target branch
for FILE in "${FILES[@]}"; do
  if [[ -f $FILE ]]; then
    echo "Comparing $FILE..."
    git diff $TARGET_BRANCH -- $FILE > diff_output_$FILE.txt
    if [[ -s diff_output_$FILE.txt ]]; then
      echo "Differences found in $FILE"
      cat diff_output_$FILE.txt
      DIFFERENCES_FOUND=true
    else
      echo "No differences found in $FILE"
      rm diff_output_$FILE.txt
    fi
  else
    echo "$FILE does not exist in the repository."
  fi
done

# Fail the script if differences were found
if [ "$DIFFERENCES_FOUND" = true ]; then
  echo "Differences were found in one or more files."
  exit 1
else
  echo "No differences were found in any files."
fi
