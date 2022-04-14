package alehad.messenger.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleMessengerDatabaseImplTest {
	
	private SimpleMessengerDatabaseImpl simpleDb;
	
	@BeforeEach
	void init() {
		// given that getInstance is a static method, we could have done this in @BeforeAll
		// but in that case, we would need a static simpleDb variable...
		// anyway, this is just to show that we can utilise @BeforeEach
		simpleDb = SimpleMessengerDatabaseImpl.getInstance();
	}

	@Test
	void testGetMessages() {
		assertEquals(simpleDb.getMessages().size(), 2, "Basic implementation has 2 entries");
	}
	
	@Test
	void testGetMessage() {
		assertEquals(simpleDb.getMessage(1).getAuthor(), "moi", "Get Message 1, assert on author field");
		assertEquals(simpleDb.getMessage(2).getMessage(), "bonjour a vous!", "Get Message 2, assert on message content");
	}
}
