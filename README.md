## Commit Syntax Requirement
**git commit -m "[All Upper-case Letters In Message] by [yournamelikethis_ex_levandung]"**

## TODO Notes
**1. Authorization with JWT**

**2. Authentication with Bcrypt**

**3. @Transaction with Services**

## How Our Pages Work:
*1. Login Page*
- Validating before login:
  - Validate under Javascript site.
  - Validate from Java (Customized Modules with Spring Security) site.
- After login:
  - Access LoginPage from Authorized Request:
    > GET: /public/login
  <br> *Note: With Cookies.AccessToken*
  <br>
  <br> GET: Redirect:/role/home
  - Access Authorized Request:
    > METHOD: /teacher/, /manager/
  <br>
  <br>*If: Without Cookies.AccessToken*
  <br>*Else If: AccessToken is Expired*
  <br>GET: Redirect:/public/login
  <br>
  <br>*If: AccessToken has wrong Role*
  <br>HTTPStatus: 403 - *Forbidden*
  
    
