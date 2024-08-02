#!/bin/bash

# Get the root directory of the git repository
ROOT_DIR=$(git rev-parse --show-toplevel)

# Run the set-readonly script
"$ROOT_DIR/set-readonly.sh"