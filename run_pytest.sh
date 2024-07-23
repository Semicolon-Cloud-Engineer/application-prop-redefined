#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Clone the target repository
git clone https://${REPO_PAT}@github.com/Tharolloo/Identity-Management_QA.git

# Navigate to the cloned repository
ls
cd Identity-Management_QA
git checkout dev

# Create and activate a virtual environment
python3 -m venv venv
source venv/bin/activate

# Install pytest and other requirements
pip install pytest
pip install -r requirements.txt

# Run the tests
pytest --junitxml=pytest-results.xml

# Deactivate the virtual environment
deactivate

# Navigate back to the original directory
cd ..

# Clean up (optional, remove if you want to keep the cloned repo)
rm -rf Identity-Management_QA
