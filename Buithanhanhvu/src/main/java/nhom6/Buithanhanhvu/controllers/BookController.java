package nhom6.Buithanhanhvu.controllers;

import nhom6.Buithanhanhvu.daos.Item;
import nhom6.Buithanhanhvu.entities.Book;
import nhom6.Buithanhanhvu.services.BookService;
import nhom6.Buithanhanhvu.services.CartService;
import nhom6.Buithanhanhvu.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final CartService cartService;

    /**
     * Display all books with pagination
     */
    @GetMapping
    public String showAllBooks(@NotNull Model model,
                               @RequestParam(defaultValue = "0") Integer pageNo,
                               @RequestParam(defaultValue = "20") Integer pageSize,
                               @RequestParam(defaultValue = "id") String sortBy) {
        var books = bookService.getAllBooks(pageNo, pageSize, sortBy);
        model.addAttribute("books", books);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", (books.size() + pageSize - 1) / pageSize);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/list";
    }

    /**
     * Display add book form
     */
    @GetMapping("/add")
    public String addBookForm(@NotNull Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }

    /**
     * Add a new book
     */
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          @NotNull BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/add";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }

    /**
     * Display edit book form
     */
    @GetMapping("/edit/{id}")
    public String editBookForm(@NotNull Model model, @PathVariable Long id) {
        var book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/edit";
    }

    /**
     * Update book information
     */
    @PostMapping({"/edit", "/edit/{id}"})
    public String editBook(@Valid @ModelAttribute("book") Book book,
                           @NotNull BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/edit";
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }

    /**
     * Delete a book by id
     */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.getBookById(id)
                .ifPresentOrElse(
                        book -> bookService.deleteBookById(id),
                        () -> {
                            throw new IllegalArgumentException("Book not found with id: " + id);
                        }
                );
        return "redirect:/books";
    }

    /**
     * Search books by keyword
     */
    @GetMapping("/search")
    public String searchBook(@NotNull Model model,
                             @RequestParam String keyword,
                             @RequestParam(defaultValue = "0") Integer pageNo,
                             @RequestParam(defaultValue = "20") Integer pageSize) {
        var books = bookService.searchBook(keyword);
        model.addAttribute("books", books);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", (books.size() + pageSize - 1) / pageSize);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/list";
    }

    /**
     * Add book to cart
     */
    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
                            @RequestParam Long id,
                            @RequestParam String name,
                            @RequestParam Double price,
                            @RequestParam(defaultValue = "1") Integer quantity) {
        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, name, price, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/books";
    }

    /**
     * View cart
     */
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        var cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        return "book/cart";
    }

    /**
     * Update cart item quantity
     */
    @PostMapping("/update-cart")
    public String updateCart(HttpSession session,
                             @RequestParam Long id,
                             @RequestParam Integer quantity) {
        var cart = cartService.getCart(session);
        cart.updateItems(id.intValue(), quantity);
        cartService.updateCart(session, cart);
        return "redirect:/books/cart";
    }

    /**
     * Remove item from cart
     */
    @PostMapping("/remove-from-cart")
    public String removeFromCart(HttpSession session, @RequestParam Long id) {
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        cartService.updateCart(session, cart);
        return "redirect:/books/cart";
    }

    /**
     * Clear cart
     */
    @PostMapping("/clear-cart")
    public String clearCart(HttpSession session) {
        cartService.removeCart(session);
        return "redirect:/books/cart";
    }

    /**
     * Checkout cart
     */
    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        cartService.checkout(session);
        redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
        return "redirect:/books/cart";
    }
}
