package com.btbatux.taskmanagement;
import com.btbatux.taskmanagement.model.Task;
import com.btbatux.taskmanagement.model.User;
import com.btbatux.taskmanagement.repository.TaskRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling
public class ScheduledTasks {
    private final TaskRepository taskRepository;
    private final JavaMailSender emailSender;

    public ScheduledTasks(TaskRepository taskRepository, JavaMailSender emailSender) {
        this.taskRepository = taskRepository;
        this.emailSender = emailSender;
    }



    @Scheduled(cron = "0 0 * * * *") // Her saat çalışır
    public void sendTaskCompletionEmails() {


        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Task> tasks = taskRepository.findByEndDate(tomorrow);

        for (Task task : tasks) {
            User user = task.getUser();
            if(user != null)
            {
                sendEmail(user.getEmail(), "Göreviniz Yarın Bitiyor", "Merhaba "
                        + user.getUsername() + ", göreviniz yarın bitiyor. Lütfen kontrol edin.");
            }

        }
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
