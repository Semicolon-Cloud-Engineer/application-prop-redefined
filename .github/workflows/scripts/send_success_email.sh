#!/bin/bash

SMTP_SERVER=$1
SMTP_PORT=$2
SMTP_USERNAME=$3
SMTP_PASSWORD=$4
EMAILS=$5
TAG=$6
BRANCH_NAME=$7
COMMIT_AUTHOR=${10}

IFS=',' read -r -a email_array <<< "${EMAILS}"
for email in "${email_array[@]}"
do
  echo "From: builds@semicolon.africa" > /tmp/email.txt
  echo "To: $email" >> /tmp/email.txt
  echo "Subject: Build Report" >> /tmp/email.txt
  printf "Congratulations, your recent build in Identity Management Backend was successful.\nTAG: ${TAG}" >> /tmp/email.txt
  printf "Branch: ${BRANCH_NAME}\n" >> /tmp/email.txt
  printf "Author: ${COMMIT_AUTHOR}\n" >> /tmp/email.txt
  printf "\nTAG: ${TAG}\n\n" >> /tmp/email.txt
  printf "click on the links below to view your reports\n" >> /tmp/email.txt
  printf "Maven Build Report: https://semicolon-build-reports.s3.eu-west-1.amazonaws.com/karrabo/identity-management/maven-reports/new-reports/index.html\n" >> /tmp/email.txt
  printf "Automation Test Report: https://semicolon-build-reports.s3.eu-west-1.amazonaws.com/karrabo/identity-management/automation-tests-result/report-pytest-results.html\n" >> /tmp/email.txt
  printf "Sonarqube Report: http://sonarqube.enum.africa/dashboard?id=Karrabo-Identity-Management\n" >> /tmp/email.txt
  
  printf "\nRegards,\nThe Cloud Team" >> /tmp/email.txt
  curl --ssl-reqd \
    --url "smtps://${SMTP_SERVER}:${SMTP_PORT}" \
    --mail-from "builds@semicolon.africa" \
    --mail-rcpt "$email" \
    --user "${SMTP_USERNAME}:${SMTP_PASSWORD}" \
    --upload-file /tmp/email.txt
done
