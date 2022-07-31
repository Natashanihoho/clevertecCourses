package ru.clevertec.gordievich.domain.receipt;

import lombok.NoArgsConstructor;
import ru.clevertec.gordievich.domain.cards.DiscountCard;
import ru.clevertec.gordievich.domain.cards.DiscountCardDao;
import ru.clevertec.gordievich.domain.products.Product;
import ru.clevertec.gordievich.domain.products.ProductDao;
import ru.clevertec.gordievich.infrastructure.aspect.annotation.CustomLog;
import ru.clevertec.gordievich.infrastructure.exceptions.DaoException;
import ru.clevertec.gordievich.infrastructure.exceptions.NotEnoughProductsException;
import ru.clevertec.gordievich.infrastructure.exceptions.UnknownIdException;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;
import static ru.clevertec.gordievich.domain.receipt.ReceiptFormatter.*;

@NoArgsConstructor(access = PRIVATE)
public class ReceiptServiceImpl implements ReceiptService {

    private static final ReceiptServiceImpl INSTANCE = new ReceiptServiceImpl();
    private static final DiscountCardDao discountCardDao = DiscountCardDao.getInstance();
    private static final ProductDao productDao = ProductDao.getInstance();

    @CustomLog
    @Override
    public String interpret(String[] args) throws UnknownIdException, NotEnoughProductsException, DaoException {
        String title = title(List.of("Liam Neeson", "Meryl Streep", "Kate Winslet", "Will Smith"));
        List<Position> positions = addPositionsToList(args);
        String receipt = receipt(positions, discountCard(args));
        return title + receipt;
    }

    private String title(List<String> cashiers) {
        return TITLE.formatted(
                cashiers.get(new Random().nextInt(cashiers.size())),
                LocalDate.now(),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );
    }

    private Optional<DiscountCard> discountCard(String[] argsLine) {
        return Arrays.stream(argsLine)
                .filter(isCard())
                .findFirst()
                .flatMap(card -> {
                    try {
                        return discountCardDao.findByName(card.toUpperCase());
                    } catch (DaoException e) {
                        return Optional.empty();
                    }
                });
    }

    private List<Position> addPositionsToList(String[] argsLine) throws UnknownIdException, NotEnoughProductsException, DaoException {
        List<Position> positions = new ArrayList<>();
        List<String> productArguments = Arrays.stream(argsLine)
                .filter(Predicate.not(isCard()))
                .collect(Collectors.toList());
        for (String productArgument : productArguments) {
            String[] arg = productArgument.split("-");
            int id = Integer.parseInt(arg[0]);
            int requiredNumber = Integer.parseInt(arg[1]);
            Product product = productDao.findById(id)
                    .orElseThrow(() -> new UnknownIdException("Product is not found with id: " + id));
            if (product.getAvailableNumber() < requiredNumber) {
                throw new NotEnoughProductsException("There are not enough products");
            }
            positions.add(new Position(product, requiredNumber));
            product.setAvailableNumber(product.getAvailableNumber() - requiredNumber);
            productDao.update(product);
        }
        return positions;

    }

    private Predicate<String> isCard() {
        return line -> line.contains("card");
    }

    private String receipt(List<Position> positions, Optional<DiscountCard> discountCard) {
        StringBuilder receipt = new StringBuilder();
        List<Double> totalDiscounts = new ArrayList<>();
        List<Double> totalAmounts = new ArrayList<>();
        double discount = 0;
        for (Position position : positions) {
            if (position.isSpecialDiscount()) {
                int discountPercentForFive = 10;
                discount = position.getFullPrice() * (double) discountPercentForFive / 100;
            } else if (discountCard.isPresent()) {
                discount = position.getFullPrice() * (double) discountCard.get().getDiscountPercent() / 100;
            }
            totalDiscounts.add(discount);
            totalAmounts.add(position.getFullPrice() - discount);
            receipt.append(
                    NORMAL_FIELD.formatted(
                            position.getProduct().getId(),
                            position.getProduct().getDescription(),
                            position.getProduct().getPrice(),
                            position.getRequiredNumber(),
                            position.getFullPrice() - discount
                    )
            );
            if (discount != 0) {
                receipt.append(DISCOUNT_FIELD.formatted(-discount));
            }
        }
        Double totalDiscount = totalDiscounts.stream()
                .reduce(0.0, Double::sum);
        Double totalAmount = totalAmounts.stream()
                .reduce(0.0, Double::sum);
        receipt.append(COMPLETION.formatted(-totalDiscount, totalAmount));
        return receipt.toString();
    }

    public static ReceiptServiceImpl getInstance() {
        return INSTANCE;
    }
}
