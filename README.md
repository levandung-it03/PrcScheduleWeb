## Commit Syntax Requirement
**git commit -m "[All Upper-case Letters In Message] by [yournamelikethis_ex_levandung]"**

## TODO Notes

**1. @Transaction with Services**

**2. Teacher List**

**3. All about Practice Schedule (Add, Show, Update, Delete)**

**4. Converting docs ActionMethod**

**5. Adding Whitelist Form (Manager, Teacher)**

**6. Adding mock data form**
  - Student
  - Department
  - Grade
  - Semester
  - Subject
  - SubjectDetail
  - SubjectSchedule
  - SubjectRegistration

## How Our Pages Work:
*Original Account*
- Username: manager0@ptithcm.edu.vn
- Password: 111222333

*General Forms*
- Validate under Javascript site.
- Validate from Java (Customized Modules with Spring Security) site.

*1. Login Page*
- Access LoginPage from Unauthorized Request (main-logic):
  ```Http
  GET /service/v1/auth/authenticate
  If: InstitueEmail not found.
  If: Password is invalid.
  If: Password is not true.
  GET [Redirect] /public/login?errorMessage=<err_code>
  
  Else: All condition is valid.
  GET [Redirect] [Role] /home
  ```
- Access LoginPage from Authorized Request:
  ```Http
  GET /public/login
  If: With Cookies.AccessToken
  GET [Redirect] [Role] /home
  ```
- Access Authorized Request:
  ```Http
  GET /teacher/, /manager/
  POST /teacher/, /manager/
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
    
  If: AccessToken has wrong Role
  Return: HTTPStatus[403 - Forbidden]
  ```
*2. Add Teacher Account Page*
- Main Add Account Page:
  ```Http
  GET [MANAGER] /manager/category/teacher/add-teacher-account
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  ```
- Add New Teacher Account(DtoRegisterAccount) Action:
  ```Http
  POST [MANAGER] /service/v1/manager/add-teacher-account
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: Email is invalid (eMv1at01)
  If: Password is invalid or RetypePassword and Passowrd is not similar (eMv1at02)
  If: Email is already existing (eMv1at03)
  GET [Redirect] [MANAGER] /manager/category/teacher/add-teacher-account?errorMessage=<err_code>

  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/teacher/add-teacher-account?succeedMessage=sMv1at01
  ```

*3. Computer Room Pages*
- Add Computer Room Page (on Category):
  - Main Add Computer Room Page:
  ```Http
  GET [MANAGER] /manager/category/computer-room/add-computer-room
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  ```
  - Add Computer Room Action:
  ```Http
  POST [MANAGER] /service/v1/manager/add-computer-room
  If: Data is invalid
  Return: HTTPStatus[400 - Not Found]
  
  POST [MANAGER] /service/v1/manager/add-computer-room
  If: Computer Room is already exsisting (eMv1at04)
  GET [Redirect] [MANAGER] /manager/category/computer-room/add-computer-room?errorMessage=<errorMessage>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/computer-room/add-computer-room?succeedMessage=sMv1at01
  ```
- Computer Room List Page (on Category):
  - Main List Page:
  ```Http
  GET /manager/category/computer-room/computer-room-list
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  ```
  - Update Computer Room Page:
  ```Http
  GET /manager/sub-page/computer-room/update-computer-room?roomId=<roomId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <roomId> not found
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?errorMessage=eMv1at05
  ```
  - Update Computer Room Action:
  ```Http
  POST /service/v1/manager/update-computer-room?roomId=<roomId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <roomId> not found
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?errorMessage=eMv1at05
  
  If: There's an error in application
  GET [Redirect] [MANAGER] /manager/sub-page/computer-room/update-computer-room?roomId=<roomId>&errorMessage=eMv1at00
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?succeedMessage=sMv1at03
  ```
  - Delete Computer Room Action:
  ```Http
  POST /service/v1/manager/computer-room-list-active-btn?deleteBtn=<roomId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <roomId> not found
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?errorMessage=eMv1at05
  
  If: <roomId> has SQLException (database binding = can't delete)
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?errorMessage=eMv1at06
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?succeedMessage=sMv1at02
  ```

//Test-comment