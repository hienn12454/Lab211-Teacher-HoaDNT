package sample.dto;

/**
 *
 * @author TUF
 */
/* Interface for menu */
public interface I_Menu {

    void addItem(String s);

    int getChoice();

    void showMenu();

    boolean confirmYesNo(String welcome);

}
