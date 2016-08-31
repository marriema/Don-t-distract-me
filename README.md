# Don-t-distract-me

Introduction:
This is a  a time-management Android mobile application that allows users to set a period time for study and block users’ own access to distracting applications until study time ends and alarm clock is triggered
It enable students to concentrate fully on studies and avoid distractions from social networking applications.

The user will first type in his/her desired studying time and studying subject, also his nickname that will be used in the app.
When the user starts studying, there is a countdown clock on the screen to show remaining study time. However, the most important feature of this app
is its force close function. That means, if the user is distracted by social media like Facebook, Twitter in the middle of study, the facebook page he opens will
be immediately closed and his study will be ended. 

The backend service of app will continuously check background apps like acitivity motinor. Any time it find that a outside app like Facebook and Twitter
is opened during study time, it will end that app process immediately and return to our own app's page which shows "You are being distracted. You have been 
studying XX minutes". 
