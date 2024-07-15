## Demo Video
https://youtu.be/idsMvGBJH7M

## Commit Syntax Requirement
**git commit -m "[All Upper-case Letters In Message] by [yournamelikethis_ex_levandung]"**

## Several ActionMethod redirecting threads:
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
  
  If: Email is invalid (error_account_01)
  If: Password is invalid or RetypePassword and Passowrd is not similar (error_account_03)
  If: Email is already existing (error_account_02)
  GET [Redirect] [MANAGER] /manager/category/teacher/add-teacher-account?errorMessage=<err_code>

  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/teacher/add-teacher-account?succeedMessage=succeed_add_01
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
  If: <accountId> not found (error_entity_01)
  If: <accountId> is a Manager (error_systemApplication_01)
  If: There's an error in Database session (error_systemApplication_01)
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
  
  If: <accountId> is null (error_entity_01)
  If: <accountId> and <instituteEmail> data pair not found (error_systemApplication_01)
  If: There's an error in application (error_systemApplication_01)
  GET [Redirect] [MANAGER] /manager/category/teacher/teacher-account-list?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/teacher/teacher-account-list?page=<page>&succeedMessage=succeed_update_01
  ```
  - Delete Teacher Account Action - AccountController.deleteTeacherAccount(__deleteBtn__):
  ```Http
  POST [MANAGER] /service/v1/manager/teacher-account-list-active-btn?deleteBtn=<accountId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <accountId> not found or not Integer (error_entity_01)
  If: <accountId> has SQLException (database binding = can't delete) (error_entity_02)
  GET [Redirect] [MANAGER] /manager/category/teacher/teacher-acocount-list?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/teacher/teacher-acocount-list?page=<page>&succeedMessage=succeed_delete_01
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
  If: <accountId> not found (error_entity_01)
  If: <accountId> is a Manager (error_systemApplication_01)
  If: There's an error in Database session (error_systemApplication_01)
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
  GET [Regirect] [MANAGER] /manager/sub-page/update-teacher?teacherid=<teacherId>&errorMessage=error_entity_03
  
  Notice: <teacherId> is always a MANAGER because of Teacher.findByTeacherIdAndInstituteEmail(...) 
  If: <teacherId> and <instituteEmail> data pair not found (error_systemApplication_01)
  If: There's an error in Database session (error_systemApplication_01)
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
  If: Data is invalid (error_entity_03)
  If: Computer Room is already exsisting (error_computerRoom_02)
  If: There's a wierd error (error_systemApplication_01)
  GET [Redirect] [MANAGER] /manager/category/computer-room/add-computer-room?errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/computer-room/add-computer-room?succeedMessage=succeed_add_01
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
  If: <roomId> not found (error_entity_01)
  If: There's an error in Database session (error_systemApplication_01)
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
  
  If: <roomId> not found (error_entity_01)
  If: There's an error in application (error_systemApplication_01)
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?page=<page>&succeedMessage=succeed_update_01
  ```
  - Delete Computer Room Action - ComputerRoomController.deleteComputerRoom(__deleteBtn__):
  ```Http
  POST [MANAGER] /service/v1/manager/computer-room-list-active-btn?deleteBtn=<roomId>
  If: Without Cookies.AccessToken
  If: AccessToken is invalid
  GET [Redirect] /public/login
  
  If: <roomId> not found or not Integer (error_entity_01)
  If: <roomId> has SQLException (database binding = can't delete) (error_entity_02)
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?page=<page>&errorMessage=<error_code>
  
  Else: All condition is valid
  GET [Redirect] [MANAGER] /manager/category/computer-room/computer-room-list?page=<page>&succeedMessage=succeed_delete_01
  ```
...more...
