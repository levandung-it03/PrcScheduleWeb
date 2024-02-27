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
    ```Http
    GET /public/login
    Note: With Cookies.AccessToken
    
    GET Redirect:/role/home
    ```
  - Access Authorized Request:
    ```Http
    GET, POST /teacher/, /manager/
    If: Without Cookies.AccessToken
    If: AccessToken is Expired
    GET Redirect:/public/login
    
    If: AccessToken has wrong Role
    Return: HTTPStatus[403 - Forbidden]
    ```
  
    
