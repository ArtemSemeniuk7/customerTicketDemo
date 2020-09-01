package net.customer.ticketPaymentService.util.idRandomGenerator;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    public static AtomicLong generateUniqueId() {
        AtomicLong id = new AtomicLong(-1);
        do {
            id.addAndGet(UUID.randomUUID().getMostSignificantBits());
        } while (id.get() < 0);
        return id;
    }
}
