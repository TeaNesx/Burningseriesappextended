package com.example.burningseriesapp_extended.Model;

import java.net.URL;

public class Serie {
    URL url;
    String categorie;
    String name;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Serie(URL url, String categorie, String name) {
        this.url = url;
        this.categorie = categorie;
        this.name = name;
    }
}
