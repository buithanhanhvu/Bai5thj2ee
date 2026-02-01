package nhom6.Buithanhanhvu.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private List<Item> cartItems = new ArrayList<>();

    public List<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Item> cartItems) {
        this.cartItems = cartItems;
    }

    public void addItems(Item item) {
        boolean isExist = cartItems.stream()
                .filter(i -> Objects.equals(i.getBookId(), item.getBookId()))
                .findFirst()
                .map(i -> {
                    i.setQuantity(i.getQuantity() + item.getQuantity());
                    return true;
                })
                .orElse(false);

        if (!isExist) {
            cartItems.add(item);
        }
    }

    public void removeItems(Long bookId) {
        cartItems.removeIf(item -> Objects.equals(item.getBookId(), bookId));
    }

    public void updateItems(int bookId, int quantity) {
        cartItems.stream()
                .filter(item -> Objects.equals(item.getBookId(), (long) bookId))
                .forEach(item -> item.setQuantity(quantity));
    }
} 
