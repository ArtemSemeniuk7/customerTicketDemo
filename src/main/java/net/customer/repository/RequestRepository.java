package net.customer.repository;

import net.customer.model.RequestTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<RequestTable, Long> {
    String FIND_REQUESTS =
            "SELECT * FROM request_table WHERE request_status = 307 OR request_status = 0 LIMIT 1;";

    @Query(value = FIND_REQUESTS, nativeQuery = true)
    public RequestTable findProject();
}
