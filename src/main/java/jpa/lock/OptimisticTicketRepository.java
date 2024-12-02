package jpa.lock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OptimisticTicketRepository extends JpaRepository<OptimisticTicket, Long> {
}
