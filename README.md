CFP-Tool
=======

Read call for paper mails and use REST to create new issues in JIRA.
Furthermore this tool will generates tables for confluence.

Usage
=======
1. clone repo
2. mvn clean install on project-root
3. cd ./cfptool-main
4. mvn assembly:assembly on cfptool-main
5. execute cfptool-main/target/ cfptool-main-1.0-SNAPSHOT-jar-with-dependencies.jar
6. first, cfptool will generate a config.properties.
7. fill out the config.properties and start cfptool again.

Config
=======
- emailHost = email host																											| mail.yourdomain.de
- emailUsername = username for your mailaccoun																| test@yourdomain.de
- emailPassword = password for your mailaccoun																| x!c!shj123
- emailReadFolderPath = folder which should be read ou												| cfp/inbox
- emailMoveToFolderPath = after read out, cfptool move mails to this folder		| cfp/old
- moveEmails = if 'true' cfptool will move mails to 'emailMoveToFolderPath'		| true
- defaultIssueProjectId = project-id for new issues														| 1001
- defaultIssueTypeId = issue-type for new issues															| 3
- defaultPriorityId = priority-id for new issues															| 1
- defaultAssigneeName = assignee username																			| myusername
- defaultReporterName = reporter username																			| myusername
- defaultSummary = summarytext																								| default
- defaultDescription = description																						| default
- defaultLabel = label																												| cfp
- jiraUsername = username for your jiraaccount																| myusername
- jiraPassword = password for your jiraaccount																| x!c!shj123
- jiraUrl = url to JIRA																												| https://my-jira.atlassian.net
- jiraRestAPI = path to REST-API																							| /rest/api/2
- speakerTableFileName = filename for tabel with speaker											| speakerTable.txt
- submissionTableFileName = filename for tabel with all submissions						| submissionTable.txt
- writeMode = test run or a normal run? If 'true', cfptool will create issues | true
