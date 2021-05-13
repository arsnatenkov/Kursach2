package application.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "offer_id")
    @NotNull
    private Long offerId;

    @Column(name = "time")
    @NotNull
    private LocalDateTime time;

    @Column(name = "message")
    @NotNull
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender")
    @NotNull
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    @NotNull
    private User receiver;

    public Message(User sender, User receiver){
        this.sender = sender;
        this.receiver = receiver;
        time = LocalDateTime.now();
        message = sender.getName() + "joined";
    }

}
