import java.util.*;

// Base Product class
abstract class Product implements Cloneable {
    String name;
    double price;
    boolean available;

    public Product(String name, double price, boolean available) {
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public abstract Product clone();

    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }
}

// Concrete Product classes
class Laptop extends Product {
    public Laptop(double price, boolean available) {
        super("Laptop", price, available);
    }

    @Override
    public Product clone() {
        return new Laptop(this.price, this.available);
    }
}

class Headphones extends Product {
    public Headphones(double price, boolean available) {
        super("Headphones", price, available);
    }

    @Override
    public Product clone() {
        return new Headphones(this.price, this.available);
    }
}

// Discount Strategy Interface
interface DiscountStrategy {
    double applyDiscount(double totalAmount);
}

// Concrete Discount Strategies
class PercentageDiscountStrategy implements DiscountStrategy {
    private double percentage;

    public PercentageDiscountStrategy(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * percentage / 100);
    }
}

class BuyOneGetOneFreeStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount; // No additional discount logic here for simplicity
    }
}

// Cart Class
class Cart {
    private Map<Product, Integer> items = new HashMap<>();
    private DiscountStrategy discountStrategy;

    public void addProduct(Product product, int quantity) {
        if (product.isAvailable()) {
            Product clonedProduct = product.clone();
            items.put(clonedProduct, items.getOrDefault(clonedProduct, 0) + quantity);
        } else {
            System.out.println(product.getName() + " is not available.");
        }
    }

    public void updateQuantity(Product product, int newQuantity) {
        if (items.containsKey(product)) {
            items.put(product, newQuantity);
        }
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return discountStrategy != null ? discountStrategy.applyDiscount(total) : total;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            Map<String, Integer> productSummary = new LinkedHashMap<>();
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                String productName = entry.getKey().getName();
                int quantity = entry.getValue();
                productSummary.put(productName, productSummary.getOrDefault(productName, 0) + quantity);
            }

            StringBuilder cartItems = new StringBuilder("You have ");
            for (Map.Entry<String, Integer> entry : productSummary.entrySet()) {
                cartItems.append(entry.getValue()).append(" ").append(entry.getKey()).append("(s) and ");
            }
            // Remove the last " and "
            if (cartItems.length() > 0) {
                cartItems.setLength(cartItems.length() - 5);
            }
            System.out.println(cartItems.toString() + " in your cart.");
        }
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create products
        Product laptop = new Laptop(1000, true);
        Product headphones = new Headphones(50, true);
        Cart cart = new Cart();

        while (true) {
            System.out.println("\nAvailable commands:");
            System.out.println("1. Add to Cart");
            System.out.println("2. Update Quantity");
            System.out.println("3. Remove from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Calculate Total Bill");
            System.out.println("6. Set Discount Strategy");
            System.out.println("7. Exit");

            System.out.print("Enter command number: ");
            int command = scanner.nextInt();
            scanner.nextLine();  

            switch (command) {
                case 1:
                    System.out.print("Enter product name to add (Laptop/Headphones): ");
                    String addProductName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int addQuantity = scanner.nextInt();
                    scanner.nextLine(); 

                    if (addProductName.equalsIgnoreCase("Laptop")) {
                        cart.addProduct(laptop, addQuantity);
                    } else if (addProductName.equalsIgnoreCase("Headphones")) {
                        cart.addProduct(headphones, addQuantity);
                    } else {
                        System.out.println("Invalid product name.");
                    }
                    break;

                case 2:
                    System.out.print("Enter product name to update (Laptop/Headphones): ");
                    String updateProductName = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int updateQuantity = scanner.nextInt();
                    scanner.nextLine(); 

                    if (updateProductName.equalsIgnoreCase("Laptop")) {
                        cart.updateQuantity(laptop, updateQuantity);
                    } else if (updateProductName.equalsIgnoreCase("Headphones")) {
                        cart.updateQuantity(headphones, updateQuantity);
                    } else {
                        System.out.println("Invalid product name.");
                    }
                    break;

                case 3:
                    System.out.print("Enter product name to remove (Laptop/Headphones): ");
                    String removeProductName = scanner.nextLine();

                    if (removeProductName.equalsIgnoreCase("Laptop")) {
                        cart.removeProduct(laptop);
                    } else if (removeProductName.equalsIgnoreCase("Headphones")) {
                        cart.removeProduct(headphones);
                    } else {
                        System.out.println("Invalid product name.");
                    }
                    break;

                case 4:
                    System.out.println("Cart Items:");
                    cart.viewCart();
                    break;

                case 5:
                    System.out.println("Total Bill: $" + cart.calculateTotal());
                    break;

                case 6:
                    System.out.println("Available discount strategies:");
                    System.out.println("1. Percentage Discount");
                    System.out.println("2. Buy One Get One Free");
                    System.out.print("Choose a strategy: ");
                    int strategy = scanner.nextInt();
                    scanner.nextLine();  

                    if (strategy == 1) {
                        System.out.print("Enter discount percentage: ");
                        double percentage = scanner.nextDouble();
                        scanner.nextLine();  
                        cart.setDiscountStrategy(new PercentageDiscountStrategy(percentage));
                    } else if (strategy == 2) {
                        cart.setDiscountStrategy(new BuyOneGetOneFreeStrategy());
                    } else {
                        System.out.println("Invalid strategy.");
                    }
                    break;

                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid command number.");
            }
        }
    }
}
