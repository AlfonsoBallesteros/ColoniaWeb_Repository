package com.springbootapp.coloniaweb.task;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springbootapp.coloniaweb.models.services.IConfirmationTokenService;
import com.springbootapp.coloniaweb.models.services.IUsuarioService;

@Service
@Transactional
public class TokenPurgeTask {
@Autowired
IConfirmationTokenService tokenService;
@Autowired
IUsuarioService userService;

/*
 * Usamos expresion cron para indicar cada cuanto tiempos se ejecuta nuestra tarea
 *  el formato va en segundos - minuto - hora - dia - mes - dia de la semana - año
 * 
 */
@Scheduled(cron = "0 */10 * * * *")
public void purgeExpired() {

    Date now = Date.from(Instant.now());
    tokenService.deleteAllExpiredSince(now);
    userService.deleteAllExpiredSince(now);
}
}
