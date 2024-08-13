package com.mrtkrkrt.creditapp.notification.service;

import com.mrtkrkrt.creditapp.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendSms(String userTckn) {
        log.info("NotificationServiceImpl -> sendSms is started, userTckn: {}", userTckn);
    }

    @Override
    public void sendPn(String deviceId) {
        log.info("NotificationServiceImpl -> sendPn is started, deviceId: {}", deviceId);
    }

    @Override
    public void sendMail(String mail) {
        log.info("NotificationServiceImpl -> sendMail is started, mail: {}", mail);
    }
}
