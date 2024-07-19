#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Clone the target repository
git clone https://${REPO_PAT}@github.com/Tharolloo/Identity_Management_ServiceQA.git

# Navigate to the cloned repository
ls
cd ~/Identity_Management_Service_QA

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
