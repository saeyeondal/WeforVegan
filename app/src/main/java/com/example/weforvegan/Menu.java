package com.example.weforvegan;

public class Menu {
    String menu;
    String source;

    public Menu(String menu, String source) {
        this.menu = menu;
        this.source = source;
    }

    public String getMenu() { return menu; }
    public void setMenu(String menu) { this.menu = menu; }

    public String getSource() { return source; }

    public void setSource(String source) {
        this.source = source;
    }
}
