package ru.hf.util;

import ru.hf.model.Category;
import ru.hf.model.Status;
import ru.hf.model.Transaction;
import ru.hf.model.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionMock {

    private static final SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static Transaction getDefaultTransaction() throws Exception {
        return getTransaction(
                1L, "username", "password",
                1L, "Продукты", "",
                "Обычные продукты", 1L, 109.87, 12,
                "", Status.ACTIVE);
    }

    public static Transaction getTransaction(long userId, String username, String password,
                                             long categoryId, String mainCategory, String subCategory,
                                             String description, long transactionId, double price, int quantity,
                                             String comment, Status status) throws Exception {
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword(password);

        String dateText = "2019-05-04T00:00:00.000+0000";
        Date date = sdf.parse(dateText);
        Timestamp timestamp = new Timestamp(date.getTime());

        Category category = new Category();
        category.setId(categoryId);
        category.setMainCategory(mainCategory);
        category.setSubCategory(subCategory);
        category.setDescription(description);

        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setUser(user);
        transaction.setTimestamp(timestamp);
        transaction.setCategory(category);
        transaction.setPrice(new BigDecimal(price));
        transaction.setQuantity(quantity);
        transaction.setComments(comment);
        transaction.setStatus(status);

        return transaction;
    }
}
