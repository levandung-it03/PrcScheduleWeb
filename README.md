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

## How Do Our Pages Work:
*Original Account*
- Username: manager0@ptithcm.edu.vn
- Password: 111222333

*General Forms*
- Validate under Javascript site.
- Validate from Java (Customized Modules with Spring Security) site.

*1. Login Page*
- Login Action Request:
  ```Http
  POST /service/v1/auth/authenticate
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
- Add Teacher Account Page (on Category):
  - Add Teacher Account Page:
  ```Http
  GET [MANAGER] /manager/category/teacher/add-teacher-account
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  ```
  - Add Teacher Account Action - AccountController.addTeacherAccount(__DtoRegisterAccount__):
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
- Teacher Account List Page (on Category):
  - Main List Page:
  ```Http
  GET [MANAGER] /manager/category/teacher/teacher-account-list
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  ```
  - Update Teacher Account Page (sub-page) - SubPageController.getUpdateTeacherAccountPage().addObject():
  ```Http
  GET [MANAGER] /manager/sub-page/teacher/update-teacher-account?accountId=<accountId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <accountId> is Null (empty)
  If: <accountId> not found (eMv1at08)
  If: <accountId> is a Manager (eMv1at00)
  If: There's an error in Database session (eMv1at00)
  GET [Redirect] [MANAGER] /manager/sub-page/teacher/update-teacher-account?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/sub-page/teacher/update-teacher-account?page=<page>&errorMessage=<computerRoom>
  ```
  - Update Teacher Account Action - AccountController.updateTeacherAccount(__DtoUpdateTeacherAccount__):
  ```Http
  POST [MANAGER] /service/v1/manager/update-teacher-account?accountId=<accountId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <accountId> is null (eMv1at08)
  If: <accountId> and <instituteEmail> data pair not found (eMv1at00)
  If: There's an error in application (eMv1at00)
  GET [Redirect] [MANAGER] /manager/category/teacher/teacher-account-list?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/teacher/teacher-account-list?page=<page>&succeedMessage=sMv1at03
  ```
  - Delete Teacher Account Action - AccountController.deleteTeacherAccount(__deleteBtn__):
  ```Http
  POST [MANAGER] /service/v1/manager/teacher-account-list-active-btn?deleteBtn=<accountId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <accountId> not found or not Integer (eMv1at08)
  If: <accountId> has SQLException (database binding = can't delete) (eMv1at06)
  GET [Redirect] [MANAGER] /manager/category/teacher/teacher-acocount-list?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/teacher/teacher-acocount-list?page=<page>&succeedMessage=sMv1at02
  ```
- Teacher List Page (on Category):
  - Main List Page:
  ```Http
  GET [MANAGER] /manager/category/teacher/teacher-list
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  ```
  - Update Teacher Page (sub-page) - SubPageController.getUpdateTeacherPage().addObject(__DtoUpdateTeacher__):
  ```Http
  GET [MANAGER] /manager/sub-page/teacher/update-teacher-account?accountId=<accountId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <accountId> is Null (empty)
  If: <accountId> not found (eMv1at07)
  If: <accountId> is a Manager (eMv1at00)
  If: There's an error in Database session (eMv1at00)
  GET [Redirect] [MANAGER] /manager/sub-page/teacher/update-teacher?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/sub-page/teacher/update-teacher?page=<page>&errorMessage=<computerRoom>
  ```
  - Update Teacher Page Action - TeacherController.updateTeacherInfo(__DtoUpdateTeacher__):
  ```Http
  POST [MANAGER] /service/v1/manager/update-teacher
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: data is invalid
  GET [Regirect] [MANAGER] /manager/sub-page/update-teacher?teacherid=<teacherId>&errorMessage=eMv1at09
  
  Notice: <teacherId> is always a MANAGER because of Teacher.findByTeacherIdAndInstituteEmail(...) 
  If: <teacherId> and <instituteEmail> data pair not found (eMv1at00)
  If: There's an error in Database session (eMv1at00)
  GET [Redirect] [MANAGER] /manager/sub-page/teacher/update-teacher?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/sub-page/teacher/update-teacher?page=<page>&errorMessage=<computerRoom>
  ```
  
*3. Computer Room Pages*
- Add Computer Room Page (on Category):
  - Add Computer Room Page:
  ```Http
  GET [MANAGER] /manager/category/computer-room/add-computer-room
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  ```
  - Add Computer Room Action - ComputerRoomController.addComputerRoom(__DtoAddComputerRoom__):
  ```Http
  POST [MANAGER] /service/v1/manager/add-computer-room
  If: Data is invalid (eMv1at09)
  If: Computer Room is already exsisting (eMv1at04)
  If: There's a wierd error (eMv1at00)
  GET [Redirect] [MANAGER] /manager/category/computer-room/add-computer-room?errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/computer-room/add-computer-room?succeedMessage=sMv1at01
  ```
- Computer Room List Page (on Category) - List<DtoComputerRoom>:
  - Main List Page:
  ```Http
  GET [MANAGER] /manager/category/computer-room/computer-room-list
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  ```
  - Update Computer Room Page (sub-page) - SubPageController.getUpdateTeacherPage().addObject(__DtoComputerRoom__):
  ```Http
  GET [MANAGER] /manager/sub-page/computer-room/update-computer-room?roomId=<roomId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <roomId> is Null (empty)
  If: <roomId> not found (eMv1at05)
  If: There's an error in Database session (eMv1at00)
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/sub-page/computer-room/update-computer-room?page=<page>&computerRoom=<computerRoom>
  ```
  - Update Computer Room Action - ComputerRoomController.updateComputerRoom(__DtoUpdateComputerRoom__):
  ```Http
  POST [MANAGER] /service/v1/manager/update-computer-room?roomId=<roomId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <roomId> not found (eMv1at05)
  If: There's an error in application (eMv1at00)
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?page=<page>&succeedMessage=sMv1at03
  ```
  - Delete Computer Room Action - ComputerRoomController.deleteComputerRoom(__deleteBtn__):
  ```Http
  POST [MANAGER] /service/v1/manager/computer-room-list-active-btn?deleteBtn=<roomId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <roomId> not found or not Integer (eMv1at05)
  If: <roomId> has SQLException (database binding = can't delete) (eMv1at06)
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?page=<page>&succeedMessage=sMv1at02
  ```
