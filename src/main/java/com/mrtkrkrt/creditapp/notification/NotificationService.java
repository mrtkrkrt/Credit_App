package com.mrtkrkrt.creditapp.notification;

public interface NotificationService {

    void sendSms(String userTckn);
    void sendPn(String deviceId);
    void sendMail(String mail);
}
