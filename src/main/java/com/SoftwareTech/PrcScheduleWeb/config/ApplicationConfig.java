package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.service.AuthService.JwtService;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AccountRepository accountRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String instituteEmail) throws UsernameNotFoundException {
                return accountRepository
                    .findByInstituteEmail(instituteEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }

    @Bean
    public StaticUtilMethods staticUtilMethods() {
        return new StaticUtilMethods(responseMessages(), jwtService, userDetailsService());
    }

    @Bean
    public Validator hibernateValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public Map<String, String> responseMessages() {
        Map<String, String> messagePairs = new HashMap<>();
        //--Successfully messages.
        messagePairs.put("succeed_add_01", "Thêm mới thông tin thành công!");
        messagePairs.put("succeed_delete_01", "Xoá thành công!");
        messagePairs.put("succeed_update_01", "Sửa đổi thành công!");

        //--Error messages.
        messagePairs.put("error_systemApplication_01", "Something wrong with application!");

        messagePairs.put("error_entity_01", "Không tìm thấy ID của đối tượng, vui lòng không sửa đổi phần mềm!");
        messagePairs.put("error_entity_02", "Trường dữ liệu không thể xoá, thay vào đó hãy đổi trạng thái để bảo toàn dữ liệu!");
        messagePairs.put("error_entity_03", "Dữ liệu không hợp lệ, hãy kiểm tra lại!");

        messagePairs.put("error_account_01", "Email không đúng!");
        messagePairs.put("error_account_02", "Email đã tồn tại!");
        messagePairs.put("error_account_03", "Mật khẩu không đúng!");

        messagePairs.put("error_computerRoom_01", "Đã tồn tại 'phòng học' tại khu vực bạn chọn!");
        messagePairs.put("error_computerRoom_02", "Đã tồn tại 'phòng thực hành' tại khu vực bạn chọn!");

        messagePairs.put("error_teacherRequest_01", "Yêu cầu đã được thêm lịch nên không thể tạo!");
        messagePairs.put("error_teacherRequest_02", "Yêu cầu đã bị từ chối nên không thể tạo lịch!");
        messagePairs.put("error_teacherRequest_03", "Yêu cầu đã bị huỷ nên không thể tạo lịch!");

        messagePairs.put("error_schedule_01", "Lịch không hợp lệ hoặc đang để trống!");
        return messagePairs;
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(Logger.class);
    }
}
