CREATE TABLE BOOK
(
  ID            BIGINT  NOT NULL,
  BOOK_TITLE    VARCHAR NOT NULL,
  PRICE         DOUBLE,
  DESCRIPTION   VARCHAR(2000),
  ISBN          VARCHAR,
  NB_OF_PAGES   INTEGER NOT NULL,
  ILLUSTRATIONS BOOLEAN,
  PRIMARY KEY (ID)
)
