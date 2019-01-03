package org.agoncal.fascicle.jpa.managing;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
public class CustomerTest extends AbstractPersistentTest {

  // ======================================
  // =              Unit tests            =
  // ======================================

  @Test
  public void shouldPersistACustomerWithOneAddressSet() throws Exception {

    // tag::adocPersisting[]
    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");
    Address address = new Address("Ritherdon Rd", "London", "8QE", "UK");
    customer.setAddress(address);

    // Persist the object
    tx.begin();
    em.persist(customer);
    em.persist(address);
    tx.commit();

    assertNotNull(customer.getId());
    assertNotNull(address.getId());
    // end::adocPersisting[]
  }

  @Test
  public void shouldFindACustomer() throws Exception {

    Customer createdCustomer = new Customer("Antony", "Balla", "tballa@mail.com");
    Address createdAddress = new Address("Ritherdon Rd", "London", "8QE", "UK");
    createdCustomer.setAddress(createdAddress);

    // Persist the object
    tx.begin();
    em.persist(createdCustomer);
    em.persist(createdAddress);
    tx.commit();

    assertNotNull(createdCustomer.getId());
    assertNotNull(createdAddress.getId());
    Long id = createdCustomer.getId();

    // Clear
    em.clear();

    // tag::adocFinding[]
    Customer customer = em.find(Customer.class, id);
    if (customer != null) {
      // Process the object
    }
    // end::adocFinding[]
    assertNotNull(customer);
  }

  @Test
  public void shouldGetAReferenceToCustomer() throws Exception {

    Customer createdCustomer = new Customer("Antony", "Balla", "tballa@mail.com");
    Address createdAddress = new Address("Ritherdon Rd", "London", "8QE", "UK");
    createdCustomer.setAddress(createdAddress);

    // Persist the object
    tx.begin();
    em.persist(createdCustomer);
    em.persist(createdAddress);
    tx.commit();

    assertNotNull(createdCustomer.getId());
    assertNotNull(createdAddress.getId());
    Long id = createdCustomer.getId();

    // Clear
    em.clear();

    // tag::adocReference[]
    try {
      Customer customer = em.getReference(Customer.class, id);
      // Process the object
      assertNotNull(customer);
    } catch (
      EntityNotFoundException ex) {
      // Entity not found
    }
    // end::adocReference[]
  }

  @Test
  public void shouldRemoveACustomer() throws Exception {

    // tag::adocRemove[]
    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");
    Address address = new Address("Ritherdon Rd", "London", "8QE", "UK");
    customer.setAddress(address);

    // Persist the object
    tx.begin();
    em.persist(customer);
    em.persist(address);
    tx.commit();

    assertNotNull(customer.getId());
    assertNotNull(address.getId());

    // Removes the object from the database
    tx.begin();
    em.remove(customer);
    em.remove(address);
    tx.commit();

    // The objects are still available until GC
    assertEquals(customer.getFirstName(), "Antony");
    assertEquals(address.getCity(), "London");

    // The entities are not in the database
    customer = em.find(Customer.class, customer.getId());
    assertNull(customer);
    address = em.find(Address.class, address.getId());
    assertNull(address);
    // end::adocRemove[]
  }

  @Test
  public void shouldRemoveACustomerAndLeaveAddressOrphan() throws Exception {

    // tag::adocOrphan[]
    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");
    Address address = new Address("Ritherdon Rd", "London", "8QE", "UK");
    customer.setAddress(address);

    // Persist the object
    tx.begin();
    em.persist(customer);
    em.persist(address);
    tx.commit();

    assertNotNull(customer.getId());
    assertNotNull(address.getId());

    // Removes the object from the database
    tx.begin();
    em.remove(customer);
    tx.commit();

    // The objects are still available until GC
    assertEquals(customer.getFirstName(), "Antony");
    assertEquals(address.getCity(), "London");

    // Customer is not in the database but address is
    customer = em.find(Customer.class, customer.getId());
    assertNull(customer);
    address = em.find(Address.class, address.getId());
    assertNotNull(address);
    // end::adocOrphan[]
  }

  @Test
  public void shouldPersistACustomerAndThenRefreshIt() throws Exception {

    Customer createdCustomer = new Customer("Antony", "Balla", "tballa@mail.com");
    Address createdAddress = new Address("Ritherdon Rd", "London", "8QE", "UK");
    createdCustomer.setAddress(createdAddress);

    // Persist the object
    tx.begin();
    em.persist(createdCustomer);
    em.persist(createdAddress);
    tx.commit();

    assertNotNull(createdCustomer.getId());
    assertNotNull(createdAddress.getId());
    Long id = createdCustomer.getId();

    // tag::adocRefresh[]
    Customer customer = em.find(Customer.class, id);
    assertEquals("Antony", customer.getFirstName());

    customer.setFirstName("William");
    assertEquals("William", customer.getFirstName());

    em.refresh(createdCustomer);
    assertEquals("Antony", customer.getFirstName());
    // end::adocRefresh[]
  }

