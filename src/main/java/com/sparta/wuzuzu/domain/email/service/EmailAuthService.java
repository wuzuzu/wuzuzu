package com.sparta.wuzuzu.domain.email.service;

import com.sparta.wuzuzu.domain.admin.dto.AdminVerifyRequest;
import com.sparta.wuzuzu.domain.admin.entity.Admin;
import com.sparta.wuzuzu.domain.admin.repository.AdminRepository;
import com.sparta.wuzuzu.domain.user.entity.UserRole;
import com.sparta.wuzuzu.domain.user.repository.UserRepository;
import com.sparta.wuzuzu.global.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String fromEmail = "9noeyni9@gmail.com";

    //인증 코드 이메일 발송
    public void sendEmail(String toEmail) throws MessagingException {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }

        // 이메일 폼 생성
        MimeMessage emailForm = createEmailForm(toEmail);

        // 이메일 발송
        javaMailSender.send(emailForm);
    }

    public boolean verifyEmailCode(String mail, String verifyCode) {
        String codeFromEmail = redisUtil.getData(mail);

        if (codeFromEmail == null)
            return false;

        return codeFromEmail.equals(verifyCode);
    }

    public void sendAdminEmail(Long adminId, String toEmail) throws MessagingException {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }

        // 이메일 폼 생성
        MimeMessage emailForm = createEmailForm(toEmail);

        // 이메일 발송
        javaMailSender.send(emailForm);
    }

    public String verifyAdminEmailCode(Long adminId, AdminVerifyRequest adminVerifyRequest) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String codeFromEmail = redisUtil.getData(adminVerifyRequest.getMail());

        if(admin.getRole().equals(UserRole.ADMIN)){
            throw new IllegalArgumentException("이미 인증된 회원입니다.");
        }

        if (codeFromEmail == null)
            throw new IllegalArgumentException("존재하지 않습니다.");

        if(!codeFromEmail.equals(adminVerifyRequest.getVerifyCode())){
            throw new IllegalArgumentException("잘못된 인증 코드입니다.");
        }

        String wuzuzuPassword = createWuzuzuPassword(admin.getEmail());
        admin.updateAfterAuth(wuzuzuPassword, UserRole.ADMIN);

        return wuzuzuPassword;
    }

    // 코드 생성
    private String createCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int codeLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 | i >= 97))
                .limit(codeLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // 관리자 비밀번호 생성
    private String createWuzuzuPassword(String email){

        //소문자, 대문자, 숫자
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder builder = new StringBuilder();
        SecureRandom rm = new SecureRandom();
        String[] array = email.split("@");

        builder.append(array[0]);

        for(int i=0; i<8; i++) {
            //무작위로 문자열의 인덱스 반환
            int index = rm.nextInt(chars.length());
            //index의 위치한 랜덤값
            builder.append(chars.charAt(index));
        }

        return builder.append("!@#").toString();
    }

    // 이메일 내용 초기화
    private String setContext(String code) {
        Context context = new Context();
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        context.setVariable("code", code);

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);

        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine.process("mail", context);
    }

    // 이메일 폼 생성
    private MimeMessage createEmailForm(String email) throws MessagingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[wuzuzu] 이메일 인증");
        message.setFrom(fromEmail);

        if (Objects.equals(adminRepository.findByEmail(email).getEmail(), email)) {
            message.setText(setContext("Admin" + authCode), "utf-8", "html");
            redisUtil.setDataExpire(email, "Admin" + authCode, 60 * 30L);
            return message;
        }

        message.setText(authCode, "utf-8", "html");

        // Redis 에 해당 인증코드 인증 시간 설정
        redisUtil.setDataExpire(email, authCode, 60 * 30L);

        return message;
    }
}
