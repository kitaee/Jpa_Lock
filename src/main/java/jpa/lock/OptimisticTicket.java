package jpa.lock;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
public class OptimisticTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;

    @Version
    private int version;

    public void issue() {
        if(count <= 0) {
            throw new IllegalArgumentException("수량 부족");
        }
        count-=1;
    }
}
