package com.zeroplus.dolittle.model;


public class Speakers {

    public static class Speaker {
        public Speaker(String nameArg){ name = nameArg; }

        public String getName(){ return name; }
        private String name;
    }

    public static class Stranger extends Speaker { public Stranger(String nameArg) { super(nameArg); }}
    public static class Friend extends Speaker { public Friend(String nameArg) { super(nameArg); }}

    public static class User extends Speaker { public User(String nameArg) { super(nameArg); }}

}

