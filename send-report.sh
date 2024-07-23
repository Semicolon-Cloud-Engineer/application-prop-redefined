#!/bin/bash

REPORT_DIR=target/surefire-reports
LOCAL_URL=http://localhost:8095/report
HOSTED_URL=https://api-identity-management-karrabo.enum.africa/report

# Determine the target URL based on the environment
if [ "$GITHUB_ACTIONS" = "true" ]; then
    TARGET_URL=$HOSTED_URL
else
    TARGET_URL=$LOCAL_URL
fi

# Send the reports
for REPORT_FILE in $REPORT_DIR/TEST-*.xml; do
    if [ -f "$REPORT_FILE" ]; then
        echo "Sending report $REPORT_FILE to $TARGET_URL"
        curl -X POST -H "Content-Type: text/xml" --data-binary @$REPORT_FILE $TARGET_URL
    else
        echo "No report file found at $REPORT_FILE"
    fi
done
