package com.sparta.wuzuzu.domain.email.service;

import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.entity.UserRole;
import com.sparta.wuzuzu.domain.user.repository.UserRepository;
import com.sparta.wuzuzu.global.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailAuthService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;
    private static final String fromEmail = "9noeyni9@gmail.com";

    //인증 코드 이메일 발송
    public void sendEmail(User user) throws MessagingException {
        user = userRepository.findById(user.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        if (user.getRole().equals(UserRole.USER))
            throw new IllegalArgumentException("이미 인증된 회원입니다.");

        if (redisUtil.existData(user.getEmail())) {
            redisUtil.deleteData(user.getEmail());
        }

        // 이메일 폼 생성
        MimeMessage emailForm = createEmailForm(user.getEmail());

        // 이메일 발송
        javaMailSender.send(emailForm);
    }

    public boolean verifyEmailCode(User user, String verifyCode) {
        user = userRepository.findById(user.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        String codeFromEmail = redisUtil.getData(user.getEmail());

        if (codeFromEmail == null)
            return false;

        if (!codeFromEmail.equals(verifyCode)) {
            return false;
        }
        user.updateUserRole(user, UserRole.USER);
        return true;
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
        message.setText(setContext(authCode), "utf-8", "html");

        // Redis 에 해당 인증코드 인증 시간 설정
        redisUtil.setDataExpire(email, authCode, 60 * 30L);

        return message;
    }
}
