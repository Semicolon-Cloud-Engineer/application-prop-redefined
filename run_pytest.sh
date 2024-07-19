#!/bin/bash

# Path to your Python project
PYTHON_PROJECT_PATH="https://github.com/Tharolloo/Identity-Management_QA"

# Navigate to the Python project directory
cd "$PYTHON_PROJECT_PATH" || exit

# Create and activate a virtual environment
python3 -m venv venv
source venv/bin/activate

# Install pytest and requirements
pip install pytest
pip install -r requirements.txt

# Run pytest
pytest --junitxml=pytest-results.xml

# Deactivate the virtual environment
deactivate

# Navigate back to the original directory (optional)
cd - || exit
