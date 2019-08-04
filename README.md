# Employee Tasks

## Overview
An app to coordinate and increase efficiency of work between employees and owners. 
Owners can assign work to their employees with a deadline.

## Key Features:
#### For Owners:
* Give work to a particular person from a pool of employees.
* Monitor employee's accounts.
* Track current location of each and every employee.
### For Employees:
* Register and login.
* Provide a view of work to be completed,
  each with a particular deadline.
* Update each task once it is completed.
* Provide a view of each completed task for 7 days after completion.

## Notes
Uses **Firebase** for backend services.
 * **User Authentication**: Authenticate using email and password 
 * **Realtime Database**: Store tasks, user data, location(latitude and longitude)
 * **Cloud Functions**: Automated notifications
 * **Firebase Cloud Messaging(FCM)**: Send notifications to employees 
 * Uses **Google Maps API**
 
