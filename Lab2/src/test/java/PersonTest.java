import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testEquals() {
        Person person1 = new Person("Ivanenko", "Ivan", 20);
        Person person2 = new Person("Ivanenko", "Ivan", 20);
        Person person3 = new Person("Petrenko", "Petro", 22);

        assertEquals(person1, person2);
        assertNotEquals(person1, person3);
    }

    @Test
    public void testEqualsVerifier() {
        EqualsVerifier.forClass(Person.class).verify();
    }
}