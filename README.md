# SSO (Single sign-out)
This project is an external module of laravel-jwt-idp for signle sign-out of applications that use the IDP.
The module is written in Java.
The SSO is based on the Publish-Subscribe concept:

Applications that use the IDP are subscribers, in fact when a user wants to logout the application send a logout-message to a queue.
A receiver reads the message and deletes the sessions of the user in the other applications.
