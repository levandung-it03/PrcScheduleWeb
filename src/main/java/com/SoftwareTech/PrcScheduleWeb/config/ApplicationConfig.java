package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.service.AuthService.JwtService;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
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
    public Map<String, String> responseMessages() {
        Map<String, String> messagePairs = new HashMap<>();
        //--Successfully messages.
        messagePairs.put("sMv1at01", "Thêm mới thông tin thành công!");
        messagePairs.put("sMv1at02", "Xoá thành công!");
        messagePairs.put("sMv1at03", "Sửa đổi thành công!");

        //--Error messages.
        messagePairs.put("eMv1at00", "Something wrong with application!");
        messagePairs.put("eMv1at01", "Email không tồn tại!");
        messagePairs.put("eMv1at02", "Mật khẩu không đúng!");
        messagePairs.put("eMv1at03", "Email đã tồn tại!");
        messagePairs.put("eMv1at04", "Đã tồn tại mã phòng tại khu vực bạn chọn!");
        messagePairs.put("eMv1at05", "Phòng thực hành không tồn tại!");
        messagePairs.put("eMv1at06", "Trường dữ liệu không thể xoá, thay vào đó hãy đổi trạng thái để bảo toàn dữ liệu!");
        messagePairs.put("eMv1at07", "Mã giảng viên không tồn tại!");
        messagePairs.put("eMv1at08", "Mã tài khoản không tồn tại!");
        messagePairs.put("eMv1at09", "Thông tin không đúng, hãy kiểm tra lại!");
        return messagePairs;
    }
}
