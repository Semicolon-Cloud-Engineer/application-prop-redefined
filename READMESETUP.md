## Repository Setup

After cloning the repository, run the following command to set up the project:

./setup-repo.sh

This will:
- Set up Git hooks to automatically make properties files read-only
- Initialize the repository with the custom hooks
- Set all application*.properties files to read-only

## Configuration

- All configuration files are managed by administrators.
- Local developers cannot modify any application*.properties files.
- For any configuration changes, please contact an administrator.
- The read-only status of properties files is automatically enforced after pulling changes.