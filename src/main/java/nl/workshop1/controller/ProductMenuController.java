package nl.workshop1.controller;

import nl.workshop1.menu.ProductMenu;

/**
 *
 * @author FeniksBV
 */
public class ProductMenuController extends MenuController {

    private ProductMenu productMenu;

    public ProductMenuController(ProductMenu productMenu) {
        this.productMenu = productMenu;
    }

    @Override
    public void buildOptionsMenu() {
        productMenu.resetMenu();
        productMenu.addSubMenu("Nieuw product", "1");
        productMenu.addSubMenu("Zoek product " + "(filter)", "2");
        productMenu.addSubMenu("Wijzig product", "3");
        productMenu.addSubMenu("Verwijder product", "4");
    }

    @Override
    public void handleMenu() {
        while (true) {
            productMenu.drawMenu();
            switch (productMenu.userChoice()) {
                case "0":
                    return;
                case "1":
                    System.out.println("Nieuw product");
                    break;
                case "2":
                    System.out.println("Zoek product");
                    break;
                case "3":
                    System.out.println("Wijzig product");
                    break;
                case "4":
                    System.out.println("Verwijder product");
                    break;
            }
        }
    }

}
