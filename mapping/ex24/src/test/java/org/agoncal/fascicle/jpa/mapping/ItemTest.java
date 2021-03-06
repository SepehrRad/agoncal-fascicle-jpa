package org.agoncal.fascicle.jpa.mapping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
public class ItemTest extends AbstractPersistentTest {

  // ======================================
  // =              Unit tests            =
  // ======================================

  @Test
  public void shouldCreateABook() throws Exception {

    Book book = new Book("H2G2", 21f, "Best IT book", "123-456-789", "Pinguin", 321, false);
    tx.begin();
    em.persist(book);
    tx.commit();
    assertNotNull(book.getId(), "Book Id should not be null");
  }
}
