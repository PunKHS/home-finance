package ru.hf.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;
import ru.hf.model.Category;
import ru.hf.model.Status;
import ru.hf.model.Transaction;
import ru.hf.model.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaAuditing
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static final SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private Transaction getMockTransaction() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");

        String dateText = "2019-05-04T00:00:00.000+0000";
        Date date = sdf.parse(dateText);
        Timestamp timestamp = new Timestamp(date.getTime());

        Category category = new Category();
        category.setId(1L);
        category.setMainCategory("Продукты");
        category.setSubCategory("");
        category.setDescription("Обычные продукты");

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTimestamp(timestamp);
        transaction.setCategory(category);
        transaction.setPrice(new BigDecimal(109.40));
        transaction.setQuantity(10);
        transaction.setComments("Обычный комментарий");
        transaction.setStatus(Status.ACTIVE);

        return transaction;
    }

    @Test
    public void findById() throws Exception {
        this.entityManager.persist(getMockTransaction());
        Transaction transaction = transactionRepository.findById(1L);
        assertThat(transaction).isEqualTo(getMockTransaction());
    }
}