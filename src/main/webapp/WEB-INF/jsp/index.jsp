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

<br>
a simple messaging broker, with separation of user rights.
<br>
each user has 1 source to receive or send a message.
<br>
receiving messages can be packets of n messages in a packet depending on the configuration.
<br>
access rights:
<br>
admin - create edit users
<br>
user - only send or only receive or both receive and send messages
<br>
<br>

For User<br><br>
* GET    /rest/box               get message package (package size setup in user property)<br>
* POST   /rest/box               put message in the box user<br>
<br><br>
For Admin<br>
* GET    /rest/users                  get all users<br>
* GET    /rest/users/{id}             get user by id<br>
* GET    /rest/users/by?login=login   get user by login<br>
* POST   /rest/users                  create new user<br>
* PUT    /rest/users/{id}             update user by id<br>
* DELETE /rest/users/{id}             update user by id<br>
<br>

Example User json (for Admin access)<br>
{<br>
"login": "login",<br>
"password": "login",<br>
"tablename": "login71", //source for messages<br>
"buchsize": 10,         //mex messages in package for send to user (only user right)<br>
"enabled": true,        //not ready<br>
"subscriber": true,     //for load messages (only user right)<br>
"publisher": true,      //for put messages  (only user right)<br>
"roles": [<br>
"USER", "ADMIN"<br>
]<br>
}<br>
<br>
Example Message json (for User access)<br>
[{"box":"message1"},{"box":"message2"},{"box":"message3"},{"box":"message4"}]<br>

</body>
</html>