  @Test
  public void shouldCheckIfItContainsACustomer() throws Exception {

    // tag::adocContains[]
    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");

    // Persist the object
    tx.begin();
    em.persist(customer);
    tx.commit();

    assertTrue(em.contains(customer));

    // Removes the object
    tx.begin();
    em.remove(customer);
    tx.commit();

    assertFalse(em.contains(customer));
    // end::adocContains[]
  }

  @Test
  public void shouldDetachACustomer() throws Exception {

    // tag::adocDetach[]
    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");

    // Persist the object
    tx.begin();
    em.persist(customer);
    tx.commit();

    assertTrue(em.contains(customer));

    // Detaches the object
    em.detach(customer);

    assertFalse(em.contains(customer));
    // end::adocDetach[]
  }

  @Test
  public void shouldClearAndThenMergeACustomer() throws Exception {

    // tag::adocClearMerge[]
    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");

    // Persist the object
    tx.begin();
    em.persist(customer);
    tx.commit();
    assertTrue(em.contains(customer));

    em.clear();
    assertFalse(em.contains(customer));

    customer.setFirstName("William");
    tx.begin();
    em.merge(customer);
    tx.commit();

    em.clear();
    assertFalse(em.contains(customer));

    customer = em.find(Customer.class, customer.getId());
    assertEquals(customer.getFirstName(), "William");
    assertTrue(em.contains(customer));
    // end::adocClearMerge[]
  }

  @Test
  public void shouldUpdateACustomer() throws Exception {

    // tag::adocMerge[]
    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");

    // Persist the object
    tx.begin();
    em.persist(customer);

    assertNotNull(customer.getId());
    assertEquals(customer.getFirstName(), "Antony");

    customer.setFirstName("Williman");
    assertEquals(customer.getFirstName(), "Williman");

    tx.commit();
    // end::adocMerge[]
  }

  @Test
  public void shouldPersistACustomerAndAnAddress() throws Exception {

    // tag::adocCascade[]
    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");
    Address address = new Address("Ritherdon Rd", "London", "8QE", "UK");
    customer.setAddress(address);

    // Persist the object
    tx.begin();
    em.persist(customer);
    em.persist(address);
    tx.commit();

    assertNotNull(customer.getId());
    assertNotNull(address.getId());
    // end::adocCascade[]
  }

  @Test
  public void shouldPersistACustomerAndCascadeToTheAddress() throws Exception {

    // tag::adocCascadePersist[]
    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");
    Address address = new Address("Ritherdon Rd", "London", "8QE", "UK");
    customer.setAddress(address);

    // Persist the object
    tx.begin();
    em.persist(customer);
    tx.commit();

    assertNotNull(customer.getId());
    assertNotNull(address.getId());
    // end::adocCascadePersist[]
  }

  @Test
  public void shouldUpdateACustomerAndThenClearIt() throws Exception {

    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");

    // Persist the object
    tx.begin();
    em.persist(customer);
    tx.commit();
    assertTrue(em.contains(customer));

    em.clear();
    assertFalse(em.contains(customer));

    customer = em.find(Customer.class, customer.getId());
    assertEquals(customer.getFirstName(), "Antony");
    assertTrue(em.contains(customer));

  }

  @Test
  public void shouldClearSetAndThenMergeACustomer() throws Exception {

    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");

    // Persist the object
    tx.begin();
    em.persist(customer);
    tx.commit();
    assertTrue(em.contains(customer));

    em.clear();
    assertFalse(em.contains(customer));

    tx.begin();
    customer = em.merge(customer);
    customer.setFirstName("William");
    tx.commit();

    em.clear();
    assertFalse(em.contains(customer));

    customer = em.find(Customer.class, customer.getId());
    assertEquals(customer.getFirstName(), "William");
    assertTrue(em.contains(customer));

  }

  @Test
  public void shouldPersistACustomer() throws Exception {

    Customer customer = new Customer("Antony", "Balla", "tballa@mail.com");

    // Persist the object
    tx.begin();
    em.persist(customer);
    tx.commit();

    assertNotNull(customer.getId());
  }

  @Test
  public void shouldPersistAnAddress() throws Exception {

    Address address = new Address("Ritherdon Rd", "London", "8QE", "UK");

    // Persist the object
    tx.begin();
    em.persist(address);
    tx.commit();

    assertNotNull(address.getId());
  }
}