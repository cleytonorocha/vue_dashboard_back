package com.github.cleytonorocha.vue_dashboard_back.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Address;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Customer;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Employee;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Local;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Product;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Sale;
import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductCategory;
import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductStatus;
import com.github.cleytonorocha.vue_dashboard_back.model.repository.CustomerRepository;
import com.github.cleytonorocha.vue_dashboard_back.model.repository.EmployeeRepository;
import com.github.cleytonorocha.vue_dashboard_back.model.repository.LocalRepository;
import com.github.cleytonorocha.vue_dashboard_back.model.repository.ProductRepository;
import com.github.cleytonorocha.vue_dashboard_back.model.repository.SaleRepository;
import com.github.javafaker.Faker;

import lombok.AllArgsConstructor;

// @Profile("dev")
// @Log4j2
@Configuration
@AllArgsConstructor
public class DbMock {

        private final ProductRepository productRepository;
        private final CustomerRepository customerRepository;
        private final EmployeeRepository employeeRepository;
        private final LocalRepository localRepository;
        private final SaleRepository saleRepository;
        private final Faker faker;

        private final Integer QUANTITY_PRODUCTS = 500;
        private final Integer QUANTITY_CUSTOMERS = 15000;
        private final Integer QUANTITY_EMPLOYEES = 2200;
        private final Integer QUANTITY_LOCALS = 10;
        private final Integer QUANTITY_SALES = 20000;

        @Bean
        @SuppressWarnings("deprecation")
        String saveMock() {
                for (int i = 0; i < QUANTITY_PRODUCTS; i++) {
                        Product product = new Product();
                        product.setId(null);
                        product.setName(faker.commerce().productName());
                        product.setDescription(faker.lorem().sentence());
                        product.setStock(faker.number().numberBetween(1, 100));
                        product.setRating(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 5)));
                        product.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 1000)));
                        product.setCodeQr(faker.code().isbn13());
                        product.setImageUrl(faker.internet().url());
                        product.setCategory(ProductCategory
                                        .toEnum(RandomUtils.nextInt(1, ProductCategory.values().length)));
                        product.setStatus(ProductStatus.toEnum(RandomUtils.nextInt(1, ProductStatus.values().length)));
                        productRepository.save(product);
                }

                for (int i = 0; i < QUANTITY_CUSTOMERS; i++) {
                        Customer customer = new Customer();
                        customer.setId(null);
                        customer.setFirstName(faker.name().firstName());
                        customer.setLastName(faker.name().lastName());
                        customer.setEmail(faker.internet().emailAddress());
                        customer.setPhoneNumber(faker.phoneNumber().phoneNumber());
                        customer.setAddress(generateRandomAddress());
                        customerRepository.save(customer);
                }

                for (int i = 0; i < QUANTITY_EMPLOYEES; i++) {
                        Employee employee = new Employee();
                        employee.setId(null);
                        employee.setName(faker.name().fullName());
                        employee.setDepartment(faker.job().position());
                        employee.setPosition(faker.job().title());
                        employee.setSalary(BigDecimal.valueOf(faker.number().randomDouble(2, 3000, 15000)));
                        employee.setHireDate(LocalDate.now()
                                        .minusYears(faker.number().numberBetween(1, 10)));
                        employeeRepository.save(employee);
                }

                for (int i = 0; i < QUANTITY_LOCALS; i++) {
                        Local local = new Local();
                        local.setId(null);
                        local.setAddress(generateRandomAddress());
                        localRepository.save(local);
                }

                return null;

        }

        @Bean
        String saveSales() {
                for (int i = 0; i < QUANTITY_SALES; i++) {
                        Sale sale = new Sale();
                        sale.setId(null);
                        sale.setOrderNumber(faker.code().isbn13());
                        sale.setOrderDate(LocalDateTime.now()
                                        .minusYears(faker.number().numberBetween(1, 10)));
                        sale.setTotalAmount(faker.number().randomDouble(2, 100, 10000));
                        sale.setCustomer(customerRepository
                                        .findById(Long.valueOf(faker.number().numberBetween(1, QUANTITY_CUSTOMERS)))
                                        .get());
                        sale.setEmployee(employeeRepository
                                        .findById(Long.valueOf(faker.number().numberBetween(1, QUANTITY_EMPLOYEES)))
                                        .get());
                        sale.setLocal(localRepository
                                        .findById(Long.valueOf(faker.number().numberBetween(1, QUANTITY_LOCALS)))
                                        .get());
                        saleRepository.save(sale);
                }
                return null;
        }

        private Address generateRandomAddress() {
                return Address.builder()
                                .street(faker.address().streetName())
                                .city(faker.address().city())
                                .state(faker.address().state())
                                .zipCode(faker.address().zipCode())
                                .country(faker.address().country())
                                .build();
        }
}
