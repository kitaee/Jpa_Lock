package jpa.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {

    private final TicketRepository ticketRepository;
    private final OptimisticTicketRepository optimisticTicketRepository;

    @Transactional
    public void issueTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 티켓"));
        ticket.issue();
    }

    @Transactional
    public void issueOptimisticTicket(Long id) {
        OptimisticTicket optimisticTicket = optimisticTicketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 티켓"));
        optimisticTicket.issue();
    }

    @Transactional
    public void issuePessimisticTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 티켓"));
        ticket.issue();
    }
}
