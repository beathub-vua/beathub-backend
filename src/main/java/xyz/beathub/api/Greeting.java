package xyz.beathub.api;

public class Greeting {

    private final long id;
    private final String content;
//test
    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content+"HAHAHA";
    }
}