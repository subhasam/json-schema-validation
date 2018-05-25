package subhasys.api.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import subhasys.api.restful.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
