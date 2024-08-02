#!/bin/bash

# Set up git to use the custom template directory
git config --local init.templateDir "$(pwd)/git-template"

# Re-initialize the repository to apply the template
git init

# Run the set-readonly script
./set-readonly.sh

echo "Repository has been set up with custom hooks and read-only properties."