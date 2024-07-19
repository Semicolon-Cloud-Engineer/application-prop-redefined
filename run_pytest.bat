@echo off

rem Path to your Python project
set PYTHON_PROJECT_PATH=https://github.com/Tharolloo/Identity-Management_QA

rem Navigate to the Python project directory
cd /d "%PYTHON_PROJECT_PATH%"

rem Activate the virtual environment
call venv\Scripts\activate.bat

pip install pytest

rem Install requirements
pip install -r requirements.txt

rem Run pytest
call pytest --junitxml=pytest-results.xml

rem Deactivate the virtual environment
call venv\Scripts\deactivate.bat

rem Navigate back to the original directory (optional)
cd /d "%~dp0"