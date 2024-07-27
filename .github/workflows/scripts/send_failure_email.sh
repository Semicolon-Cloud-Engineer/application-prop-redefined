#!/bin/bash
SMTP_SERVER=$1
SMTP_PORT=$2
SMTP_USERNAME=$3
SMTP_PASSWORD=$4
EMAILS=$5
TAG=$6
BRANCH_NAME=$7
COMMIT_AUTHOR=${8}
SONARQUBE_URL=http://sonarqube.enum.africa/dashboard?id=Karrabo-Identity-Management
MAVEN_REPORT_URL=https://semicolon-build-reports.s3.eu-west-1.amazonaws.com/karrabo/identity-management/maven-reports/new-reports/surefire-report.html
AUTOMATION_TEST_URL=https://semicolon-build-reports.s3.eu-west-1.amazonaws.com/karrabo/identity-management/automation-tests-result/report-pytest-results.html
      
SONARQUBE_URL_SET=${9}
MAVEN_REPORT_URL_SET=${10}
AUTOMATION_TEST_URL_SET=${11}

IFS=',' read -r -a email_array <<< "${EMAILS}"
for email in "${email_array[@]}"
do
  echo "From: builds@semicolon.africa" > /tmp/email.txt
  echo "To: $email" >> /tmp/email.txt
  echo "Subject: Build Failure" >> /tmp/email.txt
  printf "Oooops, Your recent build in Identity Management Backend was unsuccessful, kindly check the new commit and fix.\n\n" >> /tmp/email.txt
  printf "Branch: ${BRANCH_NAME}\n" >> /tmp/email.txt
  printf "Author: ${COMMIT_AUTHOR}\n" >> /tmp/email.txt
  printf "\nTAG: ${TAG}\n\n" >> /tmp/email.txt
  printf "Click on the links below to view your reports\n" >> /tmp/email.txt
  
  if [ "$SONARQUBE_URL_SET" = "true" ]; then
    printf "\nBuild and Analyze Job: $SONARQUBE_URL\n" >> /tmp/email.txt
  fi
  if [ "$MAVEN_REPORT_URL_SET" = "true" ]; then
    printf "\nDeploy and Run Job: $MAVEN_REPORT_URL\n" >> /tmp/email.txt
  fi
  if [ "$AUTOMATION_TEST_URL_SET" = "true" ]; then
    printf "\nAutomation Test Job: $AUTOMATION_TEST_URL\n" >> /tmp/email.txt
  fi
  
  printf "\n\nRegards,\nThe Cloud Team" >> /tmp/email.txt
  curl --ssl-reqd \
    --url "smtps://${SMTP_SERVER}:${SMTP_PORT}" \
    --mail-from "builds@semicolon.africa" \
    --mail-rcpt "$email" \
    --user "${SMTP_USERNAME}:${SMTP_PASSWORD}" \
    --upload-file /tmp/email.txt
done
