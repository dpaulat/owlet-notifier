package net.dpaulat.apps.owlet;

public interface IOwletPropertyUpdated {
    public void callback(String name, String oldValue, String newValue);
}
