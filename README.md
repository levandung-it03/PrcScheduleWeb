## Commit Syntax Requirement
**git commit -m "[All Upper-case Letters In Message] by [yournamelikethis_ex_levandung]"**

## TODO Notes
**1. Authorization with JWT**

**2. Authentication with Bcrypt**

**3. @Transaction with Services**

## How Our Pages Work:
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
  GET [Redirect] /role/home
  ```
- Access LoginPage from Authorized Request:
  ```Http
  GET /public/login
  If: With Cookies.AccessToken
  GET [Redirect] /role/home
  ```
- Access Authorized Request:
  ```Http
  GET /teacher/, /manager/
  POST /teacher/, /manager/
  If: Without Cookies.AccessToken
  If: AccessToken is Expired
  GET [Redirect] /public/login
    
  If: AccessToken has wrong Role
  Return: HTTPStatus[403 - Forbidden]
  ```
*2. Add Teacher Account Page*
- Access AddAccountPage from Role.MANAGER:
  ```Http
  GET /manager/category/teacher/add-teacher-account
  If: Without Cookies.AccessToken
  If: AccessToken is Expired
  GET [Redirect] /public/login
  ```
- Post NewTeacherAccount(DtoRegisterAccount) Action:
  ```Http
  GET /service/v1/manager/add-teacher-account
  If: Without Cookies.AccessToken
  If: AccessToken is Expired
  GET [Redirect] /public/login
  ```

  ```Http
  GET /service/v1/manager/add-teacher-account
  If: Username is invalid
  If: Username is already existing
  If: Password is invalid
  If: RetypePassword and Passowrd is not similar.
  GET [Redirect] /service/v1/manager/add-teacher-account?errorMessage=<err_code>

  Else: All condition is valid
  GET [Redirect] /service/v1/manager/add-teacher-account?succeedMessage=<succeed_code>
  ```
