<%--
  Created by IntelliJ IDEA.
  User: Alexandr.Yakubov
  Date: 14.03.2021
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SMB</title>
</head>
<body>
<h3>REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend</h3>

a simple messaging broker, with separation of user rights.
each user has 1 source to receive or send a message.
receiving messages can be packets of n messages in a packet depending on the configuration.
access rights:
admin - create edit users
user - only send or only receive or both receive and send messages

For User
* GET    /rest/box               get message package (package size setup in user property)
* POST   /rest/box               put message in the box user

For Admin
* GET    /rest/users                  get all users
* GET    /rest/users/{id}             get user by id
* GET    /rest/users/by?login=login   get user by login
* POST   /rest/users                  create new user
* PUT    /rest/users/{id}             update user by id
* DELETE /rest/users/{id}             update user by id


Example User json (for Admin access)
{
"login": "login",
"password": "login",
"tablename": "login71", //source for messages
"buchsize": 10,         //mex messages in package for send to user (only user right)
"enabled": true,        //not ready
"subscriber": true,     //for load messages (only user right)
"publisher": true,      //for put messages  (only user right)
"roles": [
"USER", "ADMIN"
]
}

Example Message json (for User access)
[{"box":"message1"},{"box":"message2"},{"box":"message3"},{"box":"message4"}]

</body>
</html>
