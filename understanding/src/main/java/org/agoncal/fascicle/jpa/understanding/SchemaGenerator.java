package org.agoncal.fascicle.jpa.understanding;

import javax.persistence.Persistence;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
public class SchemaGenerator {

  public static void main(String[] args) {

    // tag::adocSnippet[]
    Persistence.generateSchema("cdbookstorePU", null);
    // tag::adocSkip[]
  }
}
