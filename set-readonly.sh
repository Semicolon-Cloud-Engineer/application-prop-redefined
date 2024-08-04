#!/bin/bash

# Get the root directory of the git repository
ROOT_DIR=$(git rev-parse --show-toplevel)

# Set the directory containing the properties files
PROPERTIES_DIR="$ROOT_DIR/src/main/resources"

# Make all application*.properties files read-only
find "$PROPERTIES_DIR" -name "application*.properties" -type f -exec chmod 444 {} +

echo "All application*.properties files have been set to read-only."