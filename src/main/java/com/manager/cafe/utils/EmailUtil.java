package com.manager.cafe.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> list){
        SimpleMailMessage message=new SimpleMailMessage();
        //message.setFrom("nguyenvanphoc20@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(list !=null && list.size()>0){
            message.setCc(getCCArray(list));
        }
        javaMailSender.send(message);

    }

    private String[] getCCArray(List<String> cclist){
        String[] cc=new String[cclist.size()];
        for(int i=0;i<cclist.size();i++){
            cc[i]=cclist.get(i);
        }
        return cc;

    }

    // public void forgotMail(String to, String subject, String pass) throws MessagingException{
    //     MineMessage message=email.createMineMessage();
    //     MineMessageHelper helper =new MineMessageHelper(message,true);
    //     helper.setTo(to);
    //     helper.setSubject(subject);
    //     String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + pass + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
    //     message.setContect(htmlMsg,"text/html");
    //     javaMailSender.send(message);
    // }
}
 