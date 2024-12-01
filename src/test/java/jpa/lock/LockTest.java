package jpa.lock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class LockTest {

    private final TicketService ticketService;

    @Autowired
    public LockTest(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Test
    @DisplayName("싱글쓰레드 상황에서 티켓 카운팅을 감소시킨다.")
    void issue_ticket_in_single_thread() {
        ticketService.issueTicket(1L);
    }

    @Test
    @DisplayName("멀티쓰레드 상황에서 티켓 카운팅을 감소시킨다.")
    void issue_ticket_in_multi_thread() throws InterruptedException {
        final int executeNumber = 20;

        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        final CountDownLatch countDownLatch = new CountDownLatch(executeNumber);

        final AtomicInteger successCount = new AtomicInteger();
        final AtomicInteger failCount = new AtomicInteger();

        for(int i=0; i<executeNumber; i++) {
            executorService.execute(() -> {
                try {
                    ticketService.issueTicket(1L);
                    successCount.getAndIncrement();
                    System.out.println("티켓 발급 완료");
                } catch (Exception e) {
                    failCount.getAndIncrement();
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        System.out.println("발급된 쿠폰의 개수 = " + successCount.get());
        System.out.println("실패한 횟수 = " + failCount.get());
    }

}
