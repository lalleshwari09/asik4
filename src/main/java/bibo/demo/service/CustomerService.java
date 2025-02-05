package bibo.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bibo.demo.Customer;
import bibo.demo.repository.CustomerRepository;

@Service
public class CustomerService {
   private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(Customer customer) {
        customerRepository.addCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerRepository.getCustomer(id);
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteCustomer(id);
    }

    public void updateCustomer(int id, Customer updatedCustomer) {
        customerRepository.updateCustomer(id, updatedCustomer);
    } 
}
