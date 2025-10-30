package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificacionesController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void enviarNotificacion(String mensaje) {
        messagingTemplate.convertAndSend("/topic/notificaciones", mensaje);
    }
}
